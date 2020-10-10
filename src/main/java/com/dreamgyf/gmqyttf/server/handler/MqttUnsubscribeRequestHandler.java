package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.packet.MqttUnsubackPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttUnsubscribePacket;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.UnknownVersionException;
import com.dreamgyf.gmqyttf.server.data.Client;

public class MqttUnsubscribeRequestHandler {

    public static MqttUnsubackPacket handleClientAndPacket(Client client, MqttUnsubscribePacket mqttPacket) throws MqttPacketException {
        switch (client.getVersion()) {
            case V3_1_1:
                return handleV311(client, mqttPacket);
        }
        throw new UnknownVersionException();
    }

    private static MqttUnsubackPacket handleV311(Client client, MqttUnsubscribePacket mqttPacket) {
        return null;
    }

}
