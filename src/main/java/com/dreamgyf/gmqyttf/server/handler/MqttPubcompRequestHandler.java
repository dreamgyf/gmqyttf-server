package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.MqttPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttPubcompPacket;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.UnknownVersionException;
import com.dreamgyf.gmqyttf.server.data.Client;

public class MqttPubcompRequestHandler {

    public static MqttPacket handleClientAndPacket(Client client, MqttPubcompPacket mqttPacket) throws MqttPacketException {
        switch (client.getVersion()) {
            case V3_1_1:
                return handleV311(client, mqttPacket);
        }
        throw new UnknownVersionException();
    }

    private static MqttPacket handleV311(Client client, MqttPubcompPacket mqttPacket) {
        return null;
    }

}
