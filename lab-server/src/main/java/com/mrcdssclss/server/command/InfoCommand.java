package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.util.ServerCommand;
import com.mrcdssclss.server.managers.CollectionManager;

public class InfoCommand extends ServerCommand {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(Request request){
        if (!request.getArgs().isEmpty()){
            request.printError("Данная команда не имеет аргументов");
        }
        request.println("тип коллекции: " + collectionManager.collectionType()
                + ", дата инициализации: " + collectionManager.getLastInitTime()
                + ", размер: " + collectionManager.collectionSize());
        return new Response("команда выполнена успешно ");
    }
}