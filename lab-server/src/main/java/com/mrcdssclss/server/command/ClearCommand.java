package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.server.managers.CollectionManager;

public class ClearCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager){
        super("clear", " очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        collectionManager.clear();
        return new Response("команда выполнена успешно ");
    }
}

