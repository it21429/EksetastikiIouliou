package basic;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import exception.WikipediaNoArcticleException;

public class City {


	private String name;
	private int museums;
	private int cafes;
	private int bars;
	private int restaurants;
	private int beaches;
	private boolean weather;
	private double lat;
	private double lon;
	private static String Article;
	
	//API Key for OpenWeatherMap
	private static final String Weatherkey = "eddc1ed6bab53d705ddd4d680d211041";

	
	//two constructor which one of them is empty
	public City(String name, int museums, int cafes, int bars, int restaurants, int beaches, boolean weather, double lat, double lon) {
		
		this.name = name;
		this.museums = museums;
		this.cafes = cafes;
		this.bars = bars;
		this.restaurants = restaurants;
		this.beaches = beaches;
		this.weather = weather;
		this.lat = lat;
		this.lon = lon;
	}
	
	public City(){
	}
	
	
	//getset
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMuseums() {
		return museums;
	}


	public void setMuseums(int museums) {
		this.museums = museums;
	}


	public int getCafes() {
		return cafes;
	}


	public void setCafes(int cafes) {
		this.cafes = cafes;
	}


	public int getBars() {
		return bars;
	}


	public void setBars(int bars) {
		this.bars = bars;
	}


	public int getRestaurants() {
		return restaurants;
	}


	public void setRestaurants(int restaurants) {
		this.restaurants = restaurants;
	}


	public int getBeaches() {
		return beaches;
	}


	public void setBeaches(int beaches) {
		this.beaches = beaches;
	}


	public boolean isWeather() {
		return weather;
	}


	public void setWeather(boolean weather) {
		this.weather = weather;
	}


	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public double getLon() {
		return lon;
	}


	public void setLon(double lon) {
		this.lon = lon;
	}
	
	
	
	//this method fills the object cities
	public City fillCity (City iteratedCity) throws JsonParseException, JsonMappingException, IOException, WikipediaNoArcticleException {
		//splitting the city name from the country code
		String[] nameString = iteratedCity.getName().split(",");
		String city = nameString[0];
		String country = nameString[1];
		//setting the objects name to the city without the country code
		iteratedCity.setName(city);		
		
		//retrieving latitude
		try {
			double templat = OpenDataRest.RetrieveLatitude(city, country, Weatherkey);
			iteratedCity.setLat(templat);
		} catch (com.sun.jersey.api.client.UniformInterfaceException e) {
			System.out.println("The city "+city+"you inserted was not found, please try again.");
		}
		
		//retrieving longitude
		double templon = OpenDataRest.RetrieveLongitude(city, country, Weatherkey);
		iteratedCity.setLon(templon);
		
		//retrieving rain status (it rains when humidity is 100%)
		int temphumidity = OpenDataRest.RetrieveHumidity(city, country, Weatherkey);
		if (temphumidity == 100) {
			iteratedCity.setWeather(true);
		}else {iteratedCity.setWeather(false);
		}

		//filling criteria based on wiki article of the city
		Article = OpenDataRest.RetrieveWikipedia(iteratedCity.getName());
		iteratedCity.setMuseums(OpenDataRest.countCriterionfCity(Article, "museum"));
		iteratedCity.setCafes(OpenDataRest.countCriterionfCity(Article, "cafe"));
		iteratedCity.setBars(OpenDataRest.countCriterionfCity(Article, "bar"));
		iteratedCity.setRestaurants(OpenDataRest.countCriterionfCity(Article, "restaurant"));
		iteratedCity.setBeaches(OpenDataRest.countCriterionfCity(Article, "beach"));
		
		return iteratedCity;
	}
	
	// Overriding equals() to compare Object names 
    @Override
    public boolean equals(Object o) { 
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
        /* Check if o is an instance of City or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof City)) { 
            return false; 
        }  
        // typecast o to City so that we can compare data members  
        City c = (City) o;    
        // Compare the data members and return accordingly  
        return name == c.getName();
    } 
}
