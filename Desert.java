import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Desert here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Desert extends Theme
{
    public Desert(boolean store) {
        super("desert",0,store);
        price = 2000;
    }
    
    /**
     * Retrieves the name of the theme.
     * 
     * @return The name of the theme as a string.
     */
    public String getName() {
        return "Deserted Field";
    }
}
