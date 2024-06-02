package com.mrcdssclss.common.classes;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("standardOfLiving")
public enum StandardOfLiving {
    ULTRA_HIGH,
    HIGH,
    LOW,
    NIGHTMARE;

    public static String[] names() {
        StandardOfLiving[] standardOfLivings = StandardOfLiving.values();
        String[] names = new String[standardOfLivings.length];
        for (int i = 0; i < standardOfLivings.length; i++) {
            names[i] = standardOfLivings[i].name();
        }
        return names;
    }
}
