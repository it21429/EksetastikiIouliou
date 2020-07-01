package basic;

import java.io.Serializable;
import java.util.ArrayList;

public class Traveller implements Serializable,Comparable<Traveller> {

	
	//Serializable UID
	private static final long serialVersionUID = 1L;

	private String Name;
	private int Age;
	private double clat;
	private double clon;
	private int cmuseums;
	private int ccafes;
	private int cbars;
	private int crestaurants;
	private int cbeaches;
	private boolean cweather;
	private int score;
	
	//matchedCity is the visit variable asked in excersice 1 2nd deliverable
	private String matchedCity;
	
	//this static variable is used to count how many travellers used the app
	private static int TravCount = 0;
	
	//needed in comparisons
	private double bestscore;
	private double tempscore;
	
	
	//this method is used by Comparable to Sort the list of Travellers by age
	public int compareTo(Traveller t){ 
        return this.Age - t.Age; 
    } 
	

	//constructor and empty constructor
	public Traveller(String matchedCity, String name, int age, double clat, double clon, int cmuseums, int ccafes, int cbars,
			int crestaurants, int cbeaches, boolean cweather) {
		
		this.matchedCity = matchedCity;
		Name = name;
		Age = age;
		this.clat = clat;
		this.clon = clon;
		this.cmuseums = cmuseums;
		this.ccafes = ccafes;
		this.cbars = cbars;
		this.crestaurants = crestaurants;
		this.cbeaches = cbeaches;
		this.cweather = cweather;
	}

	public Traveller(){
	}
	
	
	//getset
	public String getmatchedCity() {
		return matchedCity;
	}
	
	public void setmatchedCity(String matchedcity) {
		matchedCity = matchedcity;
	}
	
	public static int getTravCount() {
		return TravCount;
	}

	public static void setTravCount(int travcount) {
		TravCount = travcount;
	}
	
	public static void travInc() {
		TravCount = TravCount + 1;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public double getClat() {
		return clat;
	}

	public void setClat(double clat) {
		this.clat = clat;
	}

	public double getClon() {
		return clon;
	}

	public void setClon(double clon) {
		this.clon = clon;
	}

	public int getCmuseums() {
		return cmuseums;
	}

	public void setCmuseums(int cmuseums) {
		this.cmuseums = cmuseums;
	}

	public int getCcafes() {
		return ccafes;
	}

	public void setCcafes(int ccafes) {
		this.ccafes = ccafes;
	}

	public int getCbars() {
		return cbars;
	}

	public void setCbars(int cbars) {
		this.cbars = cbars;
	}

	public int getCrestaurants() {
		return crestaurants;
	}

	public void setCrestaurants(int crestaurants) {
		this.crestaurants = crestaurants;
	}

	public int getCbeaches() {
		return cbeaches;
	}

	public void setCbeaches(int cbeaches) {
		this.cbeaches = cbeaches;
	}

	public boolean isCweather() {
		return cweather;
	}

	public void setCweather(boolean cweather) {
		this.cweather = cweather;
	}

	
	//method for accumulating city score of simple Traveller
	public double Similarity(City city, Traveller traveller) {

		score = 0;

		if (traveller.getCmuseums() > 0) {
			if (city.getMuseums() > 0) {
				score = score + 1;
			}
		}

		if (traveller.getCbars() > 0) {
			if (city.getBars() > 0) {
				score = score + 1;
			}
		}

		if (traveller.getCrestaurants() > 0) {
			if (city.getRestaurants() > 0) {
				score = score + 1;
			}
		}

		if (traveller.getCbeaches() > 0) {
			if (city.getBeaches() > 0) {
				score = score + 1;
			}
		}

		if (traveller.getCcafes() > 0) {
			if (city.getCafes() > 0) {
				score = score + 1;
			}
		}
		return score;
	}

	
	//method that compares cities without the weather parameter (not used, we check for rain in the other method)
	public City CompareCities(ArrayList<City> listofCities, Traveller CurrentTraveller) {
		bestscore = 0;
		City bestCity = new City();
		for (City chosenCity : listofCities) {
			tempscore = Similarity(chosenCity, CurrentTraveller);
			if (tempscore > bestscore) {
				bestscore = tempscore;
				bestCity.setName(chosenCity.getName());
			}
		}
		return bestCity; // only the name is returned
	}

	//method that compares cities taking rain as a parameter (rain = 100% humidity)
	public City CompareCities(ArrayList<City> listofCities, Traveller CurrentTraveller, boolean badWeather) {
		bestscore = 0;
		City bestCity = new City();
		for (City chosenCity : listofCities) {
			if (badWeather == false) {
				tempscore = Similarity(chosenCity, CurrentTraveller);
				if (tempscore > bestscore) {
					bestscore = tempscore;
					bestCity = chosenCity;
				}
			}
		}
		return bestCity;
	}
}
