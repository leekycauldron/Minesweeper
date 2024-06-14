import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.util.*;

public abstract class Theme extends Actor
{
    private Runnable clickAction = null;
    private GreenfootImage img; 
    protected int price;
    private boolean owned = false;
    protected int index; // index of theme as it is saved in settings.txt
    private String fname;
    private boolean store;
    
    public abstract String getName();
    
    
    /**
     * Constructs a new Theme object, setting its appearance based on ownership status.
     * If the theme is already owned, it displays an "owned" image, otherwise a standard image.
     *
     * @param fname the file name for the theme's image.
     * @param index the index of the theme in a collection or list.
     * @param store a boolean indicating if the theme is being accessed from the store.
     */

    public Theme(String fname,int index,boolean store) {
        this.store = store;
        this.fname = fname;
        if(!store) {
            img = new GreenfootImage(fname+"owned.png");
            img.scale(100, 100);
            setImage(img);
            return;
        };
        
        img = new GreenfootImage(fname+".png");
        img.scale(100, 100);
        setImage(img);
        
        
        this.index = index;

        if(getBuyStatus(index).equals("YES")) {
          
            img = new GreenfootImage(fname+"owned.png");
            img.scale(100, 100);
            setImage(img);
            owned = true;
        }
    }
    
    public void act() {
        if(!store)return;
        if (!owned && Greenfoot.mouseClicked(this)) {
            buy();
            if(clickAction != null) clickAction.run();
        }
    }
    
    /**
     * Attempts to purchase the theme if the player has enough coins. It updates the coin balance,
     * marks the theme as owned, and updates the theme's image to indicate ownership.
     */

    private void buy() {
        if(Utils.getCoins() >= price) {
            Utils.updateCoins(-1 * price);
            updateBuyStatus(index);
            img = new GreenfootImage(fname+"owned.png");
            img.scale(100, 100);
            setImage(img);
        }
        
        owned = true;
    }
    
    public String getFname() {
        return fname;
    }
    
    private String getBuyStatus(int index) {
        try {
            return Utils.getLineValue("settings.txt",index+2);
        }
        catch (IOException e ){
            return "ERR";
        }
    }
    
    private void updateBuyStatus(int lineIndex) {
        Utils.changeLineValue("settings.txt",lineIndex+2,"YES");
    }
    
    public void setOnClickAction(Runnable clickAction) {
        this.clickAction = clickAction;
    }
}
