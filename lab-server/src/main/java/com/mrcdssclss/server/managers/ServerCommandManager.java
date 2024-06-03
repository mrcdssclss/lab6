package com.mrcdssclss.server.managers;
import com.mrcdssclss.server.command.*;
import lombok.Getter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServerCommandManager {
    @Getter
    private static Map<String, ServerCommand> serverCommandMap = new HashMap<>();

    public ServerCommandManager(CollectionManager collectionManager, FileManager fileManager){
        serverCommandMap.put("add", new AddCommand());
        serverCommandMap.put("clear", new ClearCommand(collectionManager));
        serverCommandMap.put("count_greater_than_meters_above_sea_level", new CountGreaterCommand());
        serverCommandMap.put("filter_by_name", new FilterCommand());
        serverCommandMap.put("min_By_Standard_Of_Living", new MinByCommand());
        serverCommandMap.put("remove_greater", new RemoveGreaterCommand(collectionManager));
        serverCommandMap.put("remove_head", new RemoveHeadCommand(collectionManager));
        serverCommandMap.put("save", new SaveCommand(fileManager, collectionManager));
        serverCommandMap.put("info", new InfoCommand(collectionManager));
        serverCommandMap.put("show", new ShowCommand(collectionManager));
        serverCommandMap.put("update_id", new UpdateIdCommand(collectionManager));
    }


    public static ServerCommand getServerCommand(String name){
        return serverCommandMap.get(name);
    }

}
