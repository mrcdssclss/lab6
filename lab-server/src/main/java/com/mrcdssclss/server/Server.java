package com.mrcdssclss.server;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.server.managers.ServerCommandManager;
import com.mrcdssclss.server.managers.ServerRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    private final ServerSocketChannel serverSocketChannel;
    private final ByteBuffer buffer = ByteBuffer.allocate(5000);
    private SocketChannel socketChannel;
    private final ServerRunner runner;

    public Server(int port, ServerRunner runner) throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.bind(new InetSocketAddress(port));
        this.serverSocketChannel.configureBlocking(false);
        this.runner = runner;
    }

    public void start() throws IOException {
        System.out.println("Сервер запущен и слушает на порту " + serverSocketChannel.socket().getLocalPort());
        while(true) {
        socketChannel = serverSocketChannel.accept();
        if(socketChannel != null) {
            System.out.println("Подключен новый клиент: " + socketChannel.getRemoteAddress());
            while (true) {
                    try {
                        Request request = getRequest();
                        System.out.println(request.getMessage());
                        if (!request.isEmpty()) {
                            String command = (request.getMessage().split(" "))[0];
                            if (!ServerCommandManager.getServerCommandMap().containsKey(command)) {
                                sendResponse(new Response("такой команды не существует "));
                            }
                            Response response = runner.getServerCommand(request);
                            sendResponse(response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        System.err.println("клиент отключен");
                    }
                }
            }
        }
    }

    public Request getRequest() throws IOException, ClassNotFoundException {
        buffer.clear();
        socketChannel.read(buffer);
        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            byteArrayInputStream.close();
            objectInputStream.close();
            return (Request) objectInputStream.readObject();
        }
    }

    public void sendResponse(Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        System.out.println("Ответ отправлен клиенту");
    }

}
