package network;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import goods.Response;
import logging.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientReceiver {
    public ClientReceiver() {
    }

    public Response getAResponse(DatagramChannel channel, int MAX_PACKET_SIZE) throws NetworkException {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
            channel.receive(buffer);
            buffer.flip();
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Response response = (Response) ois.readObject();
            buffer.clear();
            LogUtil.logInfo("Received response at port " + channel.getLocalAddress());
            return response;
        } catch (IOException | ClassNotFoundException e) {
            LogUtil.logTrace(e);
            throw new NetworkException();
        }
    }
}
