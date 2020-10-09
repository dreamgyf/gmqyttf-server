package com.dreamgyf.gmqyttf.server.data;

import java.util.HashMap;
import java.util.Map;

public class ClientSessionPool {

    private final Map<Short, Client> clientIdMap = new HashMap<>();

    private ClientSessionPool() {
    }

    public static ClientSessionPool create() {
        return new ClientSessionPool();
    }

    public void put(short id, Client client) {
        clientIdMap.put(id, client);
    }

    public Client get(short id) {
        return clientIdMap.get(id);
    }

    public boolean contains(short id) {
        return clientIdMap.containsKey(id);
    }

    public Client remove(short id) {
        return clientIdMap.remove(id);
    }
}
