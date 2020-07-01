package basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class FileWriterReader implements Serializable {

	//serializable UID
	private static final long serialVersionUID = 5193163575368046387L;

	//method for saving a list of travellers to a Travellers.txt file
	public static void saveTravellers(ArrayList<Traveller> travellerstoSave) {

		try {
			FileOutputStream fileout = new FileOutputStream(new File("Travellers.txt"));
			ObjectOutputStream objout = new ObjectOutputStream(fileout);
			for (Traveller iteratedTraveller : travellerstoSave) {
				objout.writeObject(iteratedTraveller);
			}
			objout.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	//method for retrieving a list of Travellers from a Travellers.txt file
	public static ArrayList<Traveller> RetrieveTravellers(int numberofTravellers) {

		ArrayList<Traveller> fileList = new ArrayList<>();
		
		if(numberofTravellers !=0) {
		try { 
			FileInputStream fi = new FileInputStream(new File("Travellers.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			for(int i=1;i<=numberofTravellers;i++) {
				fileList.add((Traveller) oi.readObject());
			}
			oi.close();
			fi.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		}
		return fileList;
	}
	
	
	//method that saves the Traveller count to a file
	public static void saveCount(int counttoSave) {

		try {
			FileWriter fw = new FileWriter("Tcount.txt",false);
			fw.write(String.valueOf(counttoSave));
			fw.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}

	//method to retrieve the Traveller count from a file
	public static int retrieveCount() throws FileNotFoundException {
	
		String CounttoRetrieve;
		File file = new File("Tcount.txt");
		Scanner scanner = new Scanner(file);
		CounttoRetrieve = scanner.next();
		int retree = Integer.parseInt(CounttoRetrieve);
		scanner.close();

	return retree;
}
}