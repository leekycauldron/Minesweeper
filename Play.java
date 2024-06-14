import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
import java.io.*;


/**
 * Main play grid, changes based on difficulty level.
 * 
 * @author Bryson, Bonnie, Matthew David 
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
    private boolean addedCoins = false;
    private String theme;
    
    public Play(int width, int height)
    {    
        super(width, height+1, 25); 
 
        try {
            theme = Utils.getLineValue("settings.txt",10);
        } catch (IOException e){
            theme = "default";
        }
        
        
        
        // Populate grid with cells.
        grid = new Cell[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                grid[i][j] = new Cell(j,i);
                addObject(grid[i][j],i,j+1);
                grid[i][j].prepare();
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
            if(done && !addedCoins) {
                addedCoins = true;
                state = 1;
                int seconds = (int) (timer.millisElapsed() / 1000);
                int coinsEarned = 0;
                switch(grid.length) {
                    case 9:
                        coinsEarned = 500;
                        break;
                    case 16:
                        coinsEarned = 1000;
                        break;
                    case 30:
                        coinsEarned = 1500;
                }
                coinsEarned -= seconds;
                if(coinsEarned < 0) {
                    coinsEarned = 0;
                }
                Utils.updateCoins(coinsEarned);
                
                Label l = new Label("+"+coinsEarned+" coins." ,17);
                l.setFillColor(Color.BLACK);
                addObject(l,getWidth()/2+2,0);
                s.win();
                GreenfootSound sound = new GreenfootSound("win.wav");
                sound.play();
            }
        }
    }
    
    
    /**
     * Retrieves the current state of the game.
     * 
     * @return The current state as an integer.
     */
    public int getState() {
        return state;
    }
    
    
    /**
     * Retrieves the current theme.
     * 
     * @return The current theme as a string.
     */

    public String getTheme() {
        return theme;
    }
    
    
    /**
     * Checks if all cells in the grid are either flagged or revealed.
     * 
     * @return true if all cells are flagged or revealed, false otherwise.
     */

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

   
    /**
     * Restarts the game by creating a new play area with the same dimensions as the current grid.
     */

    public void restart() {
        Greenfoot.setWorld(new Play(grid.length,grid[0].length));
    }
    
    /**
     * Checks if the game has started.
     * 
     * @return true if the game has started, false otherwise.
     */

    public boolean isStarted() {
        return started;
    }
    
    
    /**
     * Starts the game by placing bombs on the grid, ensuring that the initial area around the specified start position
     * does not contain bombs. It also marks the start of the game's timer.
     *
     * @param startRow the row index of the starting cell.
     * @param startCol the column index of the starting cell.
     */

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

    /**
     * Reveals all bomb locations on the grid and changes the game state to indicate a loss. It plays a sound effect
     * when bombs are revealed. If a cell is flagged but does not contain a bomb, it updates the image to indicate a false flag.
     */

    public void revealBombs()
    {
        state = -1;
        s.lose();
        GreenfootSound sound = new GreenfootSound("lose.wav");
        
        sound.play();
        
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                Cell cell = grid[i][j];
                if (cell.hasBomb() && !cell.isRevealed())
                {
                    GreenfootSound pop = new GreenfootSound("pop.mp3");
                    sound.play();
                    cell.setImage(new GreenfootImage(getTheme()+"bomb.png")); // Show bomb image for actual bombs
                }
                else if (!cell.hasBomb() && cell.isFlagged())
                {
                    cell.setImage(new GreenfootImage(getTheme()+"notBomb.png")); // Show notBomb image for falsely flagged cells
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
    
    /**
     * Checks for bombs in adjacent cells around the specified coordinates and updates the display accordingly.
     * If bombs are found, it displays the count; if none are found, it recursively reveals adjacent cells.
     *
     * @param x the x-coordinate of the cell to check.
     * @param y the y-coordinate of the cell to check.
     */

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
