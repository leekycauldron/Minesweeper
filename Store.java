import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Store here.
 * 
 * @author Bryson, Bonnie, Matthew, David
 */
public class Store extends World
{
    private Label coinLabel;
    
    public Store()
    {    
        super(720, 600, 1); 
        
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(Color.BLACK);
        bg.fill();
        setBackground(bg);
        
        GreenfootImage titleImage = new GreenfootImage("store.png");
        titleImage.scale(160, 80);
        int x = (getWidth() - titleImage.getWidth()) / 2;
        int y = 1;
        getBackground().drawImage(titleImage, x, y);
        
        
        GreenfootImage themesImage = new GreenfootImage("themes.png");
        themesImage.scale(160, 90); 
        x = (getWidth() - themesImage.getWidth()) / 2 - 200;
        y = 80;
        getBackground().drawImage(themesImage, x, y);
        
        GreenfootImage powerImage = new GreenfootImage("powerups.png");
        powerImage.scale(160, 90); 
        x = (getWidth() - powerImage.getWidth()) / 2 - 200;
        y = 250;
        getBackground().drawImage(powerImage, x, y);
        
        GreenfootImage coinImage = new GreenfootImage("coin.png");
        coinImage.scale(25,25);
        getBackground().drawImage(coinImage, getWidth()-100,25);
        
        coinLabel = new Label(Utils.getCoins(), 30);
        addObject(coinLabel,getWidth()-50,37);
        
        displayThemes();
        displayPowers();
        
        Button save = new Button("save.png","saves.png");
        save.setOnClickAction(() -> {
           Greenfoot.setWorld(new Title());
        });
        addObject(save, getWidth()/2,getHeight()-50);
    }
    
    public void act() {
        removeObject(coinLabel);
        coinLabel = new Label(Utils.getCoins(), 30);
        addObject(coinLabel,getWidth()-50,37);
    }
    
    /**
     * Displays selectable game themes in a row. Each theme is represented by a square,
     * and they are displayed centered horizontally within the window.
     */
    public void displayThemes() {
        int squareSize = 110; // Size of each square
        int numberOfSquares = 5;
        int centerX = getWidth() / 2;
        int centerY = (getHeight() / 3);
        int totalWidth = numberOfSquares * squareSize;
        int startX = centerX - (totalWidth / 2) + (squareSize / 2);

        // Create different actors for each square
        Arctic arctic = new Arctic(true);
        City city = new City(true);
        Dark dark = new Dark(true);
        Desert desert = new Desert(true);
        Space space = new Space(true);

        // Add each square to the world at the calculated position
        addObject(desert, startX, centerY);
        addObject(arctic, startX + squareSize, centerY);
        addObject(city, startX + 2 * squareSize, centerY);
        addObject(space, startX + 3 * squareSize, centerY);
        addObject(dark, startX + 4 * squareSize, centerY);
    }
    
    /**
     * Displays selectable game powers in a grid layout. Each power is represented by a square,
     * and they are displayed centered horizontally with a slight vertical offset in the game window.
     */
    public void displayPowers() {
        int squareSize = 210; // Size of each square
        int numberOfSquares = 3;
        int centerX = getWidth() / 2;
        int centerY = (getHeight() / 2) + 120;
        int totalWidth = numberOfSquares * squareSize;
        int startX = centerX - (totalWidth / 2) + (squareSize / 2);

        // Create different actors for each square
        Burst burst = new Burst();
        Shield shield = new Shield();
        Xray xray = new Xray();

        // Add each square to the world at the calculated position
        addObject(burst, startX, centerY);
        addObject(shield, startX + squareSize, centerY);
        addObject(xray, startX + 2 * squareSize, centerY);
    }
    
}
