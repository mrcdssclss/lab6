package com.mrcdssclss.common.util;
import lombok.Getter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CommandManager {
    private final Map<String, ServerCommand> serverCommandMap = new HashMap<>();
    private final Map<String, ClientCommand> clientCommandMap = new HashMap<>();
    private final List<String> commandHistory = new ArrayList<>();

    public void ServerRegistration(String name, ServerCommand command){
        serverCommandMap.put(name,command);
    }
    public void ClientRegistration(String name, ClientCommand command){
        clientCommandMap.put(name,command);
    }

    public ServerCommand getServerCommand(String name){
        return serverCommandMap.get(name);
    }
    public ClientCommand getClientCommand(String name){
        return clientCommandMap.get(name);
    }

    public void addToHistory(String line){
        if(line.isBlank()) return;
        this.commandHistory.add(line);
    }


}
