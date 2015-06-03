
/**
 * Write a description of class TransporterRoom here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BurningRoom extends Room
{


    /**
     * Constructor for objects of class TransporterRoom
     */
    public BurningRoom(String name, String description)
    {
        super(name, description);
    }
    
    public String getLongDescription()
    { 
        return  name + " - " + description;
    }


}
