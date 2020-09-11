package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.MqttConnectPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttPacket;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.server.data.Client;

public class MqttRequestHandler {

    public static MqttPacket buildResponsePacket(Client client, MqttPacket mqttPacket) throws MqttPacketException {
        if(mqttPacket instanceof MqttConnectPacket) {
            return MqttConnectRequestHandler.buildResponsePacket(client, (MqttConnectPacket) mqttPacket);
        }
        return null;
    }
}
