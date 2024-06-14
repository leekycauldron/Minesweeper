import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Space here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Space extends Theme
{
    public Space(boolean store) {
        super("space",3,store);
        price = 2500;
    }
    
    /**
     * Retrieves the name of the theme.
     * 
     * @return The name of the theme as a string.
     */
    public String getName() {
        return "Eerie Space";
    }
}
