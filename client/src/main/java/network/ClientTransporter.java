package network;


import authorization_lib.Account;
import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import logging.LogUtil;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.Callable;

public class ClientTransporter {
    private DatagramChannel channel;
    private final ClientSender clientSender;
    private final ClientReceiver clientReceiver;
    private final int PORT = 4999;
    private final int TIMEOUT = 1000000;
    private final int MAX_PACKET_SIZE = 65507;
    private SocketAddress serverAddress;
    private Account account;

    public ClientTransporter() {
        account = new Account();
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
    public Response transport(Request request) throws NetworkException {
        buildAndConnect();
        clientSender.sendPacket(channel, serverAddress, request);
        Response response = clientReceiver.getAResponse(channel, MAX_PACKET_SIZE);
        shutDown();
        return response;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public Account getAccount() {
        return account;
    }


}
