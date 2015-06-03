/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "back", "look", "take", "drop", "inventory", "use", "quit", "help" // Lab 10, Exercise 3: Look command + Lab 10, Exercise 4: eat command
    
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    
        /**
    * Return all commands in one single String seperated by spaces.
    */
    public String getCommandString() // Lab 10, Exercise 5: improved version of printing out the command words 
    {
        String commandWords = ""; 
        for(String command : validCommands) {
           commandWords = commandWords + " " + command;
        }
        return commandWords;
    }
}
