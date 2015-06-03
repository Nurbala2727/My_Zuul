

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RoomTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class RoomTest
{
    private Item item1;
    private Item item2;
    private Item item3;
    private Room room1;

    
    
    
    

    

    /**
     * Default constructor for test class RoomTest
     */
    public RoomTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        item1 = new Item("test1", 10, "testing item", true);
        item2 = new Item("test", 11, "testing item 2", true);
        item3 = new Item("test3", 12, "testing item 3", true);
        room1 = new Room("testing room", "room for testing item funtionality");

    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testItemFuntionality()
    {
        room1.addItem(item1);
        room1.addItem(item2);
        room1.addItem(item3);
        assertEquals("test1", item1.getName());
        assertEquals(10, item1.getWeight());
        assertEquals(true, item1.getPortable());
        assertEquals("testing item", item1.getDescription());

    }







    @Test
    public void testStringOfItems()
    {
        room1.addItem(item3);
        assertEquals("available items: \n test3   -> testing item 3, weight: 12\n ", room1.getItemInformation());
    }
}





