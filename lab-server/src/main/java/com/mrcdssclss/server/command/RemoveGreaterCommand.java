package com.mrcdssclss.server.command;

import java.util.ArrayDeque;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.classes.City;
import com.mrcdssclss.server.managers.CollectionManager;


public class RemoveGreaterCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater", " удалить из коллекции все элементы, большие, чем заданный");
        this.collectionManager = collectionManager;
    }
    public Response execute(Request request) {
        int id = 0;
        ArrayDeque<City> city = CollectionManager.getCollection();
        if (!request.getArgs().isBlank()) {
            id = Integer.parseInt(request.getArgs());
        } else {
            request.printError("Данная команда имеет аргументы");
        }
        for (City el : city){
            if (el.getId() > id){
                collectionManager.remove(el.getId());
            }
        }
        return new Response("команда выполнена успешно ");
    }
}
