package network;

import exceptions.log_exceptions.LogException;
import logging.LogUtil;
import goods.Response;
import printer_options.RainbowPrinter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Sender {
    public Sender() {}

    public void sendAResponse(Response response, SocketAddress port, DatagramChannel channel) throws LogException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            oos.flush();
            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            channel.send(buffer, port);
            RainbowPrinter.printInfo("The response has been sent to " + port + ".");
            LogUtil.logInfo("The response has been sent to " + port + ".");
        } catch (IOException e) {
            LogUtil.logTrace(e);
            RainbowPrinter.printError("There was a problem with sending process!");
            throw new LogException();
        }

    }
}
