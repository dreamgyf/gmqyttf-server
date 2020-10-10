package com.dreamgyf.gmqyttf.server.data;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.utils.MqttRandomPacketIdGenerator;

public class Client {

    private final MqttRandomPacketIdGenerator packetIdGenerator = MqttRandomPacketIdGenerator.create();

    private MqttVersion version;
    /**
     * 清理会话 Clean Session
     */
    private boolean cleanSession;
    /**
     * 遗嘱标志 Will Flag
     */
    private boolean willFlag;
    /**
     * 遗嘱QoS Will QoS
     */
    private int willQoS;
    /**
     * 遗嘱保留 Will Retain
     */
    private boolean willRetain;
    /**
     * 用户名标志 User Name Flag
     */
    private boolean usernameFlag;
    /**
     * 密码标志 Password Flag
     */
    private boolean passwordFlag;
    /**
     * 保持连接 Keep Alive
     */
    private short keepAliveTime;
    /**
     * 客户端标识符 Client Identifier
     */
    private String clientId;
    /**
     * 遗嘱主题 Will Topic
     */
    private String willTopic;
    /**
     * 遗嘱消息 Will Message
     */
    private String willMessage;
    /**
     * 用户名 User Name
     */
    private String username;
    /**
     * 密码 Password
     */
    private String password;

    public MqttRandomPacketIdGenerator getPacketIdGenerator() {
        return packetIdGenerator;
    }

    public MqttVersion getVersion() {
        return version;
    }

    public void setVersion(MqttVersion version) {
        this.version = version;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    public boolean isWillFlag() {
        return willFlag;
    }

    public void setWillFlag(boolean willFlag) {
        this.willFlag = willFlag;
    }

    public int getWillQoS() {
        return willQoS;
    }

    public void setWillQoS(int willQoS) {
        this.willQoS = willQoS;
    }

    public boolean isWillRetain() {
        return willRetain;
    }

    public void setWillRetain(boolean willRetain) {
        this.willRetain = willRetain;
    }

    public boolean isUsernameFlag() {
        return usernameFlag;
    }

    public void setUsernameFlag(boolean usernameFlag) {
        this.usernameFlag = usernameFlag;
    }

    public boolean isPasswordFlag() {
        return passwordFlag;
    }

    public void setPasswordFlag(boolean passwordFlag) {
        this.passwordFlag = passwordFlag;
    }

    public short getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(short keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getWillTopic() {
        return willTopic;
    }

    public void setWillTopic(String willTopic) {
        this.willTopic = willTopic;
    }

    public String getWillMessage() {
        return willMessage;
    }

    public void setWillMessage(String willMessage) {
        this.willMessage = willMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
