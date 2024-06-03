package command;
import java.io.IOException;

public interface ClientExecutable {
    boolean execute(String argument) throws IOException;
}
