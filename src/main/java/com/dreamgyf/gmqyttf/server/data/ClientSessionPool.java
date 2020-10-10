package com.dreamgyf.gmqyttf.server.data;

import java.util.HashMap;
import java.util.Map;

public class ClientSessionPool {

    private final Map<String, Client> clientIdMap = new HashMap<>();

    private ClientSessionPool() {
    }

    public static ClientSessionPool create() {
        return new ClientSessionPool();
    }

    public void put(String id, Client client) {
        clientIdMap.put(id, client);
    }

    public Client get(String id) {
        return clientIdMap.get(id);
    }

    public boolean contains(String id) {
        return clientIdMap.containsKey(id);
    }

    public Client remove(String id) {
        return clientIdMap.remove(id);
    }
}
