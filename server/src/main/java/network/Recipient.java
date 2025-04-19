package network;

import exceptions.log_exceptions.LogException;
import goods.Request;
import logging.LogUtil;
import printer_options.RainbowPrinter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class Recipient implements Callable<Request> {

    private final BlockingQueue<Request> requestQueue;
    private final DatagramChannel channel;
    private final int MAX_PACKET_SIZE = 65507;
    public Recipient(DatagramChannel channel, BlockingQueue<Request> requestQueue) {
        this.requestQueue = requestQueue;
        this.channel = channel;
    }

    public Request readARequest() throws LogException {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
            SocketAddress remoteAddress = null;
            synchronized (channel) {
                remoteAddress = channel.receive(buffer);
            }
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
            throw new LogException("Read Request failed!");
        }
    }

    @Override
    public Request call() {
        Request request = null;
        try {
            request = readARequest();
            requestQueue.put(request);
        } catch (LogException e) {
            RainbowPrinter.printError(e);
        } catch (InterruptedException e) {
            LogUtil.logTrace(e);
            RainbowPrinter.printError("There was an error while processing the request, please look at the log file for more details.");
        }
        return request;
    }
}
