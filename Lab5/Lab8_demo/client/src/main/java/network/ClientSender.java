package network;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import goods.Request;
import logging.LogUtil;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientSender {
    public ClientSender() {
    }

    public void sendPacket(DatagramChannel channel, SocketAddress serverAddress, Request request) throws NetworkException {
        try {
            if(!request.getCmd().equals("show")) CommandHistory.addHistory(request.getCmd());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            oos.flush();

            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            channel.send(buffer, serverAddress);
            LogUtil.logInfo("The " + request.getCmd() + " command is being sent from " + "port " + channel.getLocalAddress());
        } catch (IOException e) {
            LogUtil.logTrace(e);
            throw new NetworkException();
        }
    }
}
