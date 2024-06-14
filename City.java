import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class City here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class City extends Theme
{
    public City(boolean store) {
        super("city",2,store);
        price = 2500;
    }
    /**
     * Retrieves the name of the theme.
     * 
     * @return The name of the theme as a string.
     */
    public String getName() {
        return "Bustling City";
    }
}
