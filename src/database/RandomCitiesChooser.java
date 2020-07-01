package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.util.Pair;

public class RandomCitiesChooser {
    private static int amountRandomButtonPressed = 0;
    private static String randomlyChosenCity01 = "";
    private static String randomlyChosenCity02 = "";
    
    public static void InitializeCities() {
        try {
            FileWriter myWriter = new FileWriter("RandomCities.txt");
            myWriter.write("Athens,GR\n");
            myWriter.write("Tokyo,JP\n");
            myWriter.write("Patra,GR\n");
            myWriter.write("Moscow,RU\n");
            myWriter.write("Shuzenji,JP\n");
            myWriter.write("Paris,FR\n");
            myWriter.write("Prague,CZ\n");
            myWriter.write("Berlin,DE\n");
            myWriter.write("Cairo,EG\n");
            myWriter.write("Johannesburg,ZA\n");
            myWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static void UserPressedRandomButton(){
        amountRandomButtonPressed++;
        System.out.println("Now, the random button has been pressed " + amountRandomButtonPressed);
    }
    
    public static Pair<String, String> selectTwoRandomCities(){
        String token1 = "";
        Scanner inFile1 = null;
        
        try {
            inFile1 = new Scanner(new File("RandomCities.txt")).useDelimiter("\\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        List<String> listOfAvailableCities = new ArrayList<>();

        // while loop
        while (inFile1.hasNext()) {
            // find next line
            token1 = inFile1.next();
            listOfAvailableCities.add(token1);
        }
        inFile1.close();

        String[] tempsArray = listOfAvailableCities.toArray(new String[0]);

        for (String s : tempsArray) {
            System.out.println(s);
        }
        
        int low = 0; int high = 10;

        Random r = new Random();
        int result = r.nextInt(high-low) + low;
        System.out.println("RANDOM NUMBER: " + result);

        int result2 = result;
        while(result2 == result) {
            Random r2 = new Random();
            result2 = r2.nextInt(high-low) + low;
            System.out.println("RANDOM NUMBER: " + result2);
        }

        randomlyChosenCity01 = tempsArray[result]; 
        randomlyChosenCity02 = tempsArray[result2]; 
        
//        return new Pair<>("Athens,GR", "Patra,GR");
        return new Pair<>(randomlyChosenCity01, randomlyChosenCity02);
    }
    
    public static void produceFinalResults(String _travellerName){
        try {
            FileWriter myWriter = new FileWriter("RandomCitiesResults.txt");
            myWriter.write("NAME: " + _travellerName + "\n");
            myWriter.write("======================\n");
            myWriter.write("Amount random pressed: " + amountRandomButtonPressed + " times\n");
            myWriter.write("First randomly chosen city: " + randomlyChosenCity01 + "\n");
            myWriter.write("Second randomly chosen city: " + randomlyChosenCity02 + "\n");
            myWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
