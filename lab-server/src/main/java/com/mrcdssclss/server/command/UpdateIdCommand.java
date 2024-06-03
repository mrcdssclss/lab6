package com.mrcdssclss.server.command;


import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.classes.City;
import com.mrcdssclss.server.managers.CollectionManager;
import java.util.Objects;


public class UpdateIdCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public UpdateIdCommand(CollectionManager collectionManager) {
        super("update_id", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        City city;
        try {
            int id = Integer.parseInt(request.getArgs().trim());
            city = collectionManager.getById(Integer.parseInt(request.getArgs()));
            if (request.getArgs().isBlank()) return new Response("для команды нужны аргументы");
            if (!collectionManager.isContain(city)) return new Response("такого элемента коллекции не существует");
            if (Objects.isNull(collectionManager.getById(Integer.parseInt(request.getArgs())))){
                return new Response("Для команды " + this.getName() + " требуется объект");
            }
            collectionManager.addById(id, request.getCity());
            return new Response("Объект успешно обновлен");
        } catch (IllegalArgumentException err) {
            return new Response("В коллекции нет элемента с таким id");
        }
    }
}