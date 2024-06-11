package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.server.managers.CollectionManager;


public class RemoveHeadCommand extends ServerCommand {

    public RemoveHeadCommand(CollectionManager collectionManager) {
        super("remove_head", "удалить первый элемент из коллекции");
    }

    @Override
    public Response execute(Request request) {
        try{
            if(!request.getArgs().isEmpty()) throw new IllegalArgumentException();
            CollectionManager.removeFirst();
            request.println("элемент был успешно удален");
            return new Response("команда выполнена успешно ");
        } catch (NumberFormatException e) {
            request.printError("Поле должно быть int");
        } catch (ArrayIndexOutOfBoundsException exception){
            request.printError("Недопустимый индекс");
        } catch (IllegalArgumentException e){
            request.printError("Использование аргумента '" + request.getArgs() + "' в команде '" + getName() + "'");
        }
        return null;
    }
}

