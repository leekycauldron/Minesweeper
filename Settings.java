import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.util.*;

public class Settings extends World
{
    private String mode;
    private GreenfootImage modeImage;
    private Mode modeDisplay;
    
    public Settings()
    {    
        super(600, 400, 1); 
        
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(Color.BLACK);
        bg.fill();
        setBackground(bg);
        
        
        GreenfootImage titleImage = new GreenfootImage("Settings.png");
        int x = (getWidth() - titleImage.getWidth()) / 2;
        int y = 1;
        getBackground().drawImage(titleImage, x, y);
        
        GreenfootImage difficultyImage = new GreenfootImage("Difficulty.png");
        x = (getWidth() - difficultyImage.getWidth()) / 2 - 50;
        y = 75;
        getBackground().drawImage(difficultyImage, x, y);
        
        try (BufferedReader reader = new BufferedReader(new FileReader("settings.txt"))) {
            mode = reader.readLine().trim().toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        updateMode();
        
        
        Button easy = new Button("easy.png", "easys.png");
        Button medium = new Button("medium.png", "mediums.png");
        Button hard = new Button("hard.png", "hards.png");
        
        easy.setOnClickAction(() -> {           
            mode = "easy";
            updateMode();
        });
        medium.setOnClickAction(() -> {
            mode = "medium";
            updateMode();
        });
        hard.setOnClickAction(() -> {
            mode = "hard";
            updateMode();
        });
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int buttonSpacing = 20; // Space between buttons
        int yOffset = -30; // Variable to tweak the y location
        
        addObject(easy, centerX - easy.getImage().getWidth() - buttonSpacing, centerY + yOffset);
        addObject(medium, centerX, centerY + yOffset);
        addObject(hard, centerX + hard.getImage().getWidth() + buttonSpacing, centerY + yOffset);
        
        Button save = new Button("save.png","saves.png");
        save.setOnClickAction(() -> {
           writeModeToFile("settings.txt",mode);
           Greenfoot.setWorld(new Title());
        });
        addObject(save, centerX,getHeight()-50);
        
    }
    
    private void updateMode() {
        removeObject(modeDisplay);
        modeDisplay = new Mode(mode);
        addObject(modeDisplay,(getWidth() / 2) + 60,120);
    }
    
    private void writeModeToFile(String filePath, String mode) {
        try {
            // Read existing file contents
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (FileNotFoundException e) {
                // If the file doesn't exist, just start with an empty list
            }
    
            // Update the first line with the new mode
            if (!lines.isEmpty()) {
                lines.set(0, mode);
            } else {
                lines.add(mode);
            }
    
            // Write updated contents back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
