import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Cell here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cell extends Actor
{
    private boolean revealed = false;
    private boolean bomb = false;
    private boolean flagged = false;
    private int x;
    private int y;
    public Cell(int x, int y) {
        GreenfootImage img = getImage();
        img.scale(25,25);
        setImage(img);
        this.x = x;
        this.y = y;
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            
            if (mouse.getButton() == 1) // Left click
            {
                if (!revealed && !flagged)
                {
                    reveal();
                }
            }
            else if (mouse.getButton() == 3) // Right click
            {
                if (!revealed)
                {
                    toggleFlag();
                }
            }
        }
    }
    
    private void toggleFlag()
    {
        Play world = (Play) getWorld();
        if (flagged)
        {
            world.removeFlag();
            flagged = false;
            setImage(new GreenfootImage("cell.png")); // Change back to the default cell image
        }
        else
        {
            world.placeFlag();
            flagged = true;
            setImage(new GreenfootImage("flag.png")); // Change to the flagged image
        }
    }
    
    private void reveal()
    {
        
        revealed = true;
        Play world = (Play) getWorld();
        
        if (world.getState() != 0) {
            return;
        }
        if (!world.isStarted()) {
            world.start(y,x);
        }
        if (bomb)
        {
            setImage(new GreenfootImage("explode.png")); // Set the image to bomb
            
            world.revealBombs();
        }
        else
        {
            // Code to handle revealing the cell when it is not a bomb
            setImage(new GreenfootImage("revealed.png")); // Replace with your own revealed cell image
            
            world.checkAdjacentBombs(x,y);
        }
    }
    
    public void algoReveal() {
        revealed = true;
        setImage(new GreenfootImage("revealed.png"));
    }
    public void unReveal() {
        revealed = false;
    }

    public boolean isRevealed() {
        return revealed;
    }
    
    public boolean isFlagged() {
        return flagged;
    }
    
    public boolean hasBomb() {
        return bomb;
    }
    
    public void placeBomb() {
        bomb = true;
    }
}
