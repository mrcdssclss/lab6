package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.classes.City;
import com.mrcdssclss.server.managers.CollectionManager;
import java.io.IOException;
import java.util.ArrayDeque;

public class AddCommand extends ServerCommand {

    public AddCommand(){
        super("add: ", "добавить элемент в коллекцию" );
    }

    @Override
    public Response execute(Request request) throws IOException {
        ArrayDeque<City> collection = CollectionManager.getCollectionById(request.getCity().getId());
        if (collection == null || collection.isEmpty()) {
            CollectionManager.add(request.getCity());
            return new Response("Объект успешно добавлен в коллекцию");
        } else {
            return new Response("Невозможно добавить объект в коллекцию, т.к. объект с таким ID уже существует");
        }
    }
}
