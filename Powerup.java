import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.util.*;

/**
 * Unfortunately, this feature was NOT implemented due to time restrictions, but remains in the store and is 
 * still purchasable
 */

public class Powerup extends Actor
{
    private Runnable clickAction = null;
    private GreenfootImage img; 
    protected int price;
    private boolean owned = false;
    protected int index; // index of theme as it is saved in settings.txt
    private String fname;
    private Label amountLabel;
    
    public Powerup(String fname,int index) {
        img = new GreenfootImage(fname+".png");
        img.scale(200, 200);
        setImage(img);
        this.fname = fname;
        this.index = index;

        if(getBuyStatus(index) > 0) {
            owned = true;
        }
    }
    
    public void act() {
        if(owned) {
            getWorld().removeObject(amountLabel);
            amountLabel = new Label("x"+getBuyStatus(index),50);
            getWorld().addObject(amountLabel,getX(),getY()-100);
        }
        
        if (Greenfoot.mouseClicked(this)) {
            buy();
            if(clickAction != null) clickAction.run();
        }
    }
    
    public void buy() {
        if(Utils.getCoins() >= price) {
            Utils.updateCoins(-1 * price);
            updateBuyStatus(index);
            
            getWorld().removeObject(amountLabel);
            amountLabel = new Label("x"+getBuyStatus(index),50);
            getWorld().addObject(amountLabel,getX(),getY()-100);
        }
        
        owned = true;
    }
    
    private int getBuyStatus(int index) {
        try {
            return Integer.valueOf(Utils.getLineValue("settings.txt",index+2));
        }
        catch (IOException e ){
            return 0;
        }
    }
    
    private void updateBuyStatus(int lineIndex) {
        Utils.changeLineValue("settings.txt",lineIndex+2,String.valueOf(getBuyStatus(lineIndex)+1));
    }
    
    public void setOnClickAction(Runnable clickAction) {
        this.clickAction = clickAction;
    }
}
