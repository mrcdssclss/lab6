package com.mrcdssclss.server.managers;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.util.ConsoleManager;
import com.mrcdssclss.server.command.ServerCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ServerRunner {
    private final ServerCommandManager serverCommandManager;
    ConsoleManager console;
    private final ArrayList<String> usedFileNames = new ArrayList<>();

    public ServerRunner(ServerCommandManager serverCommandManager) {
        this.serverCommandManager = serverCommandManager;
    }

    public Response getServerCommand(Request request) {
        try {
            String[] userCommand = (request.getMessage().trim() + " ").split(" ", 2);

            if (userCommand[0].isEmpty()) {
                return new Response("Команда не введена");
            }
            if (serverCommandManager.getServerCommand(userCommand[0]) != null) {
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
    private Response serverCommandLaunch(String[] userCommand, Request request) throws IOException {
        ServerCommand serverCommand = ServerCommandManager.getServerCommand(userCommand[0]);
        if (serverCommand != null) {
            return serverCommand.execute(request);
        } else {
            return new Response("Серверная команда не найдена");
        }
    }


}
