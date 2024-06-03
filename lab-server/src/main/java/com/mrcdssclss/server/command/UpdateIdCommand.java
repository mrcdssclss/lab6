package com.mrcdssclss.server.command;


import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
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
        if (request.getArgs().isBlank()) throw new IllegalArgumentException();
        class NoSuchId extends RuntimeException{

        }
        try {
            int id = Integer.parseInt(request.getArgs().trim());
            if (!collectionManager.isContain(request.getCity())) throw new NoSuchId();
            if (Objects.isNull(request.getCity())){
                return new Response("Для команды " + this.getName() + " требуется объект");
            }
            collectionManager.addById(id, request.getCity());
            return new Response("Объект успешно обновлен");
        } catch (NoSuchId err) {
            return new Response("В коллекции нет элемента с таким id");
        } catch (NumberFormatException exception) {
            return new Response("id должно быть числом типа int");
        }
    }
}