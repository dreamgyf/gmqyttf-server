package com.dreamgyf.gmqyttf.server.data;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ClientPool {

    private final static Map<SocketAddress, Client> sClientMap = new HashMap<>();

    private ClientPool() {
    }

    public void putEmptyClient(SocketAddress addr) {
        sClientMap.put(addr, new Client());
    }

    public static ClientPool create() {
        return new ClientPool();
    }

    public Client get(SocketAddress addr) {
        return sClientMap.get(addr);
    }

    public void remove(SocketAddress addr) {
        sClientMap.remove(addr);
    }
}
