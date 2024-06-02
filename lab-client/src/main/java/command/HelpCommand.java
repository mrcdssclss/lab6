package command;

import com.mrcdssclss.common.util.ClientCommand;
import com.mrcdssclss.common.util.CommandManager;
import com.mrcdssclss.common.util.ConsoleManager;

public class HelpCommand extends ClientCommand {
    private final ConsoleManager console;
    private final CommandManager commandManager;
    public HelpCommand(ConsoleManager console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    public boolean execute(String argument){
        if (!argument.isEmpty()){
            console.printError("Данная команда не имеет аргументов");
            return false;
        }
        commandManager.getClientCommandMap().values()
                .forEach(command ->
                        console.println("Клиентские команды: " + command.getName() + " " + command.getDescription()));
        commandManager.getServerCommandMap().values()
                .forEach(command ->
                        console.println("Серверные команды: " + command.getName() + " " + command.getDescription()));
        return true;
    }

}
