package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.server.managers.CollectionManager;

public class ClearCommand extends ServerCommand {

    public ClearCommand(CollectionManager collectionManager){
        super("clear", " очистить коллекцию");
    }

    @Override
    public Response execute(Request request) {
        CollectionManager.clear();
        return new Response("команда выполнена успешно ");
    }
}

