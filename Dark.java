import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Dark here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dark extends Theme
{
    public Dark(boolean store) {
        super("dark",4,store);
        price = 5000;
    }
    /**
     * Retrieves the name of the theme.
     * 
     * @return The name of the theme as a string.
     */
    public String getName() {
        return "Dark Mode";
    }
}
