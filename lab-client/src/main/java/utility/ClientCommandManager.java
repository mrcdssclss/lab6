package utility;

import com.mrcdssclss.server.command.*;
import command.ClientCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ClientCommandManager {
    private final Map<String, ClientCommand> clientCommandMap = new HashMap<>();
    private final List<String> serverCommands = new ArrayList<>();
    public void ClientRegistration(String name, ClientCommand command){
        clientCommandMap.put(name,command);
    }
    public void ServerRegistration(){
        serverCommands.add("clear: очистить коллекцию");
        serverCommands.add("count_greater_than_meters_above_sea_level: вывести количество элементов, значение поля meters_above_sea_level которых равно заданному");
        serverCommands.add("filter_by_name: вывести элементы, значение поля name которых содержит заданную подстроку");
        serverCommands.add("min_By_Standard_Of_Living: вывести любой объект из коллекции, значение пол Standard_Of_Living которого является минимальным");
        serverCommands.add("remove_greater: удалить из коллекции все элементы, большие, чем заданный");
        serverCommands.add("remove_head: удалить первый элемент из коллекции");
        serverCommands.add("save: сохранить коллекцию в файл");
        serverCommands.add("info: вывести в стандартный поток вывода информацию о коллекции");
        serverCommands.add("show: вывести в стандартный поток вывода все элементы коллекции");
        serverCommands.add("update_id: обновить значение элемента коллекции, id которого равен заданному");
    }
    public ClientCommand getClientCommand(String name){
        return clientCommandMap.get(name);
    }

}
