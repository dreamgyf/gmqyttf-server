package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.*;
import com.dreamgyf.gmqyttf.common.throwable.exception.MqttException;
import com.dreamgyf.gmqyttf.common.throwable.exception.connect.MqttConnectException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.common.throwable.runtime.connect.MqttUnconnectedException;
import com.dreamgyf.gmqyttf.server.data.Client;
import com.dreamgyf.gmqyttf.server.data.ClientSessionPool;
import com.dreamgyf.gmqyttf.server.data.ConnectedClientPool;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class MqttRequestHandler {

    private final ClientSessionPool mClientSessionPool;

    private final ConnectedClientPool mConnectedClientPool;

    public MqttRequestHandler(ClientSessionPool clientSessionPool, ConnectedClientPool connectedClientPool) {
        mClientSessionPool = clientSessionPool;
        mConnectedClientPool = connectedClientPool;
    }

    public MqttPacket handleClientAndPacket(SocketChannel channel, MqttPacket mqttPacket) throws MqttException {
        try {
            Client client = mConnectedClientPool.get(channel);

            if(client != null && mqttPacket instanceof MqttConnectPacket) {
                throw new MqttConnectException("Already connected");
            } else if(client == null && !(mqttPacket instanceof MqttConnectPacket)) {
                throw new MqttUnconnectedException("Unconnected");
            }

            if (mqttPacket instanceof MqttConnectPacket) {
                client = new Client();
                mConnectedClientPool.put(channel, client);
                return MqttConnectRequestHandler.handleClientAndPacket(mClientSessionPool, client, (MqttConnectPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPublishPacket) {
                return MqttPublishRequestHandler.handleClientAndPacket(client, (MqttPublishPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPubackPacket) {
                return MqttPubackRequestHandler.handleClientAndPacket(client, (MqttPubackPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPubrecPacket) {
                return MqttPubrecRequestHandler.handleClientAndPacket(client, (MqttPubrecPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPubrelPacket) {
                return MqttPubrelRequestHandler.handleClientAndPacket(client, (MqttPubrelPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPubcompPacket) {
                return MqttPubcompRequestHandler.handleClientAndPacket(client, (MqttPubcompPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttSubscribePacket) {
                return MqttSubscribeRequestHandler.handleClientAndPacket(client, (MqttSubscribePacket) mqttPacket);
            } else if (mqttPacket instanceof MqttUnsubscribePacket) {
                return MqttUnsubscribeRequestHandler.handleClientAndPacket(client, (MqttUnsubscribePacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPingreqPacket) {
                return MqttPingreqRequestHandler.handleClientAndPacket(client, (MqttPingreqPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttDisconnectPacket) {
                mConnectedClientPool.remove(channel);
                return MqttDisconnectRequestHandler.handleClientAndPacket(client, (MqttDisconnectPacket) mqttPacket);
            }

        } catch (IOException | MqttPacketException e) {
            e.printStackTrace();
            try {
                mConnectedClientPool.remove(channel);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new MqttPacketException(e.getMessage());
        }
        throw new MqttPacketException();
    }

}
