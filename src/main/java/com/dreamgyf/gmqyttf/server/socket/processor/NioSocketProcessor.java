package com.dreamgyf.gmqyttf.server.socket.processor;

import java.nio.channels.SelectionKey;

public interface NioSocketProcessor {
    void onAcceptable(SelectionKey key);
    void onReadable(SelectionKey key);
    void onWritable(SelectionKey key);
}
