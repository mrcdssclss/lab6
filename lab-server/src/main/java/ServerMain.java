import com.mrcdssclss.common.Request;
import com.mrcdssclss.server.command.SaveCommand;
import com.mrcdssclss.server.managers.CollectionManager;
import com.mrcdssclss.server.managers.FileManager;
import com.mrcdssclss.server.managers.ServerCommandManager;
import com.mrcdssclss.server.managers.ServerRunner;
import com.mrcdssclss.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ServerMain {
    static int port = 8082;

    public static void main(String[] args) {
        if (args == null || args.length == 0 || args[0].isEmpty()){
            System.err.println("ты что самый умный?? где норм файл??");
            System.exit(-1);
        }
        FileManager fileManager = new FileManager(args[0]);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        fileManager.readCollection();
        ServerCommandManager commandManager = new ServerCommandManager(collectionManager, fileManager);
        ServerRunner runner = new ServerRunner(commandManager);
        //реализация save
        Thread consoleThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    if ("save".equals(inputLine.trim())) {
                        System.out.println("Выполнение команды сохранения");
                        SaveCommand saveCommand = new SaveCommand(fileManager, collectionManager);
                        System.out.println(saveCommand.execute(new Request(" ")).getResponse());
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка при чтении из консоли: " + e.getMessage());
            }
        });
        consoleThread.start();

        try {
            Server server = new Server(port, runner);
            server.start();
        } catch (IOException e) {
            System.err.println("нe удалось запустить клиент ");
            e.printStackTrace();
        }
    }
}
