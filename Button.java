import greenfoot.*;

public class Button extends Actor {
    private GreenfootImage normalImage;   // Image for the normal (not hovered) state
    private GreenfootImage hoveredImage;  // Image for the hovered state
    private Runnable clickAction = null;
    
    /**
     * Constructor to initialize the button with images for both states.
     */
    public Button(String normalImageFile, String hoveredImageFile) {
        // Load the images from the files
        normalImage = new GreenfootImage(normalImageFile);
        hoveredImage = new GreenfootImage(hoveredImageFile);
        
        normalImage.scale(96, 54);
        hoveredImage.scale(96, 54);
        
        // Set the initial image to the normal state
        setImage(normalImage);
    }
    
    /**
     * Act method to check for mouse hover state.
     */
    public void act() {
        if (Greenfoot.mouseMoved(this)) {
            // Change to the hovered image if the mouse is over the button
            setImage(hoveredImage);
        } else if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)) {
            // Change to the normal image if the mouse moved away from the button
            setImage(normalImage);
        }
        
        // Add other button behavior, like handling clicks, here if needed
        if (Greenfoot.mouseClicked(this)) {
            // Handle button click if needed
            clickAction.run();

        }
    }
    
    public void setOnClickAction(Runnable clickAction) {
        this.clickAction = clickAction;
    }
}
