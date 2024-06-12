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
        CollectionManager.add(request.getCity());
        return new Response("Объект успешно добавлен в коллекцию");
    }
}
