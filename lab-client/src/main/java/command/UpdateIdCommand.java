package command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.classes.City;
import com.mrcdssclss.common.util.ConsoleManager;
import utility.Ask;
import utility.Client;

import java.io.IOException;

public class UpdateIdCommand extends ClientCommand {

    private final ConsoleManager console;
    private final Client client;

    public UpdateIdCommand(ConsoleManager console, Client client) {
        super("update_id ", "обновить элемент коллекции по id");
        this.client = client;
        this.console = console;
    }

    public boolean execute(String argument) {
        try {
            Ask ask = new Ask(console);
            City city = new City(
                    ask.getId(),
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
            Request request = new Request("update_id "+argument, city);
            client.sendRequest(request);
            return true;
        } catch (Ask.AskBreak e) {
            console.printError("Input interrupted.");
        } catch (IOException e) {
            console.printError("Ошибка при отправке запроса или получении ответа: " + e.getMessage());
        } catch (ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
