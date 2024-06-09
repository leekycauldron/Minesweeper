import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Mode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mode extends Actor
{
    /**
     * Act - do whatever the Mode wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Mode(String fname) {
        GreenfootImage img = new GreenfootImage("s"+fname+".png");
        img.scale(80, 45);
        setImage(img);
    }
}
