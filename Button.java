import greenfoot.*;

public class Button extends Actor {
    private GreenfootImage normalImage;   // Image for the normal (not hovered) state
    private GreenfootImage hoveredImage;  // Image for the hovered state
    private Runnable clickAction = null;
    
    /**
     * Constructs a new Button object with specified images for its normal and hovered states.
     * The images are scaled to fit the button size.
     *
     * @param normalImageFile the file path for the normal state image.
     * @param hoveredImageFile the file path for the hovered state image.
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
