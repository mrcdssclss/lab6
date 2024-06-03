package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.server.managers.CollectionManager;
import com.mrcdssclss.server.managers.FileManager;

public class SaveCommand extends ServerCommand {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    public SaveCommand(FileManager fileManager, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }
    public Response execute(Request request){
        collectionManager.saveCollection(fileManager);
        return new Response("команда выполнена успешно");
    }
}

