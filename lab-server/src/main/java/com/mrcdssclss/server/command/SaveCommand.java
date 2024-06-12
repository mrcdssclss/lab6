package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.server.managers.CollectionManager;
import com.mrcdssclss.server.managers.FileManager;

public class SaveCommand extends ServerCommand {
    private final FileManager fileManager;

    public SaveCommand(FileManager fileManager, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.fileManager = fileManager;
    }
    public Response execute(Request request){
        CollectionManager.saveCollection(fileManager);
        return new Response("команда выполнена успешно");
    }
}

