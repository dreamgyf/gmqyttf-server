package com.dreamgyf.gmqyttf.server;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.server.socket.MqttServerSocket;

import java.io.IOException;

public class MqttServer {

    private MqttVersion mVersion;

    private int mPort;

    private MqttServerSocket mServerSocket;

    private MqttServer(MqttVersion version, int port) {
        mVersion = version;
        mPort = port;
    }

    public static class Builder {

        private MqttVersion version = MqttVersion.V3_1_1;

        private int port = 1883;

        public Builder version(MqttVersion version) {
            this.version = version;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public MqttServer build() {
            return new MqttServer(version, port);
        }

    }

    public void open() throws IOException {
        mServerSocket = MqttServerSocket.create(mPort);
        mServerSocket.open();
    }

    public void close() throws IOException {
        mServerSocket.close();
    }

    public MqttVersion getVersion() {
        return mVersion;
    }

    public int getPort() {
        return mPort;
    }
}
