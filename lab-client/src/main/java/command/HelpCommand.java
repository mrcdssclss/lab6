package command;

import com.mrcdssclss.common.util.ConsoleManager;
import utility.ClientCommandManager;

public class HelpCommand extends ClientCommand {
    private final ConsoleManager console;
    private final ClientCommandManager clientCommandManager;
    public HelpCommand(ConsoleManager console, ClientCommandManager clientCommandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.clientCommandManager = clientCommandManager;
    }

    public boolean execute(String argument){
        StringBuilder a = new StringBuilder();
        for (int i = 0; i<10; i++){
            a.append("Серверные команды: ").append(clientCommandManager.getServerCommands().get(i)).append("\n");
        }
        if (!argument.isEmpty()){
            console.printError("Данная команда не имеет аргументов");
            return false;
        }
        clientCommandManager.getClientCommandMap().values()
                .forEach(command ->
                        console.println("Клиентские команды: " + command.getName() + " " + command.getDescription()));
        console.println(a);
        return true;
    }

}
