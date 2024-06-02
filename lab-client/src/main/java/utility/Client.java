package utility;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.util.CommandManager;
import com.mrcdssclss.common.util.Runner;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private final SocketChannel socketChannel;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    Scanner scanner = new Scanner(System.in);


    public Client(String host, int port) throws IOException {
        this.socketChannel = SocketChannel.open();
        this.socketChannel.configureBlocking(false);
        this.socketChannel.connect(new InetSocketAddress(host, port));
        while (!socketChannel.finishConnect()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Подключение прервано, повторная попытка...");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Подключение к серверу " + host + " на порту " + port);
    }

    public void start(CommandManager commandManager) {
        Runner runner = new Runner(commandManager);
        try {
            while (true) {
                System.out.print("Введите команду: ");
                String command = scanner.nextLine();
                if (commandManager.getServerCommandMap().containsKey(command) || commandManager.getClientCommandMap().containsKey(command)) {
                    if ("exit".equalsIgnoreCase(command)) {
                        System.out.println("Завершение работы клиента.");
                        break;
                    }
                    if (commandManager.getClientCommandMap().containsKey(command)) {
                        runner.getClientCommand(command);
                    } else {
                        try {
                            sendRequest(new Request(command));
                            Response response = getResponse();
                            System.out.println("Получен ответ: " + response);
                        } catch (IOException | ClassNotFoundException e) {
                            System.err.println("Ошибка при отправке запроса или получении ответа.");
                            e.printStackTrace();
                        }
                    }
                }
            }
        } finally {
            try {
                scanner.close();
                if (socketChannel!= null && socketChannel.isOpen()) {
                    socketChannel.close();
                }
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии ресурсов.");
                e.printStackTrace();
            }
        }
    }


    public void sendRequest(Request request) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(request);
        }
        buffer.clear();
        buffer.put(baos.toByteArray());
        buffer.flip();
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        System.out.println("Отправлен запрос: " + request.getMessage());
    }

    public Response getResponse() throws IOException, ClassNotFoundException {
        if (!socketChannel.isOpen()) {
            throw new IOException("Соединение закрыто");
        }
        buffer = ByteBuffer.allocate(1024);
        int bytesRead = socketChannel.read(buffer);
        if (bytesRead == -1) {
            socketChannel.close();
            throw new IOException("Сервер закрыл соединение");
        }
        if (bytesRead > 0) {
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Response) ois.readObject();
        } else {
            throw new IOException("Отсутствуют данные для чтения от сервера");
        }
    }
}