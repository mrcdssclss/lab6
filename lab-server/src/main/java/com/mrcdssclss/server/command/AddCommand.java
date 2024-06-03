package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.server.managers.CollectionManager;

import java.io.IOException;

public class AddCommand extends ServerCommand {

    public AddCommand(){
        super("add: ", "добавить элемент в коллекцию" );
    }

    @Override
    public Response execute(Request request) throws IOException {
        CollectionManager.add(request.getCity());
        return new Response(null);
    }
}
