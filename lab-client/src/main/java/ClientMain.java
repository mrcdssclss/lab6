
import com.mrcdssclss.common.util.CommandManager;
import com.mrcdssclss.common.util.ConsoleManager;
import command.*;
import utility.Client;
import java.net.IDN;
import java.util.regex.Pattern;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        Scanner scanner = new Scanner(System.in);
        var console = new ConsoleManager();
        System.out.println("введите хост: ");
        var host = scanner.nextLine();
        Client client = new Client(host, port);
        CommandManager commandManager = new CommandManager() {{
            ClientRegistration("add", new AddCommand(console, client));
            ClientRegistration("execute_script", new ExecuteScriptCommand(console));
            ClientRegistration("help", new HelpCommand(console, this));
            ClientRegistration("history", new HistoryCommand(console, this));
        }};
        client.start(commandManager);

    }
}



