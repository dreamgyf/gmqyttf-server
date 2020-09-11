package com.dreamgyf.gmqyttf.server;

import java.io.IOException;

public class MqttServerDemo {

    public static void main(String[] args) {
        MqttServer server = new MqttServer.Builder().build();
        try {
            server.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
