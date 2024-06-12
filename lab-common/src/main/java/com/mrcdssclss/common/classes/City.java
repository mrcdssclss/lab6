package com.mrcdssclss.common.classes;

import com.mrcdssclss.common.util.Validatable;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.lang.Math.max;


public class City implements Validatable, Serializable {
    @Getter
    private Integer id;
    @Getter
    private String name;
    private final Coordinates coordinates;
    private final LocalDateTime creationDate;
    private final int area;
    private final Long population;
    @Getter
    private double metersAboveSeaLevel;
    private final int carCode;
    private final Government government;
    @Getter
    private StandardOfLiving standardOfLiving;
    private final Long governor;
    private static Integer nextId = 1;

    public City(String name, Coordinates coordinates, LocalDateTime creationDate, int area, Long population, double metersAboveSeaLevel,
                int carCode, Government government, StandardOfLiving standardOfLiving, long governor){
        this.id = nextId;
        nextId++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.carCode = carCode;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }

    public void setId(Integer id){
        this.id = id;
        nextId = max(nextId, id + 1);
    }

    public static void resetId(){
        nextId = 1;
    }

    public boolean validate(){
        if (id == null || id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (area <= 0) return false;
        if (population <= 0) return false;
        if (carCode < 0 || carCode>1000) return false;
        if (government == null) return false;
        if (standardOfLiving == null) return false;
        if (governor < 0) return false;
        return true;}
    @Override
    public String toString() {
        return "->\n"+
                "id:\n " + id +
                ",\nname: \n" + name  +
                ",\ncoordinates: \n " + coordinates +
                ",\ncreationDate: \n " + creationDate +
                ",\narea: \n " + area +
                ",\npopulation \n " + population +
                ",\nmetersAboveSeaLevel \n " + metersAboveSeaLevel +
                ",\ncarCode \n '" + carCode +
                ",\ngovernment \n " + government +
                ",\nstandardOfLiving \n " + standardOfLiving +
                ",\ngovernor \n " + governor;
    }
}
