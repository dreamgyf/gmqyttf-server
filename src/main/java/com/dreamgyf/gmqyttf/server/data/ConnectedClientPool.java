package com.dreamgyf.gmqyttf.server.data;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class ConnectedClientPool {

    private final Map<SocketAddress, Client> mClientMap = new HashMap<>();

    private ConnectedClientPool() {
    }

    public static ConnectedClientPool create() {
        return new ConnectedClientPool();
    }

    public void putEmptyClient(SocketChannel channel) throws IOException {
        mClientMap.put(channel.getRemoteAddress(), new Client());
    }

    public Client get(SocketChannel channel) throws IOException {
        return mClientMap.get(channel.getRemoteAddress());
    }

    public void remove(SocketChannel channel) throws IOException {
        mClientMap.remove(channel.getRemoteAddress());
    }
}
