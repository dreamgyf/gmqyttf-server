package com.dreamgyf.gmqyttf.server.socket;

import com.dreamgyf.gmqyttf.server.data.ClientSessionPool;
import com.dreamgyf.gmqyttf.server.data.ConnectedClientPool;
import com.dreamgyf.gmqyttf.server.socket.processor.MqttServerSocketProcessor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

public class MqttServerSocket {

    private Selector mSelector;

    private ServerSocketChannel mServerSocketChannel;

    private ClientSessionPool mClientSessionPool;

    private ConnectedClientPool mConnectedClientPool;

    private MqttServerSocketProcessor mProcessor;

    private boolean isRunning;

    private MqttServerSocket() {
    }

    public static MqttServerSocket create(int port, ClientSessionPool clientSessionPool, ConnectedClientPool connectedClientPool) throws IOException {
        MqttServerSocket serverSocket = new MqttServerSocket();
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocket.mSelector = selector;
        serverSocket.mServerSocketChannel = serverSocketChannel;
        serverSocket.mClientSessionPool = clientSessionPool;
        serverSocket.mConnectedClientPool = connectedClientPool;
        return serverSocket;
    }

    public void open() throws IOException {
        mServerSocketChannel.register(mSelector, SelectionKey.OP_ACCEPT);
        mProcessor = new MqttServerSocketProcessor(mClientSessionPool, mConnectedClientPool);
        isRunning = true;
        while (isRunning) {
            try {
                if (mSelector.select() == 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            Set<SelectionKey> selectionKeys = mSelector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isAcceptable()) {
                    mProcessor.onAcceptable(key);
                }
                if (key.isReadable()) {
                    mProcessor.onReadable(key);
                }
                if (key.isValid() && key.isWritable()) {
                    mProcessor.onWritable(key);
                }
            }
        }
    }

    public void close() throws IOException {
        isRunning = false;
        mSelector.close();
        mServerSocketChannel.close();
    }

    public boolean isOpen() {
        return isRunning && mSelector.isOpen() && mServerSocketChannel.isOpen();
    }
}
