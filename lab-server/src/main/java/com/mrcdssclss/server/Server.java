package com.mrcdssclss.server;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.util.Runner;

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
    private final ByteBuffer buffer = ByteBuffer.allocate(1024); // Исправлено
    private final Runner runner;

    public Server(int port, Runner runner) throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.bind(new InetSocketAddress(port));
        this.serverSocketChannel.configureBlocking(false);
        this.runner = runner;
    }

    public void start() {
        System.out.println("Сервер запущен и слушает на порту " + serverSocketChannel.socket().getLocalPort());
        while (true) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel!= null) {
                    handleClient(socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(SocketChannel socketChannel) {
        try {
            while (socketChannel.isOpen()) {
                Request request = getRequest(socketChannel);
                Response response = runner.getServerCommand(request);
                sendResponse(socketChannel, response);

                if (((request.getMessage().trim() + " ").split(" ", 2)[0].equalsIgnoreCase("exit"))) {
                    System.out.println("Клиент запросил завершение соединения");
                    break;
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public Request getRequest(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        buffer.clear();
        int bytesRead = socketChannel.read(buffer);
        if (bytesRead > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                 ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                return (Request) objectInputStream.readObject();
            }
        }
        throw new IOException("Не удалось прочитать запрос");
    }

    public void sendResponse(SocketChannel socketChannel, Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(response);
        }
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        System.out.println("Ответ отправлен клиенту");
    }
}
