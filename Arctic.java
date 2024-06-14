import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arctic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arctic extends Theme
{
    public Arctic(boolean store) {
        super("arctic",1,store);
        price = 2000;
    }
    /**
     * Retrieves the name of the theme.
     * 
     * @return The name of the theme as a string.
     */

    public String getName() {
        return "Arctic Springs";
    }
}
