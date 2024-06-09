import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Title Screen where user can adjust game settings.
 * 
 * @author Bryson, Bonnie, Matthew, David
 */
public class Title extends World
{
    String difficulty = "";
    
    public Title()
    {    
        super(600, 400, 1); 
        
        prepare();
    }
    
    private void prepare() {
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(Color.BLACK);
        bg.fill();
        setBackground(bg);

        GreenfootImage titleImage = new GreenfootImage("title.png");
        titleImage.scale(320, 180); 
        int x = (getWidth() - titleImage.getWidth()) / 2;
        int y = 1;
        getBackground().drawImage(titleImage, x, y);

        Button play = new Button("1.png", "2.png");
        Button store = new Button("5.png", "6.png");
        Button settings = new Button("3.png", "4.png");

        play.setOnClickAction(() -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader("settings.txt"))) {
                        difficulty = reader.readLine().trim().toLowerCase();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Set the world based on the difficulty
                    switch (difficulty) {
                        case "easy":
                            Greenfoot.setWorld(new Play(9, 9));
                            break;
                        case "medium":
                            Greenfoot.setWorld(new Play(16, 16));
                            break;
                        case "hard":
                            Greenfoot.setWorld(new Play(30, 16));
                            break;
                        default:
                            // Handle unexpected difficulty levels, if needed
                            throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
                    }

            });
        store.setOnClickAction(() -> {
                    Greenfoot.setWorld(new Store()); 
            });

        settings.setOnClickAction(() -> {
                    Greenfoot.setWorld(new Settings()); 
            });

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int buttonSpacing = 20; // Space between buttons

        int verticalOffset = 50; // Move buttons down by 50 pixels

        addObject(play, centerX, centerY - play.getImage().getHeight() - buttonSpacing + verticalOffset);
        addObject(store, centerX, centerY + verticalOffset);
        addObject(settings, centerX, centerY + settings.getImage().getHeight() + buttonSpacing + verticalOffset);

    }
}
