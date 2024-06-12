package com.mrcdssclss.server.command;

import java.util.List;
import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.classes.City;
import com.mrcdssclss.common.classes.StandardOfLiving;
import com.mrcdssclss.server.managers.CollectionManager;


public class MinByCommand extends ServerCommand {


    public MinByCommand() {
        super("min_By_Standard_Of_Living", "вывести любой объект из коллекции, " +
                "значение поля " +
                "Standard_Of_Living которого является минимальным");
    }
    @Override
    public Response execute(Request request) {
        if (!request.getArgs().isEmpty()) {
            return new Response("Данная команда не имеет аргументов");
        }
        List<City> filteredCities = CollectionManager.getCollection()
                .stream()
                .filter(city -> city.getStandardOfLiving() == StandardOfLiving.NIGHTMARE)
                .toList();

        if (filteredCities.isEmpty()) {
            return new Response("Элемент с уровнем жизни NIGHTMARE не найден в коллекции");
        }
        return new Response(filteredCities.toString());
    }
}


