package com.mrcdssclss.server.command;


import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.classes.City;
import com.mrcdssclss.server.managers.CollectionManager;
import java.util.Objects;


public class UpdateIdCommand extends ServerCommand {

    public UpdateIdCommand(CollectionManager collectionManager) {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public Response execute(Request request) {
        if (request.getArgs().isBlank()) return new Response("для команды нужны аргументы");
        if (request.getCity() == null) return new Response("объект пустой");
        try {
            CollectionManager.addById(Integer.valueOf(request.getArgs()), request.getCity());
            return new Response("Объект успешно обновлен");
        } catch (IllegalArgumentException err) {
            return new Response("В коллекции нет элемента с таким id");
        }
    }
}