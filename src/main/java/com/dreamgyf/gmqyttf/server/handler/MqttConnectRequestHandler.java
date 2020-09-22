package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.enums.MqttConnectReturnCode;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.packet.MqttConnackPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttConnectPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttPacket;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.server.data.Client;

public class MqttConnectRequestHandler {

    public static MqttPacket updateClientAndBuildRespPacket(Client client, MqttConnectPacket mqttPacket) throws MqttPacketException {
        switch (mqttPacket.getVersion()) {
            case V3_1_1:
                return handleV311(client, mqttPacket);
        }
        throw new MqttPacketException();
    }

    private static MqttPacket handleV311(Client client, MqttConnectPacket mqttPacket) {
        client.setVersion(MqttVersion.V3_1_1);
        return new MqttConnackPacket.Builder().connectReturnCode(MqttConnectReturnCode.V3_1_1.ACCEPT).build(MqttVersion.V3_1_1);
    }

}
