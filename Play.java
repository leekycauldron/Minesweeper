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
    private int state = 0; // 0 = playing, -1 = lose, 1 win;
    private int flags; // Flags left.
    private Label flagLabel;
    private SimpleTimer timer = new SimpleTimer();
    private Label timeLabel;
    private State s;
    public Play(int width, int height)
    {    
        super(width, height+1, 25); 
        
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
        flags = bombCount;
        timeLabel = new Label("0",25);
        addObject(timeLabel,getWidth()-1,0);
        s = new State();
        addObject(s, getWidth()/2,0);
    }
    
    public void act() {
        removeObject(flagLabel);
        flagLabel = new Label(flags,25);
        addObject(flagLabel,0,0);
        
        if(state == 0 && started) {
            removeObject(timeLabel);
            int seconds = (int) (timer.millisElapsed() / 1000);
            timeLabel = new Label(seconds,25);
            addObject(timeLabel,getWidth()-1,0);
        }
        
        // Constantly check if flags are all placed right.
        if(flags == 0 && allCellsFlaggedOrRevealed()) {
            boolean done = true;
            for (int i = 0; i < grid.length; i++)
            {
                for (int j = 0; j < grid[i].length; j++)
                {
                    Cell cell = grid[i][j];
                    // If a cell was falsely flagged
                    if(cell.isFlagged() && !cell.hasBomb()) {
                        done = false;
                        break;
                    }
                }
                if(!done) break;
            }
            if(done) {
                state = 1;
                s.win();
            }
        }
    }
    
    public int getState() {
        return state;
    }
    
    public boolean allCellsFlaggedOrRevealed() {
    for (int x = 0; x < grid.length; x++) {
        for (int y = 0; y < grid[x].length; y++) {
            Cell cell = grid[x][y];
            if (!cell.isRevealed() && !cell.isFlagged()) {
                return false;
            }
        }
    }
    return true;
}

   
    
    public void restart() {
        Greenfoot.setWorld(new Play(grid.length,grid[0].length));
    }
    
    public boolean isStarted() {
        return started;
    }
    
    public void start(int startRow, int startCol) {
        timer.mark();
        started = true;
        int bombs = 0;
    
        while (bombs < bombCount) {
            int randomRow = Greenfoot.getRandomNumber(grid.length);
            int randomCol = Greenfoot.getRandomNumber(grid[0].length);
    
            // Check if the random cell is within two cells of the starting cell
            if (Math.abs(randomRow - startRow) <= 2 && Math.abs(randomCol - startCol) <= 2) {
                continue; // Skip placing a bomb in the 5x5 area around the starting cell
            }
    
            // Ensure the cell is neither revealed nor already has a bomb
            if (!grid[randomRow][randomCol].isRevealed() && !grid[randomRow][randomCol].hasBomb()) {
                grid[randomRow][randomCol].placeBomb(); 
                bombs++;
            }
        }
    }

    
    public void revealBombs()
    {
        state = -1;
        s.lose();
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
    
    public void placeFlag() {
        flags--;
    }
    
    public void removeFlag() {
        flags++;
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
                    if(!grid[adjY][adjX].isRevealed() && !grid[adjY][adjX].isFlagged()) {
                        grid[adjY][adjX].algoReveal();
                        checkAdjacentBombs(adjX,adjY);
                        
                    }
                } catch (ArrayIndexOutOfBoundsException  e){
                    
                }
        }
        }
        
        
    }
}
