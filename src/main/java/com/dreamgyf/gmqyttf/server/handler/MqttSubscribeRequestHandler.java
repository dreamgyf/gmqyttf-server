package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.MqttSubackPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttSubscribePacket;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.UnknownVersionException;
import com.dreamgyf.gmqyttf.server.data.Client;

public class MqttSubscribeRequestHandler {

    public static MqttSubackPacket updateClientAndBuildRespPacket(Client client, MqttSubscribePacket mqttPacket) throws MqttPacketException {
        switch (client.getVersion()) {
            case V3_1_1:
                return handleV311(client, mqttPacket);
        }
        throw new UnknownVersionException();
    }

    private static MqttSubackPacket handleV311(Client client, MqttSubscribePacket mqttPacket) {
        return null;
    }

}
