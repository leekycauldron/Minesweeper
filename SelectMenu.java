import greenfoot.*;
import java.io.*;
import java.util.*;

public class SelectMenu extends Actor {
    private List<Theme> themes;
    private int currentThemeIndex;
    private Label themeLabel;
    private Button backButton;
    private Button forthButton;

    public SelectMenu() {
        // Initialize themes list
    }
    
    public void prepare() {
        themes = new ArrayList<>();
        
        // Load owned themes from the settings file
        loadOwnedThemes("settings.txt");

        // Add the default theme at the beginning
        themes.add(new Default());

        // Create the theme label to display the current theme name
        themeLabel = new Label("Default", 24); // Label class with font size 24
        getWorld().addObject(themeLabel, getX(), getY() - 50); // Positioning above the menu
        
        // Create Back and Forth buttons
        backButton = new Button("back_normal.png", "back_hover.png");
        forthButton = new Button("forth_normal.png", "forth_hover.png");
        
        // Add buttons to the world and position them
        getWorld().addObject(backButton, getX() - 100, getY());
        getWorld().addObject(forthButton, getX() + 100, getY());

        // Set button click actions
        backButton.setOnClickAction(() -> selectPreviousTheme());
        forthButton.setOnClickAction(() -> selectNextTheme());

        // Initialize the current theme index to the first theme
        currentThemeIndex = 0;
        updateThemeDisplay();
    }

    /**
     * Load the owned themes from the settings file.
     */
    private void loadOwnedThemes(String filePath) {
        try {
            if ("YES".equals(Utils.getLineValue(filePath, 2))) {
                themes.add(new Desert(false));
            }
            if ("YES".equals(Utils.getLineValue(filePath, 3))) {
                themes.add(new Arctic(false));
            }
            if ("YES".equals(Utils.getLineValue(filePath, 4))) {
                themes.add(new City(false));
            }
            if ("YES".equals(Utils.getLineValue(filePath, 5))) {
                themes.add(new Space(false));
            }
            if ("YES".equals(Utils.getLineValue(filePath, 6))) {
                themes.add(new Dark(false));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Select the previous theme in the list, looping to the end if at the start.
     */
    private void selectPreviousTheme() {
        currentThemeIndex = (currentThemeIndex - 1 + themes.size()) % themes.size();
        updateThemeDisplay();
    }

    /**
     * Select the next theme in the list, looping to the start if at the end.
     */
    private void selectNextTheme() {
        currentThemeIndex = (currentThemeIndex + 1) % themes.size();
        updateThemeDisplay();
    }
    
    /**
     * Retrieves the file name of the current theme.
     * 
     * @return The file name of the current theme as a string.
     */

    public String getTheme() {
        return themes.get(currentThemeIndex).getFname();
    }

    /**
     * Update the theme display to show the currently selected theme.
     */
    private void updateThemeDisplay() {
        Theme currentTheme = themes.get(currentThemeIndex);
        getWorld().removeObject(themeLabel);
        themeLabel = new Label (currentTheme.getName(),24);
        getWorld().addObject(themeLabel, getX(), getY() - 50);
        // Optionally, you can update other parts of the UI to reflect the selected theme
    }

   
}
