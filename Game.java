import java.util.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private CommandWords cw = new CommandWords();
    private Room yourRoom, leftHallway, largeSickroom, drLounge, office, centralHallway,
                 jimsRoom, storage, empty, rightHallway, downstairs, transporter;         
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player("Daniel", 45);
        createRooms();
        parser = new Parser();
    }
    
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }

    /**
     * Create all the rooms and link their exits together.
     * Initilize Items
     * place player into the starting room
     */
    private void createRooms()
    {
                     
        // create the rooms
        yourRoom = new Room("yourRoom", "in your room");
        leftHallway = new Room("leftHallway", "in left hallway");
        largeSickroom = new Room("largeSickroom", "in the large sickRoom, empty beds everywhere");
        drLounge = new Room("drLounge", "in Doctor's lounge");
        office = new Room("office", "in Dr. Hanks office");
        centralHallway = new Room("centralHallway", "in central hallway");
        jimsRoom = new Room("jimsRoom", "in Jims very messy room");
        storage = new Room("storage", "in the storage room");
        empty = new Room("empty", "in the empty room, nobody here - just a bed");
        rightHallway = new Room("rightHallway", "in right hallway");
        downstairs = new Room("downstairs", "you have entered the lower level - you escaped the fire. \n You've mastered the first level! \n (more is coming)");
        transporter = new BurningRoom("burning room", "you cannot enter, there is too much fire");
        
        // initialise room exits       
        yourRoom.setExit("east", leftHallway);
        
        leftHallway.setExit("north", drLounge); 
        leftHallway.setExit("east", centralHallway);
        leftHallway.setExit("south", largeSickroom);
        leftHallway.setExit("west", yourRoom);
        
        largeSickroom.setExit("north", leftHallway);
        largeSickroom.setExit("west", transporter);
        
        drLounge.setExit("south", leftHallway);
        drLounge.setExit("east", office);
        
        office.setExit("west", drLounge);
        
        centralHallway.setExit("down", downstairs);
        centralHallway.setExit("east", rightHallway);
        centralHallway.setExit("west", leftHallway);
        centralHallway.setExit("north", empty);
        centralHallway.setExit("south", jimsRoom);
        
        jimsRoom.setExit("north", centralHallway);
        
        storage.setExit("west", rightHallway);
        
        empty.setExit("south", centralHallway);
        
        
        rightHallway.setExit("east", storage);
        rightHallway.setExit("west", centralHallway);
             
        //downstairs has no exits currently --> End of Game (so far)
        
        
        
        //initialize items
        yourRoom.addItem("syringe", 11, "a syringe with a needle", true);
        yourRoom.addItem("paper", 7, "a simple sheet sheet of paper", true);
        
        drLounge.addItem("crower", 23, "old rusty crower", true);
        
        largeSickroom.addItem("medication", 9, "unlabled bottle of medication", true);
        
        office.addItem("card", 6, "weird black card with numbers on it", true);
        
        jimsRoom.addItem("key", 8, "a simple small key", true);
        jimsRoom.addItem("large-backpack", 0, "A large backpack. Maybe I can use this to carry more items?", true);
        jimsRoom.addItem("cheese", 12, "an old cheese. I don't think its eatable anymore", true);
        jimsRoom.addItem("pencil", 9, "a simple pencil. It needs to be sharpened", true);
        jimsRoom.addItem("mystic-box", 43, "a large black box with cryptic signs on it", true);
        
        storage.addItem("fire-extinguisher", 31, "a fire-extinguisher - seriously? the hospital is burning down an nobody used this fire ditinguisher yet?", true);
        storage.addItem("rake", 20, "an old rake. I wonder why a hospital would need that", true);
        storage.addItem("router", 14, "a simple router with wifi", true);
        storage.addItem("card-game", 11, "32 cards", true);
        storage.addItem("ambos", 100, "ambos - seems a little heavy", false);
        storage.addItem("ethernet-cable", 10, "white ethernet cable. A little twisted but should work.", true);
        
        empty.addItem("decoder", 17, "a decoder for some kind of card", true); 
        
        player.setRoom(yourRoom);                
    }
    
   
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        player.printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            player.goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("look")){ // Lab 10, Exercise 3: Look command
            player.look();
        }
        else if(commandWord.equals("take")){
            player.take(secondWord);
        }
        else if(commandWord.equals("drop")){
            player.drop(secondWord);
        }
        else if(commandWord.startsWith("inventory")){
            player.printInventory();
        }
       else if(commandWord.startsWith("use")){ // Lab 10, Exercise 4: eat command
    
            player.useItem(secondWord);
            /*if(secondWord.equals("mystic-box") && player.getRoom() == rightHallway && player.getInventoryItem("mystic-box") != null)
            {
                rightHallway.setExit("north", secret);
            }*/
        }
        else if(commandWord.startsWith("back")){
            player.back();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() // Lab 10, Exercise 5: improved version of printing out the command words 
    {
        System.out.println("You are lost. You are alone. Fire everywhere - You wander");
        System.out.println("around at the hospital.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println(cw.getCommandString());
    }



    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

}
