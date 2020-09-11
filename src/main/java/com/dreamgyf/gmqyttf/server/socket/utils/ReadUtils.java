package com.dreamgyf.gmqyttf.server.socket.utils;

import com.dreamgyf.gmqyttf.common.throwable.exception.net.MqttSocketReadException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReadUtils {

    public static byte readSocketOneBit(SocketChannel channel) throws MqttSocketReadException {
        return readSocketBit(channel, 1)[0];
    }

    public static byte[] readSocketBit(SocketChannel channel, int length) throws MqttSocketReadException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        int resLength = 0;
        try {
            resLength = channel.read(byteBuffer);
            if (resLength < 0) {
                throw new MqttSocketReadException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new MqttSocketReadException();
        }
        if (length != resLength) {
            throw new MqttSocketReadException();
        }
        return byteBuffer.array();
    }
}
