package com.dreamgyf.gmqyttf.server.handler;

import com.dreamgyf.gmqyttf.common.enums.MqttConnectReturnCode;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.packet.MqttConnackPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttConnectPacket;
import com.dreamgyf.gmqyttf.common.packet.MqttPacket;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.IllegalPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketException;
import com.dreamgyf.gmqyttf.server.data.Client;
import com.dreamgyf.gmqyttf.server.data.ClientSessionPool;

public class MqttConnectRequestHandler {

    public static MqttPacket handleClientAndPacket(ClientSessionPool clientSessionPool, Client client, MqttConnectPacket packet) throws MqttPacketException {
        switch (packet.getVersion()) {
            case V3_1_1:
                return handleV311(clientSessionPool, client, packet);
        }
        return new MqttConnackPacket.Builder().connectReturnCode(MqttConnectReturnCode.V3_1_1.UNSUPPORTED_VERSION).build(MqttVersion.V3_1_1);
    }

    private static MqttPacket handleV311(ClientSessionPool clientSessionPool, Client client, MqttConnectPacket packet) throws IllegalPacketException {
        client.setVersion(packet.getVersion());
        client.setCleanSession(packet.isCleanSession());
        client.setWillFlag(packet.isWillFlag());
        client.setWillQoS(packet.getWillQoS());
        client.setWillRetain(packet.isWillRetain());
        client.setUsernameFlag(packet.isUsernameFlag());
        client.setPasswordFlag(packet.isPasswordFlag());
        client.setClientId(packet.getClientId());
        client.setWillTopic(packet.getWillTopic());
        client.setWillMessage(packet.getWillMessage());
        client.setUsername(packet.getUsername());
        client.setPassword(packet.getPassword());

        if(!client.isUsernameFlag() && client.isPasswordFlag()) {
            throw new IllegalPacketException("Username flag is 0, but password flag is 1");
        }

        if(client.isCleanSession()) {
            clientSessionPool.remove(client.getClientId());
        } else {
            Client sessionClient = clientSessionPool.get(client.getClientId());
            if(sessionClient != null) {

            } else {
                //TODO 验证会话正确性
                clientSessionPool.put(client.getClientId(), client);
            }
        }
        return new MqttConnackPacket.Builder().connectReturnCode(MqttConnectReturnCode.V3_1_1.ACCEPT).build(MqttVersion.V3_1_1);
    }

}
