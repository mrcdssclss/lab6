package utility;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;


import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private final SocketChannel socketChannel;
    private ByteBuffer buffer = ByteBuffer.allocate(5000);
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

    public void start(ClientCommandManager clientCommandManager) {
        ClientRunner runner = new ClientRunner(clientCommandManager);
        try {
            while (true) {
                System.out.print("Введите команду: ");
                String[] command = scanner.nextLine().split(" ");
                if ("exit".equalsIgnoreCase(command[0])) {
                    System.out.println("Завершение работы клиента.");
                    break;
                }
                if (clientCommandManager.getClientCommandMap().containsKey(command[0])) {
                        runner.getClientCommand(command);
                    } else {
                        try {
                            Response response;
                            if (command.length == 2){
                                response = sendRequest(new Request(command[0]+ " " +command[1]));
                            }else {
                                response = sendRequest(new Request(command[0]));
                            }
                            System.out.println("Получен ответ: " + response.getResponse());
                        } catch (IOException | ClassNotFoundException e) {
                            System.err.println("Ошибка при отправке запроса или получении ответа.");
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        } finally {
            try {
                scanner.close();
                if (socketChannel != null && socketChannel.isOpen()) {
                    socketChannel.close();
                }
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии ресурсов.");
                e.printStackTrace();
            }
        }
    }

    public Response sendRequest(Request request) throws IOException, ClassNotFoundException, InterruptedException {
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
        return getResponse();
    }


    public Response getResponse() throws IOException, ClassNotFoundException, InterruptedException {
        int bytesRead = 0;
        if (!socketChannel.isOpen()) {
            throw new IOException("Соединение закрыто");
        }
        buffer = ByteBuffer.allocate(5000);
        while (bytesRead <=0){
            bytesRead = socketChannel.read(buffer);
        }
        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        buffer.clear();
        return (Response) ois.readObject();
    }
}
