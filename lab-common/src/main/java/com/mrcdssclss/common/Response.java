package com.mrcdssclss.common;

import com.mrcdssclss.common.classes.City;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class Response implements Serializable {
    @Getter @Setter
    private String response = "";
    private City city;

    public Response(String response) {
         this.response = response;
    }

    public Response(String response, City city) {
        this.response = response.trim();
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response response1)) return false;
        return Objects.equals(response, response1.response) && Objects.equals(city, response1.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response, city);
    }

}