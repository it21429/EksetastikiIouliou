package basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import exception.WikipediaNoArcticleException;

public class Handler {

	private boolean found = false;
	private boolean isNumber;
	private boolean isBoolean;
	private boolean isDouble;
	private boolean isPattern;

	// initializing the user input scanners
	Scanner type = new Scanner(System.in);
	Scanner name = new Scanner(System.in);
	Scanner age = new Scanner(System.in);
	Scanner cities = new Scanner(System.in);
	Scanner criteria = new Scanner(System.in);
	Scanner wcondition = new Scanner(System.in);

	// Initializing the arraylists for data transfer through classes
	ArrayList<City> cityList = new ArrayList<City>();
	public static ArrayList<City> cityList2 = new ArrayList<City>();
	City wantedCity = new City();

	// this method is used for handling the response to a bussines traveller
	public Bussines BussinesHandler(ArrayList<City> storedCity)
			throws JsonParseException, JsonMappingException, IOException, WikipediaNoArcticleException {

		// proceed to make a Bussines object to work with
		Bussines currBussines = new Bussines();

		// making contact with the bussines traveller
		System.out.println("Please type your name:\n");
		currBussines.setName(name.nextLine());
		// ----------------------------------------------------------------------------------------------
		System.out.println("Please type your Age:\n");
		do {

			if (age.hasNextInt()) {
				currBussines.setAge(age.nextInt());
				isNumber = true;
			} else {
				System.out.println("please use numbers only");
				isNumber = false;
				age.next();
			}
		} while (isNumber == false);
		// ----------------------------------------------------------------------------------------------
		System.out.println(
				"Do you want us to check if it's raining on your chosen cities and exclude them from the list?");
		System.out.println("type 'true' for yes or 'false' for no:");
		do {
			if (wcondition.hasNextBoolean()) {
				currBussines.setCweather(wcondition.nextBoolean());
				isBoolean = true;
			} else {
				System.out.println("please type true, or false.");
				isBoolean = false;
				wcondition.next();
			}
		} while (isBoolean == false);
		// -----------------------------------------------------------------------------------------------
		System.out.println(
				"please type the city you would like to check followed by a comma and the abbreviation of the country they're in");
		System.out.println("(example: Athens,GR)\nWhen you finish type 'done'.");
		Pattern pattern = Pattern.compile("[A-Za-z]+,[A-Za-z]+");
		while (!cities.hasNext("done")) {
			do {
				if (cities.hasNext(pattern)) {
					wantedCity = new City();
					wantedCity.setName(cities.next(pattern));
					cityList.add(wantedCity);
					isPattern = true;
				} else {
					System.out.println("please use the above format.");
					isPattern = false;
					cities.next();
				} 
			} while (isPattern==false);
			System.out.println(wantedCity.getName());
		}
		//----------------------------------------------------------------------------------------------
		System.out.println("What is your current latitude?");
		do {
			if (criteria.hasNextDouble()) {
				currBussines.setClat(criteria.nextDouble());
				isDouble = true;
			} else {
				System.out.println("please insert a double type of variable.");
				isDouble = false;
				criteria.next();
			} 
		} while (isDouble==false);
		//----------------------------------------------------------------------------------------------
		System.out.println("What is your current longitude?");
		do {
			if (criteria.hasNextDouble()) {
				currBussines.setClon(criteria.nextDouble());
				isDouble = true;
			} else {
				System.out.println("please insert a double type of variable.");
				isDouble = false;
				criteria.next();
			} 
		} while (isDouble==false);
		//----------------------------------------------------------------------------------------------
		System.out.println("success, calculating results...");

		// we iterate every given city from the user and check if they are stored
		// if they are stored we add it to the comparison list
		// if they are not, we fill them from OpenWeatherMap and Wiki Api and we add it
		// to the list
		for (City iteratedCity : cityList) {

			for (City iteratedCity2 : storedCity)
				if (iteratedCity.equals(iteratedCity2)) {
					cityList2.add(iteratedCity2);
					found = true;
				}

			if (!found) {
				cityList2.add(iteratedCity.fillCity(iteratedCity));
			}
		}

		// here we compare the cities to the bussines traveller criteria(distance)
		City bestCity = new City();
		bestCity = currBussines.CompareCities(cityList2, currBussines, currBussines.isCweather());
		System.out.println("\n**************** The City with the most distance is: " + bestCity.getName() + " ******************");
		currBussines.setmatchedCity(bestCity.getName());
		return currBussines;
	}

