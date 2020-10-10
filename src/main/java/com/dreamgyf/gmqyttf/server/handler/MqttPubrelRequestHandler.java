package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.MqttPubcompPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttPubrelPacket;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.UnknownVersionException;
import com.dreamgyf.gmqyttf.server.data.Client;

public class MqttPubrelRequestHandler {

    public static MqttPubcompPacket handleClientAndPacket(Client client, MqttPubrelPacket mqttPacket) throws MqttPacketException {
        switch (client.getVersion()) {
            case V3_1_1:
                return handleV311(client, mqttPacket);
        }
        throw new UnknownVersionException();
    }

    private static MqttPubcompPacket handleV311(Client client, MqttPubrelPacket mqttPacket) {
        return null;
    }

}
