package basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import database.DatabaseDriver;
import exception.WikipediaNoArcticleException;

public class Executable {

	private static int typeoftraveller;

	public static void main(String[] args)
			throws JsonParseException, JsonMappingException, IOException, WikipediaNoArcticleException {

		// connection to the city DataBase and check for cities table
		DatabaseDriver databaseDriver = new DatabaseDriver();
		databaseDriver.dbConnect();
				
		// Arraylist for loading Travellers from file
		ArrayList<Traveller> storedTravellers = new ArrayList<Traveller>();
		
		// Arraylist for loading cities from Database
		ArrayList<City> storedCities = new ArrayList<City>();
		// ------Loading Cities from database-------
		storedCities = databaseDriver.retrieveCities();
				
		//Container for cities that are not in the Database
		ArrayList<City> tempCities = new ArrayList<City>();
		// Arraylist for retrieving and showing Travellers from file
		ArrayList<Traveller> travellersFromFile = new ArrayList<Traveller>();

		// --------extracting traveller count from file--------
		Traveller.setTravCount(FileWriterReader.retrieveCount());
		// --------loading the traveller List from File--------
		storedTravellers = FileWriterReader.RetrieveTravellers(Traveller.getTravCount());

		Scanner type = new Scanner(System.in);
		Handler handler = new Handler();
		
		//Handler Class does all the work according to what type of traveller the user chooses
		System.out.println("\nHello user,are you:\n(1) a plain traveller \n(2) a tourist \n(3) a business traveller\n\n please type 1, 2,or 3:");

		do {
			typeoftraveller = type.nextInt();

			// simple traveller
			if (typeoftraveller == 1) {
				// We store and fill the objects in an arraylist Collection using Handler
				storedTravellers.add(handler.TravellerHandler(storedCities));
				// we store the cities that we dont have in the database
				tempCities = handler.getCityList3(storedCities);
				if (tempCities != null) {
					for (City c : tempCities) {
						databaseDriver.insertCities(c);
					}
				}
				// incrementing the number of travellers by 1
				Traveller.travInc();
				// saving the new count on the file (overwrite)
				FileWriterReader.saveCount(Traveller.getTravCount());
				// saving the new traveller list on a file
				FileWriterReader.saveTravellers(storedTravellers);

				//Same for Tourist
			} else if (typeoftraveller == 2) {
				storedTravellers.add(handler.TouristHandler(storedCities));
				tempCities = handler.getCityList3(storedCities);
				if (tempCities != null) {
					for (City c : tempCities) {
						databaseDriver.insertCities(c);
					}
				}
				Traveller.travInc();
				FileWriterReader.saveCount(Traveller.getTravCount());
				FileWriterReader.saveTravellers(storedTravellers);

				//Same for Bussines
			} else if (typeoftraveller == 3) {
				storedTravellers.add(handler.BussinesHandler(storedCities));
				tempCities = handler.getCityList3(storedCities);
				if (tempCities != null) {
					for (City c : tempCities) {
						databaseDriver.insertCities(c);
					}
				}
				Traveller.travInc();
				FileWriterReader.saveCount(Traveller.getTravCount());
				FileWriterReader.saveTravellers(storedTravellers);
			} else {
				System.out.println("please use one of the numbers above.");
			}

		} while (typeoftraveller != 1 && typeoftraveller != 2 && typeoftraveller != 3);
		type.close();

		
		// we print the Count of Travellers used the program
		System.out.println("\nTraveller Number: " + Traveller.getTravCount());

		// we print the list of travellers sorted by age
		System.out.println("-----------------List Sorted By Age-------------------");
		travellersFromFile = FileWriterReader.RetrieveTravellers(Traveller.getTravCount());
		Collections.sort(travellersFromFile);
		String previousName = "";
		for (Traveller printTraveller : travellersFromFile) {
			if (!previousName.equals(printTraveller.getName())) {
				System.out.println(printTraveller.getName() + " Age: " + printTraveller.getAge());
			}
			previousName = printTraveller.getName();
		}
		
		//we print the cities that are stored at our Database
		System.out.println("\n*********Cities stored in Database**********\n");
		databaseDriver.showDB();
	}
}
