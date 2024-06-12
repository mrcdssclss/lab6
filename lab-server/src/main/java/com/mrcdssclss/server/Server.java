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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {

    private final ServerSocketChannel serverSocketChannel;
    private final ByteBuffer buffer = ByteBuffer.allocate(5000);
    private final ServerRunner runner;
    private final List<SocketChannel> clients = new ArrayList<>();

    public Server(int port, ServerRunner runner) throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.bind(new InetSocketAddress(port));
        this.serverSocketChannel.configureBlocking(false);
        this.runner = runner;
    }

    public void start() throws IOException {
        System.out.println("Сервер запущен и слушает на порту " + serverSocketChannel.socket().getLocalPort());
        while (true) {
            // Accept new clients
            SocketChannel newClient = serverSocketChannel.accept();
            if (newClient != null) {
                newClient.configureBlocking(false);
                clients.add(newClient);
                System.out.println("Подключен новый клиент: " + newClient.getRemoteAddress());
            }

            Iterator<SocketChannel> iterator = clients.iterator();
            while (iterator.hasNext()) {
                SocketChannel client = iterator.next();
                try {
                    if (client.isConnected() && client.read(buffer) > 0) {
                        buffer.flip();
                        Request request = getRequest(buffer);
                        buffer.clear();
                        System.out.println(request.getMessage());
                        if (!request.isEmpty()) {
                            String command = (request.getMessage().split(" "))[0];
                            if (!ServerCommandManager.getServerCommandMap().containsKey(command)) {
                                sendResponse(client, new Response("такой команды не существует "));
                            } else {
                                Response response = runner.getServerCommand(request);
                                sendResponse(client, response);
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    iterator.remove();
                    try {
                        client.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public Request getRequest(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (Request) objectInputStream.readObject();
        }
    }

    public void sendResponse(SocketChannel client, Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        while (buffer.hasRemaining()) {
            client.write(buffer);
        }
        System.out.println("Ответ отправлен клиенту");
    }
}
