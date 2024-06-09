import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;


/**
 * Write a description of class Play here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Play extends World
{

    private Cell[][] grid;
    private int bombCount; 
    private boolean started = false; // Starts when the first cell is revealed.
    private boolean lose = false;
    public Play(int width, int height)
    {    
        super(width, height+1, 25); 
        System.out.println(getWidth());
        System.out.println(getHeight());
        
        // Populate grid with cells.
        grid = new Cell[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                grid[i][j] = new Cell(j,i);
                addObject(grid[i][j],i,j+1);
            }
        }
        // Set number of bombs to be spawned based on difficulty.
        switch(width) {
            case 9:
                bombCount = 10;
                break;
            case 16:
                bombCount = 40;
                break;
            case 30:
                bombCount = 99;
        }
    }
    
    public boolean isLose() {
        return lose;
    }
    
    
    public boolean isStarted() {
        return started;
    }
    
    public void start() {
        started = true;
        int bombs = 0;
        while (bombs < bombCount) {
            int randomRow = Greenfoot.getRandomNumber(grid.length);
            int randomCol = Greenfoot.getRandomNumber(grid[0].length);
            
            if(!grid[randomRow][randomCol].isRevealed() && !grid[randomRow][randomCol].hasBomb()) {
                grid[randomRow][randomCol].placeBomb(); 
                bombs++;
            }
        }
    }
    
    public void revealBombs()
    {
        lose = true;
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                Cell cell = grid[i][j];
                if (cell.hasBomb() && !cell.isRevealed())
                {
                    cell.setImage(new GreenfootImage("bomb.png")); // Show bomb image for actual bombs
                }
                else if (!cell.hasBomb() && cell.isFlagged())
                {
                    cell.setImage(new GreenfootImage("notBomb.png")); // Show notBomb image for falsely flagged cells
                }
            }
        }
    }
    
    public void checkAdjacentBombs(int x, int y){
        int adjBombs = 0;
        List<int[]> adjacentCoordinates = new ArrayList<>();
        
        // Adding all 8 possible adjacent positions
        adjacentCoordinates.add(new int[]{x - 1, y - 1}); // Top-left
        adjacentCoordinates.add(new int[]{x, y - 1});     // Top
        adjacentCoordinates.add(new int[]{x + 1, y - 1}); // Top-right
        adjacentCoordinates.add(new int[]{x - 1, y});     // Left
        adjacentCoordinates.add(new int[]{x + 1, y});     // Right
        adjacentCoordinates.add(new int[]{x - 1, y + 1}); // Bottom-left
        adjacentCoordinates.add(new int[]{x, y + 1});     // Bottom
        adjacentCoordinates.add(new int[]{x + 1, y + 1}); // Bottom-right
        
        for(int[]coord : adjacentCoordinates) {
            int adjX = coord[0];
            int adjY = coord[1];
            try {
                
                if(grid[adjY][adjX].hasBomb()) {
                    adjBombs++;
                } 
                
            } catch (ArrayIndexOutOfBoundsException  e){
                
            }
        }
        
        if(adjBombs > 0) {
            // Write number
            
            Label num = new Label(adjBombs,25);
            addObject(num,y,x+1);
        } else {
            for(int[]coord : adjacentCoordinates) {
                int adjX = coord[0];
                int adjY = coord[1];
                try {  
                    if(!grid[adjY][adjX].isRevealed()) {
                        grid[adjY][adjX].algoReveal();
                        checkAdjacentBombs(adjX,adjY);
                        
                    }
                } catch (ArrayIndexOutOfBoundsException  e){
                    
                }
        }
        }
        
        
    }
}