	public Traveller TravellerHandler(ArrayList<City> storedCity)
			throws JsonParseException, JsonMappingException, IOException, WikipediaNoArcticleException {

		// proceed to make a simple Traveller object to work with
		Traveller currTraveller = new Traveller();

		// making contact with the simple traveller
		System.out.println("Please type your name:\n");
		currTraveller.setName(name.nextLine());
		// ----------------------------------------------------------------------------------------------
		System.out.println("Please type your Age:\n");
		do {

			if (age.hasNextInt()) {
				currTraveller.setAge(age.nextInt());
				isNumber = true;
			} else {
				System.out.println("please use numbers only");
				isNumber = false;
				age.next();
			}
		} while (isNumber == false);
		// ----------------------------------------------------------------------------------------------
		System.out.println(
				"Do you want us to check if it's raining on your chosen cities and exclude them from the list?");
		System.out.println("type 'true' for yes or 'false' for no:");
		do {
			if (wcondition.hasNextBoolean()) {
				currTraveller.setCweather(wcondition.nextBoolean());
				isBoolean = true;
			} else {
				System.out.println("please type true, or false.");
				isBoolean = false;
				wcondition.next();
			}
		} while (isBoolean == false);
		// ----------------------------------------------------------------------------------------------
		System.out.println(
				"please type the city you would like to check followed by a comma and the abbreviation of the country they're in");
		System.out.println("(example: Athens,GR)\nWhen you finish type 'done'.");
		Pattern pattern = Pattern.compile("[A-Za-z]+,[A-Za-z]+");
		while (!cities.hasNext("done")) {
			do {
				if (cities.hasNext(pattern)) {
					wantedCity = new City();
					wantedCity.setName(cities.next(pattern));
					cityList.add(wantedCity);
					isPattern = true;
				} else {
					System.out.println("please use the above format.");
					isPattern = false;
					cities.next();
				} 
			} while (isPattern==false);
			System.out.println(wantedCity.getName());
		}
		// ----------------------------------------------------------------------------------------------
		// we ask traveller for the wanted criteria in their chosen cities
		System.out.println("in how many museums are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTraveller.setCmuseums(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		System.out.println("in how many cafeterias are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTraveller.setCcafes(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		System.out.println("in how many bars are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTraveller.setCbars(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		System.out.println("in how many beaches are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTraveller.setCbeaches(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		System.out.println("finally, in how many restaurants are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTraveller.setCrestaurants(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		// ----------------------------------------------------------------------------------------------v
		System.out.println("success, calculating results...");

		// we iterate every given city from the user and check if they are stored
		// if they are stored we add it to the comparison list
		// if they are not, we fill them from OpenWeatherMap and Wiki Api and we add it
		// to the list
		for (City iteratedCity : cityList) {

			for (City iteratedCity2 : storedCity)
				if (iteratedCity.equals(iteratedCity2)) {
					cityList2.add(iteratedCity2);
					found = true;
				}

			if (!found) {
				cityList2.add(iteratedCity.fillCity(iteratedCity));
			}
		}

		// here we compare the cities to the simple traveller criteria(binary appearance
		// of criteria)
		City bestCity = new City();
		bestCity = currTraveller.CompareCities(cityList2, currTraveller, currTraveller.isCweather());
		System.out.println(
				"\n*********The City that matches your criteria the most is: " + bestCity.getName() + "**********");

		currTraveller.setmatchedCity(bestCity.getName());
		return currTraveller;
	}

	public Tourist TouristHandler(ArrayList<City> storedCity)
			throws JsonParseException, JsonMappingException, IOException, WikipediaNoArcticleException {

		// proceed to make a Tourist object to work with
		Tourist currTourist = new Tourist();

		// making contact with the Tourist traveller
		System.out.println("Please type your name:\n");
		currTourist.setName(name.nextLine());
		// ----------------------------------------------------------------------------------------------
		System.out.println("Please type your Age:\n");
		do {

			if (age.hasNextInt()) {
				currTourist.setAge(age.nextInt());
				isNumber = true;
			} else {
				System.out.println("please use numbers only");
				isNumber = false;
				age.next();
			}
		} while (isNumber == false);
		// ----------------------------------------------------------------------------------------------
		System.out.println(
				"Do you want us to check if it's raining on your chosen cities and exclude them from the list?");
		System.out.println("type 'true' for yes or 'false' for no:");
		do {
			if (wcondition.hasNextBoolean()) {
				currTourist.setCweather(wcondition.nextBoolean());
				isBoolean = true;
			} else {
				System.out.println("please type true, or false.");
				isBoolean = false;
				wcondition.next();
			}
		} while (isBoolean == false);
		// ----------------------------------------------------------------------------------------------
		System.out.println(
				"please type the city you would like to check followed by a comma and the abbreviation of the country they're in");
		System.out.println("(example: Athens,GR)\nWhen you finish type 'done'.");
		Pattern pattern = Pattern.compile("[A-Za-z]+,[A-Za-z]+");
		while (!cities.hasNext("done")) {
			do {
				if (cities.hasNext(pattern)) {
					wantedCity = new City();
					wantedCity.setName(cities.next(pattern));
					cityList.add(wantedCity);
					isPattern = true;
				} else {
					System.out.println("please use the above format.");
					isPattern = false;
					cities.next();
				} 
			} while (isPattern==false);
			System.out.println(wantedCity.getName());
		}
		// ----------------------------------------------------------------------------------------------
		System.out.println("in how many museums are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTourist.setCmuseums(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		System.out.println("in how many cafeterias are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTourist.setCcafes(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		System.out.println("in how many bars are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTourist.setCbars(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		System.out.println("in how many beaches are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTourist.setCbeaches(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		System.out.println("finally, in how many restaurants are you interested in?");
		do {

			if (criteria.hasNextInt()) {
				currTourist.setCrestaurants(criteria.nextInt());
				isNumber = true;
			} else {
				System.out.println("please type a number.");
				isNumber = false;
				criteria.next();
			}
		} while (isNumber == false);
		//----------------------------------------------------------------------------------------
		System.out.println("success, calculating results...");

		// we iterate every given city from the user and check if they are stored
		// if they are stored we add it to the comparison list
		// if they are not, we fill them from OpenWeatherMap and Wiki Api and we add it
		// to the list
		for (City iteratedCity : cityList) {

			for (City iteratedCity2 : storedCity)
				if (iteratedCity.equals(iteratedCity2)) {
					cityList2.add(iteratedCity2);
					found = true;
				}

			if (!found) {
				cityList2.add(iteratedCity.fillCity(iteratedCity));
			}
		}

		// here we compare the cities to the Tourist traveller criteria(multiple
		// appearance of criteria)
		City bestCity = new City();
		bestCity = currTourist.CompareCities(cityList2, currTourist, currTourist.isCweather());
		System.out.println(
				"\n*******The City that matches your criteria the most is: " + bestCity.getName() + "**********");

		currTourist.setmatchedCity(bestCity.getName());
		return currTourist;
	}

	// method for returning the List to store it
	public ArrayList<City> getCityList3(ArrayList<City> storedCity) {
		boolean flag = false;
		ArrayList<City> cityList3 = new ArrayList<City>();
		if (storedCity != null || cityList2 != null) {
			for (City progCity : cityList2) {
				for (City dbCity : storedCity) {
					if (progCity.getName().equalsIgnoreCase(dbCity.getName())) {
						flag = true;
					}
				}
				if (flag == false) {
					cityList3.add(progCity);
				}
				flag = false;
			}
		}
		return cityList3;
	}
}
