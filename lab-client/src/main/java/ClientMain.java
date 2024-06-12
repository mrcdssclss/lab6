
import com.mrcdssclss.common.util.ConsoleManager;
import command.*;
import utility.Client;
import utility.ClientCommandManager;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws IOException{
        int port = 8080;
        Scanner scanner = new Scanner(System.in);
        var console = new ConsoleManager();
        System.out.println("введите хост: ");
        var host = scanner.nextLine().toLowerCase().trim();
        Client client = new Client(host, port);
        ClientCommandManager clientCommandManager = new ClientCommandManager() {{
            ClientRegistration("add", new AddCommand(console, client));
            ClientRegistration("update_id", new UpdateIdCommand(console, client));
            ClientRegistration("execute_script", new ExecuteScriptCommand(console));
            ClientRegistration("help", new HelpCommand(console, this));
        }};
        clientCommandManager.ServerRegistration();
        client.start(clientCommandManager);

    }
}



