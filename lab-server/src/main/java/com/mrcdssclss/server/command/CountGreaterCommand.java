package com.mrcdssclss.server.command;



import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.server.managers.CollectionManager;
import java.util.Objects;

public class CountGreaterCommand extends ServerCommand {

    public CountGreaterCommand(){
        super("count_greater_than_meters_above_sea_level", " вывести количество элементов, значение поля meters_above_sea_level которых равно заданному");
    }

    public Response execute(Request request){
        if (request.getArgs().isBlank()) throw new IllegalArgumentException();
        String r = "0";
        String a = "Количество элементов, с большим значением поля meters_above_sea_level: ";
        try {
            double args = Double.parseDouble(request.getArgs().trim());
            r = String.valueOf(CollectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .filter(s -> s.getMetersAboveSeaLevel() > args)
                    .map(Objects::toString)
                    .count());
        } catch (NumberFormatException exception) {
            request.printError("meters_above_sea_level должно быть числом типа double");
        }
        return new Response(a.concat(r));
    }
}
