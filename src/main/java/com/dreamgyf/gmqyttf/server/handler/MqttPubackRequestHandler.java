package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.MqttPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttPubackPacket;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.UnknownVersionException;
import com.dreamgyf.gmqyttf.server.data.Client;

public class MqttPubackRequestHandler {

    public static MqttPacket handleClientAndPacket(Client client, MqttPubackPacket mqttPacket) throws MqttPacketException {
        switch (client.getVersion()) {
            case V3_1_1:
                return handleV311(client, mqttPacket);
        }
        throw new UnknownVersionException();
    }

    private static MqttPacket handleV311(Client client, MqttPubackPacket mqttPacket) {
        return null;
    }

}
