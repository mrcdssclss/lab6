package com.mrcdssclss.common.classes;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("government")
public enum Government {
    COMMUNISM,
    KRITARCHY,
    TOTALITARIANISM;
    public static String[] names() {
        Government[] governments = Government.values();
        String[] names = new String[governments.length];
        for (int i = 0; i < governments.length; i++) {
            names[i] = governments[i].name();
        }
        return names;
    }
}
