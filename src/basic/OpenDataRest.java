//code taken from johnviolos github OpenDataRest and modified accordingly
package basic;

import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import exception.WikipediaNoArcticleException;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

public class OpenDataRest {

	
	//we use this method to retrieve the Latitude from a city using openweathermap
	public static double RetrieveLatitude(String city, String country, String appid)
			throws JsonParseException, JsonMappingException, IOException {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appid + "").build());
		ObjectMapper mapper = new ObjectMapper();
		String json = service.accept(MediaType.APPLICATION_JSON).get(String.class);
		OpenWeatherMap weather_obj = mapper.readValue(json, OpenWeatherMap.class);
		double latitude = weather_obj.getCoord().getLat();
		return latitude;
	}
	
	
	//we use this method to retrieve the longitude of a city using openweathermap
	public static double RetrieveLongitude(String city, String country, String appid)
			throws JsonParseException, JsonMappingException, IOException {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri(
				"http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appid + "").build());
		ObjectMapper mapper = new ObjectMapper();
		String json = service.accept(MediaType.APPLICATION_JSON).get(String.class);
		OpenWeatherMap weather_obj = mapper.readValue(json, OpenWeatherMap.class);
		double longitude = weather_obj.getCoord().getLon();
		return longitude;
	}
	
	
	//we use this method to retrieve humidity from a city to check for rain
	public static int RetrieveHumidity(String city, String country, String appid)
			throws JsonParseException, JsonMappingException, IOException {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appid + "").build());
		ObjectMapper mapper = new ObjectMapper();
		String json = service.accept(MediaType.APPLICATION_JSON).get(String.class);
		OpenWeatherMap weather_obj = mapper.readValue(json, OpenWeatherMap.class);
		int humidity = weather_obj.getMain().getHumidity();
		return humidity;
	}

	//we use this method to get the article and data for the given city from Wiki Api
	public static String RetrieveWikipedia(String city) throws IOException, WikipediaNoArcticleException {
		String article = "";
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client
				.resource(UriBuilder.fromUri("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="
						+ city + "&format=json&formatversion=2").build());
		ObjectMapper mapper = new ObjectMapper();
		String json = service.accept(MediaType.APPLICATION_JSON).get(String.class);
		if (json.contains("pageid")) {
			MediaWiki mediaWiki_obj = mapper.readValue(json, MediaWiki.class);
			article = mediaWiki_obj.getQuery().getPages().get(0).getExtract();
		} else
			throw new WikipediaNoArcticleException(city);
		return article;
	}
	
	//method for counting the criteria found in the wiki page city
	public static int countCriterionfCity(String cityArticle, String criterion) {
		cityArticle=cityArticle.toLowerCase();
		int index = cityArticle.indexOf(criterion);
		int count = 0;
		while (index != -1) {
		    count++;
		    cityArticle = cityArticle.substring(index + 1);
		    index = cityArticle.indexOf(criterion);
		}
		return count;
	}
}
