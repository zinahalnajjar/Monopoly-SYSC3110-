public class Command {

    private String userCommand; // the command that is issued by the user
    private static final String[] gameCommands = {"buy", "pass", "help", "roll"};

    public Command(String word){
        this.userCommand = word;

    }

    public String getUserCommand(){
        return userCommand;
    }

    // returns true if the provided command by the user is a valid command
    public boolean validCommands(String aCommand){
        for (int i =0; i<gameCommands.length; i++){
            if(!(gameCommands[i].equals(aCommand))) {
                return false;
            }
        }
       return true;
    }

    // returns true if the command provided was invalid or not understood

    public boolean commandUnknown(){
        return (userCommand == null);
    }

}
