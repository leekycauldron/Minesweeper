import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class State here.
 * 
 * @author Bryson, Bonnie, Matthew, David
 */
public class State extends Actor
{
    
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            Play world = (Play) getWorld();
            world.restart();
        }
    }
    
    /**
     * Updates the display to show a loss image when the game is lost.
     */
    public void lose() {
        GreenfootImage image = new GreenfootImage("lose.png");
        setImage(image);
    }
    
    /**
     * Updates the display to show a win image when the game is won.
     */
    public void win() {
        GreenfootImage image = new GreenfootImage("win.png");
        setImage(image);
    }
}
