package network;

import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import logging.LogUtil;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.*;

public class Transporter {
    private BlockingQueue<Request> requestQueue;
    private BlockingQueue<Response> responseQueue;
    private BlockingQueue<SelectionKey> reRegisterKeys;
    private final ExecutorService readerRequest = Executors.newFixedThreadPool(3);
    private final ForkJoinPool handlerRequest = new ForkJoinPool(5);
    private final ExecutorService writerResponse = Executors.newCachedThreadPool();
    private DatagramChannel serverChannel;
    private Recipient recipient;
    private Processor processor;
    private Sender sender;
    private final int PORT = 4999;
    private Selector selector;

    public Transporter() {
    }

    public void init() throws NetworkException, EnvNotExistsException {
        LogUtil.logInfo("The server was started!");
        try {
            reRegisterKeys = new LinkedBlockingQueue<>();
            requestQueue = new LinkedBlockingQueue<>();
            responseQueue = new LinkedBlockingQueue<>();
            serverChannel = DatagramChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress("0.0.0.0", PORT));
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_READ);
            recipient = new Recipient(this.serverChannel, this.requestQueue);
            processor = new Processor(this.requestQueue, this.responseQueue);
            processor.init();
            sender = new Sender(this.serverChannel, this.responseQueue);
        } catch (IOException e) {
            System.out.println("There was an error while opening the channel...");
            LogUtil.logTrace(e);
            throw new NetworkException();
        }
    }

    public void handleConnection() throws LogException {
        System.out.println("The server is listening on port " + PORT);
        LogUtil.logInfo("The server is listening on port " + PORT);

        while (true) {
            try {
                while (!reRegisterKeys.isEmpty()) {
                    SelectionKey key = reRegisterKeys.poll();
                    key.interestOps(SelectionKey.OP_READ);
                }

                int readyChannels = selector.select();
                if (readyChannels == 0) continue;

                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isValid() && key.isReadable()) {
                        key.interestOps(0);
                        CompletableFuture
                                .runAsync(() -> recipient.call(), readerRequest)
                                .thenRunAsync(() -> {
                                    reRegisterKeys.add(key);
                                    selector.wakeup();
                                })
                                .thenRunAsync(() -> handlerRequest.submit(processor), handlerRequest)
                                .thenRunAsync(() -> writerResponse.submit(sender), writerResponse);
                    }
                }
            } catch (IOException e) {
                LogUtil.logTrace(e);
                throw new LogException("Selector select failed");
            }
        }
    }

    public void emergencyShutdown() {
        readerRequest.shutdownNow();
        handlerRequest.shutdownNow();
        writerResponse.shutdownNow();
    }
}
