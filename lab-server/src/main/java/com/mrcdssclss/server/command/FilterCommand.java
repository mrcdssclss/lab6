package com.mrcdssclss.server.command;


import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.classes.City;
import com.mrcdssclss.server.managers.CollectionManager;

import java.util.ArrayDeque;
import java.util.stream.Collectors;

public class FilterCommand extends ServerCommand {
    public FilterCommand() {
        super("filter_by_name ", "вывести элементы, значение поля name которых содержит заданную подстроку");
    }
    @Override
    public Response execute(Request request) {
        String r;
        try {
            if (request.getArgs().isEmpty()) throw new IllegalArgumentException();
            var name = filterByName(request.getArgs());
            if (name.isEmpty()) {
                return new Response( "элементов, которые содержат '" + request.getArgs() + "' не обнаружено.");
            } else {
                return new Response("элементов, которые содержат '" + request.getArgs()  + "' обнаружено " + name.size() + " шт.\n" + name);
            }
        } catch (IllegalArgumentException exception) {
            request.printError("Неправильное количество аргументов!");
            request.println("Использование: '" + getName() + "'");
        }
        return null;
    }
    private ArrayDeque<City> filterByName(String partNumberSubstring) {
        return CollectionManager.getCollection().stream()
                .filter(name -> (name.getName() != null && name.getName().contains(partNumberSubstring)))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }
}
