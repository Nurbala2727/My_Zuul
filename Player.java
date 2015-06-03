import java.util.*;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    private String name;
    private int backPack;
    private int backPackContent;
    private Room currentRoom;  
    private Deque<String> roomHistory = new ArrayDeque<String>();
    private ArrayList<Item> inventory;
    
    /**
     * Constructor Player
     * Initialize remaining fields
     * @param name The name of the player
     * @param backPack the size of the backpack
       */
    public Player(String name, int backPack)
    {
        this.name = name;   
        this.backPack = backPack; 
        currentRoom = null; 
        inventory = new ArrayList<Item>(); 
    }
    
    /**
     * @param currentRoom changes the room the player is located at (currentRoom)
    */
    public void setRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }
    /**
     * @return Room currentRoom (room where the player is currently at)
    */
    public Room getRoom()
    {
        return currentRoom;
    }
    /**
     * @param Command command to be analized for directions, by looking at the second command 
    */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) 
        {
            System.out.println("There is no door!");
        }
        else if (nextRoom instanceof BurningRoom)
        {
           System.out.println(nextRoom.getLongDescription() + "\n \n");                  
           look();
        }
        else 
        {
            setRoom(nextRoom);
            printLocationInfo();
           
            Set<String> validDirections = new HashSet<String>();
            validDirections.add("north");
            validDirections.add("east");
            validDirections.add("south");
            validDirections.add("west");

            if (validDirections.contains(direction))
            {
                addToRoomHistory(direction);
            }
        }
    

    }
    /**
     * prints out the current location and the available exits
    */    
    public void printLocationInfo()
    {
        System.out.println(currentRoom.getLongDescription());
        System.out.println("");
    }
    
    /**
     * Add the direction the previous room is at to the room history stack
    */      
    private void addToRoomHistory(String direction)
    {
        
        roomHistory.push(reverseDirection(direction));
    }
    /**
       *@param String if given a valid direction reverse the direction of movement, else return null
    */
    private String reverseDirection(String direction)
    {
        switch(direction)
        {
            case "east": direction = "west";
            break;
            case "west": direction = "east";
            break;
            case "north": direction = "south";
            break;
            case "south": direction = "north";
            break; 
            default: return null;
        }
        
        return direction;
    }
    
    /**
       *go back to the previous room
    */    
    public void back() 
    {
        if(roomHistory.size() > 0)
        {
            String direction = roomHistory.pop();        
            
            // copied from goRoom (duplication)
            Room nextRoom = currentRoom.getExit(direction);
            if (nextRoom == null) {
                System.out.println("There is no door!");
            }
            else {
                setRoom(nextRoom);
                printLocationInfo();
            }
        }
        else 
        {
            System.out.println("you cannot go any farther back");
        }
        
        
    }
    
    /**
       *print relevant information of the current room 
    */
    public void look() // Lab 10, Exercise 3: Look command
    {
    System.out.println(currentRoom.getLongDescription()); 
    }
    
    /**
       *@param String try to pick up chosen item specified by its name
    */
    public void take(String name)
    {
        if ((name != null) && (currentRoom.getItem(name) != null) && ((currentRoom.getItem(name)).getPortable()) && (doesItFitInBackPack(name)))
        {
            inventory.add(currentRoom.getItem(name)); // add item to inventory
            updateBackPackContent(name, true); //update the content of the backPack (available space)
            currentRoom.removeItem(name); //remove item from room 
            System.out.println(name + " was taken");            
        }
        else if( (name != null) && (currentRoom.getItem(name) != null) && !((currentRoom.getItem(name)).getPortable()))
        {
                System.out.println("this item is to heavy and can not be picked up");
        }
        else if((name != null) && (currentRoom.getItem(name) != null) && !(doesItFitInBackPack(name)))
        {
            System.out.println("you need more space in your backpack");
        }
        else 
        {
            System.out.println("Nothing was taken");
        }
    } 
    /**
     * @param String name of the item to be dropped. If "all" is passed, every item is removed.
     */
    public void drop(String name) 
    {
        if(name.equals("all"))
            {
                dropAll();
            }
            else
            {
                dropSingle(name);
            }
    }
    
    /**
       *@param String drop chosen item specified by its name.
    */
    public void dropSingle(String name)
    {
        if ((name != null) && (getInventoryItem(name) != null))
        {
            currentRoom.addItem(getInventoryItem(name));
            updateBackPackContent(name, false); //update the content of the backPack (available space)
            inventory.remove(getInventoryItem(name));             
            System.out.println(name + " was dropped");
        }
        else 
        {
            System.out.println("Nothing was dropped");
        }
        
    }
    /**
     * removes every item from the inventory and stores them in the items Array of the current room
     */    
    public void dropAll()
    {
        if(inventory.size() > 0)
        {
            for(Item item: inventory)
            {
                currentRoom.addItem(item);
            }
            inventory.clear();
            backPackContent = 0;
            System.out.println("Every item in your inventory has been dropped to the room"); 
        }
        else
        System.out.println("you don't have any items in your inventory that could be dropped");
    }


    
    /**
       *@param String name of the item to be returned
       *@retunr item specified by its name
    */
    public Item getInventoryItem(String name)
    {
        for(int i = 0;  i < inventory.size() ; i++)
        {
            if(((inventory.get(i)).getName()).equals(name))
            {
                return inventory.get(i);
            }           
        }
        return null; 
    }
    
    /**
       *@param String name of the item 
       *@return true if there is enough space in the backpack
    */
    public boolean doesItFitInBackPack(String name)
    {
        if (backPackContent + currentRoom.getItem(name).getWeight() <= backPack)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
   
    /**
       *@param String name of the item whose weight is to be added to or substracted from the backpack field 
       *@param pickUp deternmines whether to add (true) or to remove (false) item weight from field 
    */
  
    public void updateBackPackContent(String name, boolean pickUp)
    {
        if(pickUp)
        {
            backPackContent = backPackContent + currentRoom.getItem(name).getWeight(); //add item weight to backPack
        }
        else
        {
            backPackContent = backPackContent - currentRoom.getItem(name).getWeight(); // substract item weight from backPack
        }
    }
    
    /**
       *print out the content of the inventory
    */
    public void printInventory()
    {
        System.out.println("used backpack capacity: " + backPackContent + "/" + backPack);
        
        if(inventory.size() > 0)
        {
            System.out.println("Items stored in your backpack:");
        }
        else 
        {
            System.out.println(" - your backpack is empty");
        }
        for(Item item : inventory)
        {
            System.out.println(" - " + item.getDetails());
        }
    }
    
    /**
       *@param String name of the item to be used
       *if there is any use for the item at the specific moment, the corresponding method will be executed
    */
    public void useItem(String name) // Lab 10, Exercise 4: Look command
    {
        
        if(getInventoryItem(name) != null)
        {
            switch(name)
            {
                case "large-backpack": useLargeBackPack(); 
                break;
                case "mystic-box": useMysticBox();
                break;
                default: System.out.println("there is no use for this");
            }
        }
        else 
        {
            System.out.println("item not found in inventory");
        }
        
    }
    
    /**
       *use the large backpack item and increase the maximum capacity of the inventory 
    */
    public void useLargeBackPack()
    {
        this.backPack = 70;
        inventory.remove(getInventoryItem("large-backpack"));
        printInventory();
    }
    
    /**
       *removes mystery-box from inventory
       *opens special room if used in "rightHallway"
    */
    public void useMysticBox()
    {
        String room = currentRoom.getName();
        System.out.println("you have used the mytic-box in the " + room + " a secret door has been unlocked!");   
        if(room.equals("rightHallway"))
        {
            Room secretRoom = new Room("secretRoom", "in the mysterious secret room");
            secretRoom.setExit("south", currentRoom);
            inventory.remove(getInventoryItem("mystic-box"));
            currentRoom.setExit("north", secretRoom);
            look();
        }
        
        else
        {
            System.out.println("nothing happens");
        }
    }
    
    
}
