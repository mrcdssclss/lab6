import com.mrcdssclss.common.util.CommandManager;
import com.mrcdssclss.common.util.ConsoleManager;
import com.mrcdssclss.server.Server;
import com.mrcdssclss.server.command.*;
import com.mrcdssclss.server.managers.CollectionManager;
import com.mrcdssclss.server.managers.FileManager;
import com.mrcdssclss.common.util.Runner;

import java.io.IOException;

public class ServerMain {
    static int port = 8080;
    static ConsoleManager console = new ConsoleManager();

    public static void main(String[] args) {

        if (args == null || args.length == 0 || args[0].isEmpty()){
            System.err.println("ты что самый умный?? где норм файл??");
            System.exit(-1);
        }
        FileManager fileManager = new FileManager(args[0], console);
        CollectionManager collectionManager = new CollectionManager(fileManager);

        CommandManager commandManager = new CommandManager() {{
            ServerRegistration("clear", new ClearCommand(collectionManager));
            ServerRegistration("count_greater_than_meters_above_sea_level", new CountGreaterCommand());
            ServerRegistration("filter_by_name", new FilterCommand());
            ServerRegistration("min_By_Standard_Of_Living", new MinByCommand());
            ServerRegistration("remove_greater", new RemoveGreaterCommand(collectionManager));
            ServerRegistration("remove_head", new RemoveHeadCommand(collectionManager));
            ServerRegistration("save", new SaveCommand(fileManager, collectionManager));
            ServerRegistration("info", new InfoCommand(collectionManager));
            ServerRegistration("show", new ShowCommand(collectionManager));
            ServerRegistration("update_id", new UpdateIdCommand(collectionManager));
        }};

        Runner runner = new Runner(commandManager);
        try {
            Server server = new Server(port, runner);
            server.start();
        } catch (IOException e) {
            System.err.println("нe удалось запустить клиент ");
            e.printStackTrace();
        }
    }
}
