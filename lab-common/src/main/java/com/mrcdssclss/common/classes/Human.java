package com.mrcdssclss.common.classes;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
@Data
@XStreamAlias("governor")
public class Human {
    private int age; //Значение поля должно быть больше 0

}
