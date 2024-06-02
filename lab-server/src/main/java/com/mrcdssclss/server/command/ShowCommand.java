package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.util.ServerCommand;
import com.mrcdssclss.server.managers.CollectionManager;

public class ShowCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request){
        if (request.getArgs() != null && !request.getArgs().isEmpty()){
            return new Response("Данная команда не имеет аргументов"); // Возвращаем ошибку вместо вызова printError
        }
        return new Response(collectionManager.collectionToString());
    }
}
