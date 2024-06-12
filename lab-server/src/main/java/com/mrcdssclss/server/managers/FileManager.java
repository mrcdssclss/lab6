package com.mrcdssclss.server.managers;


import com.mrcdssclss.common.Response;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.mrcdssclss.common.classes.*;
import com.mrcdssclss.common.util.ConsoleManager;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ResourceBundle;

public class FileManager {
    private final String fileName;
    private final XStream xstream;

    public FileManager(String fileName) {
        this.fileName = fileName;
        this.xstream = new XStream(new DomDriver());
        this.xstream.alias("Array", ArrayDeque.class);
        this.xstream.alias("city", City.class);
        this.xstream.alias("coordinates", Coordinates.class);
        this.xstream.alias("standardOfLiving", StandardOfLiving.class);
        this.xstream.alias("government", Government.class);
        this.xstream.addPermission(AnyTypePermission.ANY);

        this.xstream.registerConverter(new CollectionConverter(xstream.getMapper()) {
            @Override
            public boolean canConvert(Class type) {
                return type.equals(ArrayDeque.class);
            }
        });
    }

    public Response writeCollection(ArrayDeque<City> collection) {
        if (!fileName.isEmpty()) {
            try {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    xstream.toXML(collection, writer);
                    return new Response("Коллекция сохранена в файле: " + fileName);
                }
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при записи в файл", e);
            }
        } else {
            return new Response("Имя файла пустое");
        }
    }

    public Response readCollection() {
        ConsoleManager console = new ConsoleManager();
        ArrayDeque<City> collection = new ArrayDeque<>();
        if (!fileName.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                Object obj = xstream.fromXML(reader);
                if (obj instanceof ArrayDeque<?> deserializedCollection) {
                    for (Object item : deserializedCollection) {
                        if (item instanceof City) {
                            collection.add((City) item);
                        } else {
                            return new Response("Невозможно преобразовать элемент XML в объект City");
                        }
                    }
                } else {
                    return new Response("Невозможно преобразовать XML в коллекцию");
                }
            } catch (IOException | ConversionException e) {
                return new Response("Ошибка при чтении файла: либо не считывается коллекция, либо файла нет");
            }
        } else {
            return new Response("Имя файла пустое");
        }
        CollectionManager.setCollection(collection);
        return new Response("файл прочитан успешно");
    }
}
