//code taken from johnviolos github OpenDataRest
package exception;

public class WikipediaNoCityException extends Exception {
	//exception handler for the method not finding a city
	private static final long serialVersionUID = 1L;
	static int numExcepetions=0;
	private String cityName;
	
	public WikipediaNoCityException(String in_cityName) {
		numExcepetions++;
		this.cityName=in_cityName;
	}
	
	public String getMessage() {
		
		return "There is not any city in wikipedia with the name "+cityName+".";
	}
}
