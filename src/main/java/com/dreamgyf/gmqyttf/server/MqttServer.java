package com.dreamgyf.gmqyttf.server;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.server.data.ClientSessionPool;
import com.dreamgyf.gmqyttf.server.data.ConnectedClientPool;
import com.dreamgyf.gmqyttf.server.socket.MqttServerSocket;

import java.io.IOException;

public class MqttServer {

    private MqttVersion mVersion;

    private int mPort;

    private MqttServerSocket mServerSocket;

    private final ClientSessionPool mClientSessionPool;

    private final ConnectedClientPool mConnectedClientPool;

    private MqttServer(MqttVersion version, int port) {
        mVersion = version;
        mPort = port;
        mConnectedClientPool = ConnectedClientPool.create();
        mClientSessionPool = ClientSessionPool.create();
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
        mServerSocket = MqttServerSocket.create(mPort, mClientSessionPool, mConnectedClientPool);
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
