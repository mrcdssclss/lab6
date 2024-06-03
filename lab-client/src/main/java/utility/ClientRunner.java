package utility;

import command.ClientCommand;
import com.mrcdssclss.common.util.ConsoleManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientRunner {
    private final ClientCommandManager clientCommandManager;

    private final ArrayList<String> usedFileNames = new ArrayList<>();

    public ClientRunner(ClientCommandManager clientCommandManager) {
        this.clientCommandManager = clientCommandManager;
    }

    public void getClientCommand(String[] command) {
        boolean commandStatus = false;
        ConsoleManager console = new ConsoleManager();
        try {
            String[] userCommand;
            userCommand = command;
            if (userCommand[0].isEmpty()) {
                console.printError("Команда не введена");
            } else {
                if (clientCommandManager.getClientCommand(userCommand[0]) == null) {
                    console.printError("Команда введена некорректно");
                } else {
                    if (userCommand.length == 1) {
                        userCommand = new String[]{userCommand[0], ""};
                    }
                    if (userCommand[0].equals("exit")){
                        System.out.println("завершение работы");
                        System.exit(-1);
                    }
                    if (userCommand[0].equals("execute_script")) {
                        commandStatus = scriptLaunch(userCommand);
                    } else {
                        commandStatus = clientCommandLaunch(userCommand);
                    }
                    if (commandStatus) console.println("Команда выполнена успешно");
                }
            }
        } catch (NoSuchElementException e) {
            console.printError("Пользовательский ввод не обнаружен");
        } catch (IllegalStateException e) {
            console.printError("Непредвиденная ошибка");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean scriptLaunch(String[] userCommand){
        ConsoleManager console = new ConsoleManager();
        boolean commandStatus = true;
        if (userCommand.length == 1) {
            console.printError("Введите название файла со скриптом");
            return false;
        }
        String fileName = userCommand[1];
        try{
            usedFileNames.add(fileName);
            String line;
            String[] scriptCommand;
            console.setFileMode(true);
            console.setScanner(new Scanner(new FileReader("/Users/lepidodendronnnn/IdeaProjects/lab6/lab-client/src/main/java/command/file.txt")));
            while (commandStatus && console.getScanner().hasNext() && (line = console.getScanner().nextLine()) != null){
                scriptCommand = (line.trim()+" ").split(" ", 2);
                scriptCommand[1] = scriptCommand[1].trim();
                while (scriptCommand[0].isEmpty() && console.getScanner().hasNext()){
                    line = console.getScanner().nextLine();
                    scriptCommand = (line.trim()+" ").split(" ", 2);
                    scriptCommand[1] = scriptCommand[1].trim();
                }
                if (scriptCommand[0].equals("execute_script")){
                    if (usedFileNames.contains(fileName)){
                        console.printError("Скрипты не вызываются рекурсивно");
                        commandStatus = false;
                    } else {
                        commandStatus = scriptLaunch(new String[]{"execute_script"});
                    }
                } else {
                    commandStatus = clientCommandLaunch(scriptCommand);
                }
            }
        }catch(IOException e){
            console.printError("Ошибка чтения файла" + e.getMessage());
        }
        console.setFileMode(false);
        usedFileNames.remove(fileName);
        return commandStatus;
    }

    private boolean clientCommandLaunch(String[] userCommand) throws IOException {
        ClientCommand clientCommand = clientCommandManager.getClientCommand(userCommand[0]);
        if (clientCommand != null) {
            clientCommand.execute(userCommand[1]);
            return true;
        } else {
            System.err.println("клиентская команда не найдена");
            return false;
        }
    }
}
