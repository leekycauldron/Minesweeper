import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils extends Actor
{
    
    /**
     * Retrieves the current number of coins from a specified file. It reads the value from the second line
     * of the file ("settings.txt"). If there is an error reading the file or if the line cannot be parsed to an integer,
     * it returns 0.
     *
     * @return The number of coins, or 0 in case of an error or if the line is empty.
     */

    public static int getCoins() {
        BufferedReader reader = null;
        try {

            return Integer.parseInt(getLineValue("settings.txt",1));
            
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0; // Default to 0 if there's an error or if the line is empty
    }
    
    /**
     * Updates the number of coins by adding a specified amount. This could be positive (gaining coins)
     * or negative (spending coins).
     *
     * @param coinsToAdd the number of coins to add to the current count. This value can be negative.
     */

    public static void updateCoins(int coinsToAdd) {
        int currentCoins = getCoins();
        int newCoinCount = currentCoins + coinsToAdd;
        saveCoins(newCoinCount);
    }
    
    private static void saveCoins(int coins) {
        changeLineValue("settings.txt",1,String.valueOf(coins));
    }
    
    /**
     * Modifies a specific line in a file to a new value. If the specified line number exceeds the number of lines
     * in the file, an exception is thrown.
     *
     * @param filePath the path to the file.
     * @param lineNumber the line number to modify (zero-based index).
     * @param newLineValue the new value to set at the specified line.
     * @throws IOException if there's an error reading from or writing to the file.
     * @throws IllegalArgumentException if the specified line number does not exist in the file.
     */

     public static void changeLineValue(String filePath, int lineNumber, String newLineValue) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        if (lineNumber > lines.size()) {
            throw new IllegalArgumentException("Line " + lineNumber + " does not exist.");
        }

        lines.set(lineNumber, newLineValue);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieves the value of a specific line from a text file.
     * 
     * @param filePath   the path to the text file
     * @param lineNumber the line number to retrieve (1-based index)
     * @return the content of the specified line
     * @throws IOException if an I/O error occurs or the line number is invalid
     */
    public static String getLineValue(String filePath, int lineNumber) throws IOException {
        if (lineNumber <= 0) {
            throw new IllegalArgumentException("Line number must be greater than 0.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 0;

            while ((line = reader.readLine()) != null) {
                currentLine++;
                if (currentLine == lineNumber+1) {
                    return line;
                }
            }

            throw new IOException("Line " + lineNumber + " does not exist.");
        }
    }
}
