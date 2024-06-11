package com.mrcdssclss.server.managers;


import com.mrcdssclss.common.classes.City;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Math.max;


public class CollectionManager {
    @Getter @Setter
    private static ArrayDeque<City> collection = new ArrayDeque<>();
    @Getter
    private static LocalDateTime lastSaveTime;

    public CollectionManager(FileManager fileManager) {
        this.lastSaveTime = null;
    }


    public static City getById(int id) {
        return collection.stream().filter(city -> city.getId() == id).findFirst().orElse(null);
    }

    public static void addById(Integer id, City city){
        if (!isEmpty() && !collection.contains(city) && getCollectionById(id) != null){
            remove(id);
            city.setId(id);
            collection.addLast(city);
        }
    }

    public static void add(City city) { collection.add(city);}

    public static void remove(int id) {
        Iterator<City> iterator = collection.iterator();
        while (iterator.hasNext()) {
            City currentCity = iterator.next();
            if (currentCity.getId() == id) {
                iterator.remove();
                break;
            }
        }
    }

    public static void clear(){
        collection.clear();
    }

    public static void saveCollection(FileManager fileManager) {
        fileManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        StringBuilder info = new StringBuilder();
        for (var city : collection) {
            info.append(city).append("\n\n");
        }
        return info.toString().trim();
    }
    public static String collectionType(){
        return collection.getClass().getName();
    }
    public static int collectionSize(){
        return collection.size();
    }

    public static void removeFirst(){ collection.pollFirst(); }

    public static String collectionToString(){
        return collection.stream().map(Objects::toString).map(str -> str + "\n").collect(Collectors.joining());
    }

    public static boolean isEmpty(){
        return collection.isEmpty();
    }
    public static ArrayDeque<City> getCollectionById(Integer id){
        if (isEmpty()) return null;
        for (City el: collection){
            if (el.getId().equals(id)) return collection;
        }
        return null;
    }

}

