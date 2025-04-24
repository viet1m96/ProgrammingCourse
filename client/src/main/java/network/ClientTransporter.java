package network;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import logging.LogUtil;
import printer_options.RainbowPrinter;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class ClientTransporter {
    private DatagramChannel channel;
    private ClientSender clientSender;
    private ClientReceiver clientReceiver;
    private final int PORT = 4999;
    private final int TIMEOUT = 1000;
    private final int MAX_PACKET_SIZE = 65507;
    private SocketAddress serverAddress;

    public ClientTransporter() {
        clientSender = new ClientSender();
        clientReceiver = new ClientReceiver();
    }

    public void buildAndConnect() throws NetworkException {
        try {
            channel = DatagramChannel.open();
            DatagramSocket socket = channel.socket();
            socket.bind(null);
            socket.setSoTimeout(TIMEOUT);
            serverAddress = new InetSocketAddress("localhost", PORT);
            channel.connect(serverAddress);
        } catch (IOException e) {
            RainbowPrinter.printError("The protocol could not be established.");
            LogUtil.logTrace(e);
            throw new NetworkException();
        }
    }


    public void shutDown() throws NetworkException {
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (IOException e) {
            LogUtil.logTrace(e);
            throw new NetworkException();
        }
    }

    public Response transport(Request request) throws NetworkException, LogException {
        buildAndConnect();
        clientSender.sendPacket(channel, serverAddress, request);
        Response response = clientReceiver.getAResponse(channel, MAX_PACKET_SIZE);
        shutDown();
        return response;
    }
}
