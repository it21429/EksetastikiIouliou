package basic;

public class Tourist extends Traveller {

	private int score;
	
	//Serializable UID
	//private static final long serialVersionUID = 1L;
	
	//constructors and super
	public Tourist(String matchedCity, String name, int age, double clat, double clon, int cmuseums, int ccafes, int cbars,
			int crestaurants, int cbeaches, boolean cweather) {
		super(matchedCity, name, age, clat, clon, cmuseums, ccafes, cbars, crestaurants, cbeaches, cweather);
	}

	public Tourist() {
	}

	//Overriden method Similarity to match the need of Tourist score accumulation
	@Override
	public double Similarity(City city, Traveller traveller) {

		score = 0;
		if (traveller.getCmuseums() > 0) {
			score = score + city.getMuseums();
		}
		if (traveller.getCbeaches() > 0) {
			score = score + city.getBeaches();
		}
		if (traveller.getCrestaurants() > 0) {
			score = score + city.getRestaurants();
		}
		if (traveller.getCcafes() > 0) {
			score = score + city.getCafes();
		}
		if (traveller.getCbars() > 0) {
			score = score + city.getBars();
		}
		return score;
	}
}