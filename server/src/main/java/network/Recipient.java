package network;

import exceptions.log_exceptions.LogException;
import logging.LogUtil;
import goods.Request;
import printer_options.RainbowPrinter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Recipient {

    public Recipient() {}
    public Request readARequest(DatagramChannel ss, int MAX_PACKET_SIZE) throws LogException {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
            SocketAddress remoteAddress = ss.receive(buffer);
            RainbowPrinter.printCondition("Received connection from: " + remoteAddress);
            buffer.flip();
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Request request = (Request) ois.readObject();
            buffer.clear();

            request.setRemoteAddress(remoteAddress);
            return request;
        } catch (IOException | ClassNotFoundException e) {
            RainbowPrinter.printError("There was an error while reading the channel!");
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }
}
