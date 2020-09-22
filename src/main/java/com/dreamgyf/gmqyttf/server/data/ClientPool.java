package com.dreamgyf.gmqyttf.server.data;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class ClientPool {

    private final static Map<SocketAddress, Client> sClientMap = new HashMap<>();

    private ClientPool() {
    }

    public void putEmptyClient(SocketChannel channel) throws IOException {
        sClientMap.put(channel.getRemoteAddress(), new Client());
    }

    public Client get(SocketChannel channel) throws IOException {
        return sClientMap.get(channel.getRemoteAddress());
    }

    public void remove(SocketChannel channel) throws IOException {
        sClientMap.remove(channel.getRemoteAddress());
    }

    public static ClientPool create() {
        return new ClientPool();
    }
}
