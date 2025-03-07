package network;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.UserException;
import logging.LogUtil;
import goods.Request;
import goods.Response;
import handler.Handler;
import printer_options.RainbowPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.List;

public class Transporter {
    private DatagramChannel serverChannel;
    private Sender sender;
    private Recipient receipient;
    private Handler handler;
    private final int PORT = 4999;
    private final int MAX_PACKET_SIZE = 65507;
    private Selector selector;

    public Transporter() {}
    public void init(String fileName) throws NetworkException {
        LogUtil.logInfo("The server was started!");
        try {
            serverChannel = DatagramChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress("localhost", PORT));
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_READ);
            receipient = new Recipient();
            handler = new Handler();
            handler.init(fileName);
            sender = new Sender();
        } catch (IOException e) {
            RainbowPrinter.printError("There was an error while opening the channel...");
            LogUtil.logTrace(e);
            throw new NetworkException();
        }
    }
    public void handleConnection() {
        LogUtil.logInfo("The server is listening on port " + PORT);
        while(true) {
            try {
                boolean stop = true;
                for(int i = 0; i < 6000; i++) {
                    if(selector.select(10) == 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        if(br.ready()) {
                            String str = br.readLine();
                            handler.processCommandFromServer(new Request(str, null, null));
                            stop = false;
                        }
                    } else {
                        stop = false;
                        break;
                    }
                }
                if(stop) {
                    handler.processCommandFromServer(new Request("save", null, null));
                    RainbowPrinter.printInfo("The server was stopped during long time waiting!");
                    LogUtil.logInfo("The server was stopped during long time waiting!");
                    System.exit(0);
                }
                for(SelectionKey key : selector.selectedKeys()) {
                    if(key.isReadable()) {
                        if(key.channel() instanceof DatagramChannel ss) {
                            Response response = null;
                            Request req = null;
                            try {
                                req = receipient.readARequest(ss, MAX_PACKET_SIZE);
                                try {
                                    response = handler.processCommandFromUser(req.getRemoteAddress(), req);
                                    response.addNotice("The " + req.getCmd() + " command has been successfully executed.");
                                } catch (UserException e) {
                                    List<String> notice = new ArrayList<>();
                                    notice.add(e.toString());
                                    notice.add("The " + req.getCmd() + " command has been unsuccessfully executed.");
                                    response = new Response(notice, null);
                                }
                            } catch (LogException e) {
                                RainbowPrinter.printError(e.toString());
                                List<String> notice = new ArrayList<>();
                                notice.add("There was a system error while executing the " + req.getCmd() + " command.");
                                notice.add("The " + req.getCmd() + " command has been unsuccessfully executed.");
                                response = new Response(notice, null);
                            }
                            sender.sendAResponse(response, req.getRemoteAddress(), ss);
                        }
                    }
                }
                selector.selectedKeys().clear();
            } catch (IOException e) {
                LogUtil.logTrace(e);
                RainbowPrinter.printError(new LogException().toString());
            } catch (LogException e) {
                RainbowPrinter.printError(e.toString());
            } finally {
                selector.selectedKeys().clear();
            }
        }
    }

    public void emergencyShutdown() {
        LogUtil.logInfo("A emergency shutdown process has been called!");
        LogUtil.logInfo("Shutting down, a backup process is being executed...");
        handler.processCommandFromServer(new Request("save", null, null));
    }
}
