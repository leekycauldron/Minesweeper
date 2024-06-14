import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.util.*;

public class Settings extends World
{
    private String mode;
    private GreenfootImage modeImage;
    private Mode modeDisplay;
    private SelectMenu themeMenu;
  
    
    public Settings()
    {    
        super(720, 600, 1); 

        
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
        
        themeMenu = new SelectMenu();
        addObject(themeMenu,getWidth()/2,getHeight()-125);
        themeMenu.prepare();
        
        Button save = new Button("save.png","saves.png");
        save.setOnClickAction(() -> {
           writeToFile("settings.txt",mode,themeMenu.getTheme());
           Greenfoot.setWorld(new Title());
        });
        addObject(save, centerX,getHeight()-50);
        
        
        
    }
    
      
    
    private void updateMode() {
        removeObject(modeDisplay);
        modeDisplay = new Mode(mode);
        addObject(modeDisplay,(getWidth() / 2) + 60,120);
    }
    
    private void writeToFile(String filePath, String mode,String theme) {
            Utils.changeLineValue(filePath,0,mode);
            Utils.changeLineValue(filePath,10,theme);
    }
}
