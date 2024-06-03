package com.mrcdssclss.server.command;

import java.util.ArrayDeque;

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
    public Response execute(Request request){
        if (!request.getArgs().isEmpty()) {
            request.printError("Данная команда не имеет аргументов");
        }
        ArrayDeque<City> city = CollectionManager.getCollection();
        if (city == null || city.isEmpty()) {
            request.printError("Коллекция пуста");
        }
        boolean found = false;
        for (City el : city) {
            if (el.getStandardOfLiving() == StandardOfLiving.NIGHTMARE) {
                found = true;
                return new Response(el.toString());
            }
        }
        if (!found) {
            System.out.println("Элемент с уровнем жизни NIGHTMARE не найден в коллекции");
        }
        return null;
    }

}


