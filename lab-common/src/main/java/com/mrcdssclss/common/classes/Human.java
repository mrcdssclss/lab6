package com.mrcdssclss.common.classes;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

import java.io.Serializable;

@Data
@XStreamAlias("governor")
public class Human implements Serializable {
    private int age; //Значение поля должно быть больше 0

}
