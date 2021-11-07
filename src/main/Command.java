package main;

import java.util.Arrays;
import java.util.List;

/**
 * Class Command- Commands for monopoly game
 * this class is part of the "Monopoly Game" application
 *
 * this class holds inforamtion abouts commands that were issued by the user
 * the command consists of one word. It is checked for being valid or not
 *If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 * @author Zinah
 */

/**
 * constructor for commands
 * construct list of used commands in the game
 */

public class Command {

    private String userCommand; // the command that is issued by the user
    private static final List <String> gameCommandsList  = Arrays.asList("buy", "pass", "help", "roll", "quit", "status", "sell");

    /**
     * constructor
     * @param word
     */
    public Command(String word){
        this.userCommand = word;

    }

    /**
     *
     * @return userCommand
     */
    public String getUserCommand(){
        return userCommand;
    }

    /**
     * returns true if the provided command by the user is a valid command
     * @param aCommand
     * @return
     */

    public boolean validCommands(String aCommand){
        return gameCommandsList.contains(aCommand);

    }


    /**
     * returns true if the command provided was invalid or not understood
     * @return
     */
    public boolean commandUnknown(){
        return (userCommand == null);
    }
    public static List<String> getCommands(){
        return gameCommandsList;
    }

}
