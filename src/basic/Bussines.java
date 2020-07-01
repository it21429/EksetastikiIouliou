package basic;

public class Bussines extends Traveller {
	
	//Serializable UID
	//private static final long serialVersionUID = 1L;
	
	private double distance;

	//constructors and super
	public Bussines() {
	}

	public Bussines(String matchedCity, String name, int age, double clat, double clon, int cmuseums, int ccafes, int cbars, int crestaurants,
			int cbeaches, boolean cweather) {
		super(matchedCity, name, age, clat, clon, cmuseums, ccafes, cbars, crestaurants, cbeaches, cweather);
	}
	
	//overriden traveller method to match the bussines score accumulation (distance)
	@Override
	public double Similarity(City city, Traveller traveller){
	
		distance = DistanceCalculator.distance(city.getLat(),city.getLon(),traveller.getClat(),traveller.getClon(),"K");
		return distance;
	}
}
