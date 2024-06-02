package com.mrcdssclss.common.util;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Runner {
    private final CommandManager commandManager;
    ConsoleManager console;
    private final ArrayList<String> usedFileNames = new ArrayList<>();

    public Runner(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response getServerCommand(Request request) {
        try {
            String[] userCommand = (request.getMessage().trim() + " ").split(" ", 2);

            if (userCommand[0].isEmpty()) {
                return new Response("Команда не введена");
            }
            if (commandManager.getServerCommand(userCommand[0]) != null) {
                return serverCommandLaunch(userCommand, request);

            }
            else {
                return new Response("Команда введена некорректно или она является клиентской.");
            }
        } catch (NoSuchElementException e) {
            return new Response("Пользовательский ввод не обнаружен");
        } catch (IllegalStateException e) {
            return new Response("Непредвиденная ошибка");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getClientCommand(String command) {
        ConsoleManager console = new ConsoleManager();
        try {
            String[] userCommand;
            userCommand = (command.trim() + " ").split(" ", 2);
            if (userCommand[0].isEmpty()) {
                console.printError("Команда не введена");
            } else {
                if (commandManager.getClientCommand(userCommand[0]) == null) {
                    console.printError("Команда введена некорректно");
                } else {
                    if (userCommand.length == 1) {
                        userCommand = new String[]{userCommand[0], ""};
                    }
                    userCommand[1] = userCommand[1].trim();
                    commandManager.addToHistory(userCommand[0]);
                    if (userCommand[0].equals("exit")){
                        System.out.println("завершение работы");
                        System.exit(-1);
                    }
                    if (userCommand[0].equals("execute_script")) {
                        boolean commandStatus = scriptLaunch(userCommand);
                    }
                    boolean commandStatus = clientCommandLaunch(userCommand);
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
            File file = new File(fileName);
            console.setFileMode(true);
            console.setScanner(new Scanner(file));
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


    private Response serverCommandLaunch(String[] userCommand, Request request) throws IOException {
        ServerCommand serverCommand = commandManager.getServerCommand(userCommand[0]);
        if (serverCommand != null) {
            commandManager.addToHistory(userCommand[0]);
            return serverCommand.execute(request);
        } else {
            return new Response("Серверная команда не найдена");
        }
    }

    private boolean clientCommandLaunch(String[] userCommand) throws IOException {
        ClientCommand clientCommand = commandManager.getClientCommand(userCommand[0]);
        if (clientCommand != null) {
            commandManager.addToHistory(userCommand[0]);
            clientCommand.execute(userCommand[1]);
            return true;
        } else {
            System.err.println("клиентская команда не найдена");
            return false;
        }
    }
}
