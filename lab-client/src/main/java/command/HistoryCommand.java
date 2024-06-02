package command;

import com.mrcdssclss.common.util.ClientCommand;
import com.mrcdssclss.common.util.CommandManager;
import com.mrcdssclss.common.util.ConsoleManager;
import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;

import java.util.List;

public class HistoryCommand extends ClientCommand {
    private final CommandManager commandManager;
    private final ConsoleManager console;

    public HistoryCommand(ConsoleManager console, CommandManager commandManager) {
        super("history", " вывести последние 12 команд (без их аргументов)");
        this.commandManager = commandManager;
        this.console = console;
    }
    public boolean execute(String args) throws IllegalArgumentException {
        if (!args.isBlank()) throw new IllegalArgumentException();
        List<String> history= commandManager.getCommandHistory();
        for (String command:history.subList(Math.max(history.size() - 12, 0), history.size())){
            console.println(command);
        }
        return true;
    }

}
