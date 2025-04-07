package network;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import goods.Response;
import logging.LogUtil;
import printer_options.RainbowPrinter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientReceiver {
    public ClientReceiver() {}

    public Response getAResponse(DatagramChannel channel, int MAX_PACKET_SIZE) throws NetworkException, LogException {
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
        } catch (SocketTimeoutException e) {
            RainbowPrinter.printError("Server timed out (no response)!");
            LogUtil.logTrace(e);
            throw new NetworkException();
        }
        catch (PortUnreachableException e) {
            RainbowPrinter.printError("Can not connect to the server! Server's port may be unreachable!");
            LogUtil.logTrace(e);
            throw new NetworkException();
        }
        catch (IOException | ClassNotFoundException e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }
}
