package utility;

import com.mrcdssclss.common.classes.*;
import lombok.*;
import com.mrcdssclss.common.util.ConsoleManager;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;


@Getter
public class Ask {
    Integer id;
    private final ConsoleManager console;
    public static class AskBreak extends Exception {}
    public Ask(ConsoleManager console) {
        this.console = console;
    }


    public int setId() {
        while (true) {
            console.println("Введите id города");
            var line = "";
            if (console.getFileMode()) {
                line = console.getScanner().nextLine().trim();
            } else {
                line = console.readln().trim();
            }
            if (line.equals("exit")) System.exit(1);
            if (line.isEmpty()) {
                console.printError("Значение не было введено");
            }
            if (!line.isEmpty()) {
                try {
                    id = Integer.parseInt(line);
                    if (id <= 0) {
                        console.printError("Значение должно быть больше нуля");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    console.printError("Значение должно быть числом");

                }
            }
        }
        return id;
    }



    public String askName() throws AskBreak {
        String name;
        do {
            console.print("name: ");
            name = console.readln().trim();
            if (name.equals("exit")) throw new AskBreak();
        } while (name.matches("^[0-9]+$") || name.isEmpty());
        return name;
    }
    public Coordinates askCoordinates() throws AskBreak {
        try {
            float x;
            while (true) {
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                     x = Float.parseFloat(line); break;
                }
            }
            float y;
            while (true) {
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    y = Float.parseFloat(line); break;
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            throw new AskBreak();
        }
    }

    public LocalDateTime askCreationDate(){
        return LocalDateTime.now();
    }
    public int askArea() throws AskBreak {
        int area;
        while (true) {
            console.print("area: ");
            var line = console.readln().trim();
            if (line.equals("exit")) throw new AskBreak();
            if (line.matches("^-?[0-9]+$") && Integer.parseInt(line) > 0) {
                    area = Integer.parseInt(line);
                    break;
            }
        }
        return area;
    }

    public Long askPopulation() throws AskBreak {
        long population;
        while (true){
            console.print("population: ");
            var line = console.readln().trim();
            if (line.equals("exit")) throw new AskBreak();
            if (line.matches("^[0-9]+$") && (!line.isEmpty() | Long.parseLong(line) > 0)) {
                population = Long.parseLong(line); break;
            }
        } return population;
    }

    public double askMetersAboveSeaLevel() throws AskBreak{
        double metersAboveSeaLevel;
        while (true){
            console.print("meters above sea level: ");
            var line = console.readln().trim();
            if (line.equals("exit")) throw new AskBreak();
            if (line.matches("^-?[0-9]+(?:,[0-9]+)?$") && !line.isEmpty()) {
                metersAboveSeaLevel = Double.parseDouble(line); break;
            }
        } return metersAboveSeaLevel;
    }

    public int askCarCode()throws AskBreak{
        int carCode;
        while (true) {
            console.print("Car Code: ");
            var line = console.readln().trim();
            if (line.matches("^[0-9]+$") && Integer.parseInt(line) > 0 && Integer.parseInt(line) < 1000) {
                carCode = Integer.parseInt(line); break;
            }
            if (line.equals("exit")) throw new AskBreak();
        } return carCode;
    }

    public Government askGovernment()throws AskBreak{
        try{
            Government r;
            while (true){
                console.print("Government ("+ Arrays.toString(Government.names()));
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty() && (line.equals("COMMUNISM") || line.equals("KRITARCHY") || line.equals("TOTALITARIANISM"))) {
                        r = Government.valueOf(line); break;
                }
            }
            return r;
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public StandardOfLiving askStandardOfLiving()throws AskBreak{
        try{
            StandardOfLiving r;
            while (true){
                console.print("Standard of Living ("+ Arrays.toString(StandardOfLiving.names()));
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty() && (line.equals("ULTRA_HIGH") || line.equals("HIGH") || line.equals("LOW") || line.equals("NIGHTMARE"))) {
                        r = StandardOfLiving.valueOf(line); break;
                }
            }
            return r;
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public long askHuman() throws AskBreak{
        long age;
        while (true) {
            console.print("Enter age");
            var line = console.readln().trim();
            if (line.equals("exit")) throw new AskBreak();
            if (line.matches("^[0-9]+$") && Long.parseLong(line)> 0 && Long.parseLong(line) < 99) {
                age = Integer.parseInt(line); break;
            }
        }  return age;
    }
}