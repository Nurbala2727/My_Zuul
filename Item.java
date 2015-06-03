
/**
 * Write a description of class item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    //fields
    String description, name;
    int weight;
    boolean portable; 
    
    public Item(String name, int weight, String description, boolean portable)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
        this.portable = portable;
    }
    
    public Item(Item item)
    {
        this.description = item.getDescription();
        this.weight = item.getWeight();
        this.name = item.getName();
        this.portable = item.getPortable();
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getDetails()
    {
        return name + "   -> " + description + ", weight: " + weight; 
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean getPortable()
    {
        return portable;
    }
    
    
    

}
