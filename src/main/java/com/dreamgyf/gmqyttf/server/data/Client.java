package com.dreamgyf.gmqyttf.server.data;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;

public class Client {

    private boolean isConnected = false;

    private MqttVersion version;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public MqttVersion getVersion() {
        return version;
    }

    public void setVersion(MqttVersion version) {
        this.version = version;
    }
}
