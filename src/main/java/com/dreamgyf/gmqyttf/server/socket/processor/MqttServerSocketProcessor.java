package com.dreamgyf.gmqyttf.server.socket.processor;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.packet.*;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.IllegalPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;
import com.dreamgyf.gmqyttf.server.data.Client;
import com.dreamgyf.gmqyttf.server.data.ClientPool;
import com.dreamgyf.gmqyttf.server.handler.MqttRequestHandler;
import com.dreamgyf.gmqyttf.server.socket.utils.ReadUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class MqttServerSocketProcessor implements NioSocketProcessor {

    private final ClientPool mClientPool;

    private final MqttRequestHandler mRequestHandler;

    public MqttServerSocketProcessor(ClientPool clientPool) {
        mClientPool = clientPool;
        mRequestHandler = new MqttRequestHandler(clientPool);
    }

    @Override
    public void onAcceptable(SelectionKey key) {
        ServerSocketChannel acceptableServerSocketChannel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel clientChannel = acceptableServerSocketChannel.accept();
            if (clientChannel != null) {
                clientChannel.configureBlocking(false);
                clientChannel.register(key.selector(), SelectionKey.OP_READ);
                mClientPool.putEmptyClient(clientChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReadable(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        MqttPacket packet = fetchPacket(channel);
        if (packet != null) {
            key.interestOps(SelectionKey.OP_WRITE);
            key.attach(packet);
        } else {
            try {
                mClientPool.remove(channel);
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onWritable(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        MqttPacket packet = (MqttPacket) key.attachment();
        if (packet != null) {
            try {
                MqttPacket respPacket = mRequestHandler.updateClientAndBuildRespPacket(channel, packet);
                if (respPacket != null) {
                    channel.write(ByteBuffer.wrap(respPacket.getPacket()));
                }
                key.interestOps(SelectionKey.OP_READ);
            } catch (MqttPacketException | IOException e) {
                e.printStackTrace();
                try {
                    mClientPool.remove(channel);
                    channel.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        } else {
            try {
                mClientPool.remove(channel);
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MqttPacket fetchPacket(SocketChannel channel) {
        try {
            Client client = mClientPool.get(channel);
            if (client == null) {
                return null;
            }

            byte[] header = new byte[1];
            header[0] = ReadUtils.readSocketOneBit(channel);
            byte type = MqttPacketUtils.parseType(header[0]);

            if ((type != MqttPacketType.V3_1_1.CONNECT && !client.isConnected()) ||
                    (type == MqttPacketType.V3_1_1.CONNECT && client.isConnected()) ||
                    (client.isConnected() && !MqttPacketUtils.isTypeInVersion(type, client.getVersion()))) {
                return null;
            }

            byte[] tempRemainLength = new byte[4];
            int pos = 0;
            do {
                tempRemainLength[pos++] = ReadUtils.readSocketOneBit(channel);
            } while (MqttPacketUtils.hasNextRemainingLength(tempRemainLength[pos - 1]));
            byte[] remainLength = ByteUtils.getSection(tempRemainLength, 0, pos);
            byte[] fixHeader = ByteUtils.combine(header, remainLength);
            byte[] residue = ReadUtils.readSocketBit(channel, MqttPacketUtils.getRemainingLength(remainLength, 0));
            byte[] packet = ByteUtils.combine(fixHeader, residue);
            return parsePacket(packet, client.isConnected() ? client.getVersion() : MqttVersion.V3_1_1);

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    private MqttPacket parsePacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        switch (version) {
            case V3_1_1:
                return parsePacketV311(packet);
        }
        return null;
    }

    private MqttPacket parsePacketV311(byte[] packet) throws MqttPacketParseException {
        byte type = MqttPacketUtils.parseType(packet[0]);
        switch (type) {
            case MqttPacketType.V3_1_1.CONNECT: {
                return new MqttConnectPacket(packet);
            }
            case MqttPacketType.V3_1_1.PUBLISH: {
                return new MqttPublishPacket(packet, MqttVersion.V3_1_1);
            }
            case MqttPacketType.V3_1_1.PUBACK: {
                return new MqttPubackPacket(packet, MqttVersion.V3_1_1);
            }
            case MqttPacketType.V3_1_1.PUBREC: {
                return new MqttPubrecPacket(packet, MqttVersion.V3_1_1);
            }
            case MqttPacketType.V3_1_1.PUBREL: {
                return new MqttPubrelPacket(packet, MqttVersion.V3_1_1);
            }
            case MqttPacketType.V3_1_1.PUBCOMP: {
                return new MqttPubcompPacket(packet, MqttVersion.V3_1_1);
            }
            case MqttPacketType.V3_1_1.SUBSCRIBE: {
                return new MqttSubscribePacket(packet, MqttVersion.V3_1_1);
            }
            case MqttPacketType.V3_1_1.UNSUBSCRIBE: {
                return new MqttUnsubscribePacket(packet, MqttVersion.V3_1_1);
            }
            case MqttPacketType.V3_1_1.PINGREQ: {
                return new MqttPingreqPacket(packet, MqttVersion.V3_1_1);
            }
            case MqttPacketType.V3_1_1.DISCONNECT: {
                return new MqttDisconnectPacket(packet, MqttVersion.V3_1_1);
            }
            default:
                throw new IllegalPacketException();
        }
    }
}
