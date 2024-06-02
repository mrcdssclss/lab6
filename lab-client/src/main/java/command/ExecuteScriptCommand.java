package command;

import com.mrcdssclss.common.util.ConsoleManager;
import com.mrcdssclss.common.util.ClientCommand;


public class ExecuteScriptCommand extends ClientCommand {

    private final ConsoleManager console;

    public ExecuteScriptCommand(ConsoleManager console) {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла (в скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме)");
        this.console = console;
    }

    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new IllegalArgumentException();
            console.println("Выполнение скрипта '" + argument + "'...");
            return true;
        } catch (IllegalArgumentException exception) {
            console.printError("Нет аргументов для исполнения команды '" + getName() + "'");
        }
        return false;
    }
}