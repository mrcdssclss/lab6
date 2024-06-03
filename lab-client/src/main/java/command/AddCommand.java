package command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;
import com.mrcdssclss.common.classes.City;
import com.mrcdssclss.common.util.ConsoleManager;
import utility.Ask;
import utility.Client;

import java.io.IOException;

public class AddCommand extends ClientCommand {
    private final ConsoleManager console;
    private final Client client;

    public AddCommand(ConsoleManager console, Client client) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.client = client;
        this.console = console;
    }

    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new IllegalArgumentException();
            Ask ask = new Ask(console);
            City city = new City(
                    ask.setId(),
                    ask.askName(),
                    ask.askCoordinates(),
                    ask.askCreationDate(),
                    ask.askArea(),
                    ask.askPopulation(),
                    ask.askMetersAboveSeaLevel(),
                    ask.askCarCode(),
                    ask.askGovernment(),
                    ask.askStandardOfLiving(),
                    ask.askHuman()
            );
            Request request = new Request("add", city);
            client.sendRequestVoid(request);
            return true;
        } catch (IllegalArgumentException e) {
            console.printError("Использование аргумента '" + argument + "' в команде '" + getName() + "'");
        } catch (Ask.AskBreak e) {
            console.printError("Input interrupted.");
        } catch (IOException e) {
            console.printError("Ошибка при отправке запроса или получении ответа: " + e.getMessage());
        }
        return false;
    }
}
