package network;

import exceptions.log_exceptions.LogException;
import goods.Request;
import logging.LogUtil;
import printer_options.RainbowPrinter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientSender {

    public ClientSender() {}
    public void sendPacket(DatagramChannel channel, SocketAddress serverAddress, Request request) throws LogException {
        RainbowPrinter.printCondition("The " + request.getCmd() + " command is being executed!");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            oos.flush();

            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            channel.send(buffer, serverAddress);
            LogUtil.logInfo("The " + request.getCmd() + " command is being sent from " + "port " + channel.getLocalAddress());
        } catch (IOException e) {
            LogUtil.logTrace(e);
            throw new LogException("Error while sending from client");
        }
    }
}
