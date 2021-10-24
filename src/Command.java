import java.util.Arrays;
import java.util.List;

public class Command {

    private String userCommand; // the command that is issued by the user
    private static final List <String> gameCommandsList  = Arrays.asList("buy", "pass", "help", "roll" );

    public Command(String word){
        this.userCommand = word;

    }

    public String getUserCommand(){
        return userCommand;
    }

    // returns true if the provided command by the user is a valid command
    public boolean validCommands(String aCommand){
        return gameCommandsList.contains(aCommand);

    }

    // returns true if the command provided was invalid or not understood

    public boolean commandUnknown(){
        return (userCommand == null);
    }
    public static List<String> getCommands(){
        return gameCommandsList;
    }

}
