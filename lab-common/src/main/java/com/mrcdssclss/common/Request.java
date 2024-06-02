package com.mrcdssclss.common;

import com.mrcdssclss.common.classes.City;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Getter
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 75758L;
    private City city;
    private String message;
    private String args = ""; // Инициализация args пустой строкой

    public Request(String message) {
        this.message = message;
        this.args = extractArgs(message); // Инициализация args при создании объекта
    }

    public Request(String message, City city) {
        this.message = message;
        this.city = city;
        this.args = extractArgs(message); // Инициализация args при создании объекта
    }

    public void setMessage(String message) {
        this.message = message;
        this.args = extractArgs(message); // Обновление args при изменении message
    }

    private String extractArgs(String message) {
        return (message.trim() + " ").split(" ", 2)[1].trim();
    }

    public boolean isEmpty() {
        return message == null || message.isEmpty();
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void println(Object object){
        System.out.println(object);
    }

    public void printError(Object object){
        System.out.println("\u001B[31m" + "Error: " + object + "\u001B[0m");
    }

    public String readln() throws NoSuchElementException, IllegalStateException{
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(message, request.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
