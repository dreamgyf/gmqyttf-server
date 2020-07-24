package com.dreamgyf.gmqyttf.server;

import com.dreamgyf.gmqyttf.server.enums.MqttVersion;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class MqttServer {

    private MqttVersion mVersion;

    private int mPort;

    private MqttServer(MqttVersion version, int port) {
        mVersion = version;
        mPort = port;
    }

    public static class Builder {

        private MqttVersion version = MqttVersion.V_3_1_1;

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
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
    }
}
