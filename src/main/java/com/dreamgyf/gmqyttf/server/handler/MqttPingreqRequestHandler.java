package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.MqttPingreqPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttPingrespPacket;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.UnknownVersionException;
import com.dreamgyf.gmqyttf.server.data.Client;

public class MqttPingreqRequestHandler {

    public static MqttPingrespPacket updateClientAndBuildRespPacket(Client client, MqttPingreqPacket mqttPacket) throws MqttPacketException {
        switch (client.getVersion()) {
            case V3_1_1:
                return handleV311(client, mqttPacket);
        }
        throw new UnknownVersionException();
    }

    private static MqttPingrespPacket handleV311(Client client, MqttPingreqPacket mqttPacket) {
        return null;
    }

}
