package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import basic.City;

public class DatabaseDriver {

	// method connecting to the database (SQLite)
	public Connection dbConnect() {

		Connection conn = null;
		try {
			String url = "jdbc:sqlite:C:\\Users\\abzorbaDev\\Desktop\\OOPII_21487_21429-master_JUNE\\OOPII_21487_21429-master\\Cities.db";
			conn = DriverManager.getConnection(url);
			
			// SQL statement for creating a new table if it doesnt exist
			String tablename = "CREATE TABLE IF NOT EXISTS Cities (\r\n" + "  name text,\r\n" + "  museums int,\r\n"
					+ "  cafeterias int,\r\n" + "  bars int,\r\n" + "  restaurants int,\r\n" + "  beaches int,\r\n"
					+ "  weather boolean,\r\n" + "  latitude double,\r\n" + "  longitude double\r\n" + ");\r\n";
			
			Statement stmt = conn.createStatement();
			stmt.execute(tablename);
			return conn;

		} catch (SQLException e) {
			throw new Error("Problem", e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	// method to insert a city into the database
	public void insertCities(City city) {
		
		String sql = "INSERT INTO Cities(name,museums,cafeterias,bars,restaurants,beaches,weather,latitude,longitude)"
				+ " VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			String url = "jdbc:sqlite:C:\\Users\\abzorbaDev\\Desktop\\OOPII_21487_21429-master_JUNE\\OOPII_21487_21429-master\\Cities.db";
			Connection conn = DriverManager.getConnection(url); 
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, city.getName());
			pstmt.setInt(2, city.getMuseums());
			pstmt.setInt(3, city.getCafes());
			pstmt.setInt(4, city.getBars());
			pstmt.setInt(5, city.getRestaurants());
			pstmt.setInt(6, city.getBeaches());
			pstmt.setBoolean(7, city.isWeather());
			pstmt.setDouble(8, city.getLat());
			pstmt.setDouble(9, city.getLon());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	// method to retrieve and load the database in the program as an ArrayList
	public ArrayList<City> retrieveCities() {

		ArrayList<City> citiesfromDB = new ArrayList<City>();
		City cityDB = new City();

		String sql = "SELECT name,museums,cafeterias,bars,restaurants,beaches,weather,latitude,longitude"
				+ " FROM Cities";

		try {
			String url = "jdbc:sqlite:C:\\Users\\abzorbaDev\\Desktop\\OOPII_21487_21429-master_JUNE\\OOPII_21487_21429-master\\Cities.db";
			Connection conn = DriverManager.getConnection(url);
			PreparedStatement pstmt = conn.prepareStatement(sql);
		
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				cityDB = new City();
				cityDB.setName(rs.getString("name"));
				cityDB.setMuseums(rs.getInt("museums"));
				cityDB.setCafes(rs.getInt("cafeterias"));
				cityDB.setBars(rs.getInt("bars"));
				cityDB.setRestaurants(rs.getInt("restaurants"));
				cityDB.setBeaches(rs.getInt("beaches"));
				cityDB.setWeather(rs.getBoolean("weather"));
				cityDB.setLat(rs.getDouble("latitude"));
				cityDB.setLon(rs.getDouble("longitude"));

				citiesfromDB.add(cityDB);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return citiesfromDB;
	}
	
	//method to print all the cities stored in the database
	public void showDB() {

		String sql = "SELECT name,museums,cafeterias,bars,restaurants,beaches,weather,latitude,longitude"
				+ " FROM Cities";

		try {
			
			String url = "jdbc:sqlite:C:\\Users\\abzorbaDev\\Desktop\\OOPII_21487_21429-master_JUNE\\OOPII_21487_21429-master\\Cities.db";
			Connection conn = DriverManager.getConnection(url);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			// loop through the result set and print
			while (rs.next()) {

				System.out.println("----------"+rs.getString("name")+"------------");
				System.out.println("museums:" + rs.getInt("museums") + " cafes:" + rs.getInt("cafeterias") + " bars:"
						+ rs.getInt("bars") + " restaurants" + rs.getInt("restaurants") + " beaches:"
						+ rs.getInt("beaches") + " rain:" + rs.getBoolean("weather") + " latitude:"
						+ rs.getDouble("latitude") + " longitude:" + rs.getDouble("longitude"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
