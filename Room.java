import java.util.HashMap;
import java.util.ArrayList;
 /** Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    protected String description;
    protected  String name;
    protected HashMap<String, Room> exits; 
    protected ArrayList<Item> items; 
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String name, String description) 
    {
        this.description = description;
        this.name = name;
        exits = new HashMap<String, Room>(); 
        items = new ArrayList<Item>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    /**
    * @return String containing all relevant information of the items in the room
    */   
    public String getItemInformation()
    {
        String itemOut = "available items: \n" + " ";
        for(int i = 0 ; i < items.size() ; i++)
        {
            itemOut = itemOut + (items.get(i)).getDetails() + "\n" + " ";
        }
        
        return itemOut;
    }
    
        /**
    * Return a long description of this room, of the form:
    * You are in the kitchen.
    * Exits: north west
    * available items: pan, weight: 1
    * @return A description of the room, including exits and Items.
    */
    public String getLongDescription() // Lab 10, Exercise 3: Look command
    { 
        return "You are " + description + ".\n" + getExitString() + ".\n" + (items.size() > 0 ? getItemInformation() : "");
    }
    
    /**
     * @param String containing the direction the player is going 
     * @return Room that the player just entered
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);    
    }
    
    /**
    * Return a description of the room’s exits,
    * for example, "Exits: north west".
    * @return A description of the available exits.
    */
    public String getExitString()
    {      
        String exit = "Exits ";
        for(String key : exits.keySet())
        {
            exit = exit + " " + key;
        }
        
        return exit; 
        
    }
    /**
     * @param String descripton of the item to be created
     * @param int weight of the item to be created
     * @param String name of the item to be created
     * @param boolean determines if the item can be carried by the player
     * finally adds the item to the room
     * 
     */
    public void addItem(String description, int weight, String name, boolean portable)
    {
        Item item = new Item(description, weight, name, portable);
        items.add(item);
    }
    /**
     * @param Item to be added to the room
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    /**
     * @param String name of the item to be returned
     * @return the item if found, else return null
     */
    public Item getItem(String name)
    {
        for(int i = 0;  i < items.size() ; i++)
        {
            if(((items.get(i)).getName()).equals(name))
            {
                return items.get(i);
            }           
        }
        return null; 
    }
    /**
     * @param String name of the item to be removed from the room
     */
    public void removeItem(String name)
    {
        for(int i = 0 ; i < items.size() ; i++)
      {
            if(((items.get(i)).getName()).equals(name))
            {
                items.remove(items.get(i));            
            }
        }
        
    }
    /**
     * @return the name of the room
     */
    public String getName()
    {
        return this.name;
    }
    
}
