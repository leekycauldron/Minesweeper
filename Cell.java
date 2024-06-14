import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Cell here.
 * 
 * @author Bryson, Bonnie, Matthew, David
 */
public class Cell extends Actor
{
    private boolean revealed = false;
    private boolean bomb = false;
    private boolean flagged = false;
    private int x;
    private int y;
    
    /**
     * Constructs a new Cell object with specified coordinates.
     *
     * @param x the x-coordinate of the cell.
     * @param y the y-coordinate of the cell.
     */

    public Cell(int x, int y) {
        
        this.x = x;
        this.y = y;
    }
    
    public void prepare() {
        Play world = (Play) getWorld();
        GreenfootImage img = new GreenfootImage(world.getTheme()+"cell.png");
        img.scale(25, 25);
        setImage(img);
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            GreenfootSound sound = new GreenfootSound("tick.wav");
            sound.play();

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
            setImage(new GreenfootImage(world.getTheme()+"cell.png")); // Change back to the default cell image
        }
        else
        {
            world.placeFlag();
            flagged = true;
            setImage(new GreenfootImage(world.getTheme()+"flag.png")); // Change to the flagged image
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
            setImage(new GreenfootImage(world.getTheme()+"explode.png")); // Set the image to bomb
            
            world.revealBombs();
        }
        else
        {
            // Code to handle revealing the cell when it is not a bomb
            setImage(new GreenfootImage(world.getTheme()+"revealed.png")); // Replace with your own revealed cell image
            
            world.checkAdjacentBombs(x,y);
        }
    }
    
    /**
     * Reveals the cell and updates its image based on the current theme.
     */
    public void algoReveal() {
        revealed = true;
        Play world = (Play) getWorld();
        setImage(new GreenfootImage(world.getTheme()+"revealed.png"));
    }
    
    /**
     * Hides the cell by marking it as not revealed.
     */
    public void unReveal() {
        revealed = false;
    }

    /**
     * Checks if the cell is currently revealed.
     * 
     * @return true if the cell is revealed, false otherwise.
     */
    public boolean isRevealed() {
        return revealed;
    }
    
    /**
     * Checks if the cell is flagged.
     * 
     * @return true if the cell is flagged, false otherwise.
     */
    public boolean isFlagged() {
        return flagged;
    }
    
    /**
     * Checks if the cell contains a bomb.
     * 
     * @return true if the cell contains a bomb, false otherwise.
     */
    public boolean hasBomb() {
        return bomb;
    }
    
    /**
     * Places a bomb in the cell.
     */
    public void placeBomb() {
        bomb = true;
    }
}
