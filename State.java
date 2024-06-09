import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class State here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class State extends Actor
{
    /**
     * Act - do whatever the State wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            Play world = (Play) getWorld();
            world.restart();
        }
    }
    
    public void lose() {
        GreenfootImage image = new GreenfootImage("lose.png");
        setImage(image);
    }
    
    public void win() {
        GreenfootImage image = new GreenfootImage("win.png");
        setImage(image);
    }
}
