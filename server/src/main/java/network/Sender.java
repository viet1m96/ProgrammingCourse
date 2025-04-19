package network;

import exceptions.log_exceptions.LogException;
import goods.Response;
import logging.LogUtil;
import printer_options.RainbowPrinter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class Sender implements Callable<Response> {

    private final BlockingQueue<Response> responseQueue;
    private final DatagramChannel channel;
    public Sender(DatagramChannel channel, BlockingQueue<Response> responseQueue) {
        this.responseQueue = responseQueue;
        this.channel = channel;
    }

    public void sendAResponse(Response response) throws LogException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            oos.flush();
            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            synchronized (channel) {
                System.out.println("Sending: " + response);
                channel.send(buffer, response.getClientAddress());
            }
            RainbowPrinter.printInfo("The response has been sent to " + response.getClientAddress() + ".");
            LogUtil.logInfo("The response has been sent to " + response.getClientAddress() + ".");
        } catch (IOException e) {
            LogUtil.logTrace(e);
            RainbowPrinter.printError("There was a problem with sending process!");
            throw new LogException("Send Response failed!");
        }
    }

    @Override
    public Response call() {
        Response response = null;
        try {
            response = responseQueue.take();
            sendAResponse(response);
        } catch (LogException e) {
            RainbowPrinter.printError(e.getMessage());
        } catch (InterruptedException e) {
            LogUtil.logTrace(e);
            RainbowPrinter.printError("There was a problem with sending process, please look at the log file for more details.");
        }
        return response;
    }
}
