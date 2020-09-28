package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.*;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.server.data.Client;
import com.dreamgyf.gmqyttf.server.data.ClientPool;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class MqttRequestHandler {

    private final ClientPool mClientPool;

    public MqttRequestHandler(ClientPool clientPool) {
        mClientPool = clientPool;
    }

    public MqttPacket updateClientAndBuildRespPacket(SocketChannel channel, MqttPacket mqttPacket) throws MqttPacketException {
        try {
            Client client = mClientPool.get(channel);
            if (mqttPacket instanceof MqttConnectPacket) {
                return MqttConnectRequestHandler.updateClientAndBuildRespPacket(client, (MqttConnectPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPublishPacket) {
                return MqttPublishRequestHandler.updateClientAndBuildRespPacket(client, (MqttPublishPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPubackPacket) {
                return MqttPubackRequestHandler.updateClientAndBuildRespPacket(client, (MqttPubackPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPubrecPacket) {
                return MqttPubrecRequestHandler.updateClientAndBuildRespPacket(client, (MqttPubrecPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPubrelPacket) {
                return MqttPubrelRequestHandler.updateClientAndBuildRespPacket(client, (MqttPubrelPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPubcompPacket) {
                return MqttPubcompRequestHandler.updateClientAndBuildRespPacket(client, (MqttPubcompPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttSubscribePacket) {
                return MqttSubscribeRequestHandler.updateClientAndBuildRespPacket(client, (MqttSubscribePacket) mqttPacket);
            } else if (mqttPacket instanceof MqttUnsubscribePacket) {
                return MqttUnsubscribeRequestHandler.updateClientAndBuildRespPacket(client, (MqttUnsubscribePacket) mqttPacket);
            } else if (mqttPacket instanceof MqttPingreqPacket) {
                return MqttPingreqRequestHandler.updateClientAndBuildRespPacket(client, (MqttPingreqPacket) mqttPacket);
            } else if (mqttPacket instanceof MqttDisconnectPacket) {
                mClientPool.remove(channel);
                return MqttDisconnectRequestHandler.updateClientAndBuildRespPacket(client, (MqttDisconnectPacket) mqttPacket);
            }

        } catch (IOException | MqttPacketException e) {
            e.printStackTrace();
            try {
                mClientPool.remove(channel);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new MqttPacketException(e.getMessage());
        }
        throw new MqttPacketException();
    }

}
