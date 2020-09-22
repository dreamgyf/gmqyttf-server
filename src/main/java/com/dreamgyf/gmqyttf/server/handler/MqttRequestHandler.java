package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.MqttConnectPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttPacket;
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
            if(mqttPacket instanceof MqttConnectPacket) {
                return MqttConnectRequestHandler.updateClientAndBuildRespPacket(client, (MqttConnectPacket) mqttPacket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
