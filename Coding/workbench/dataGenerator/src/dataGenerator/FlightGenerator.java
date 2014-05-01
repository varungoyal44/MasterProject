package dataGenerator;

import java.util.Random;


//import java.util.Random;

public class FlightGenerator {
	public static void main (String args[])
	{
		String airlines[] = {"American Airlines", "Delta", "JetBlue Airways", "United", "US Airways"};
		int capacityArray[] = {500, 200, 350, 800, 700, 900, 300, 600, 750, 650, 500, 200, 350, 800, 700, 900, 300, 600, 750, 650};
		String airlineAbr[] = {"AA", "DEL", "JBA", "UNI", "USA"};
		String querry = "INSERT INTO flight (flight_number, number_of_seats, airlines) VALUES('";

		Random randomGenerator = new Random(); // Iterations need to be less than max int.
		int capacityArrayIterator = 0;

		int airlineIterator = 0;
		for(int j=150; j<=2650; j++)
		{
			airlineIterator = randomGenerator.nextInt(airlineAbr.length);
			if(capacityArrayIterator == 20)
				capacityArrayIterator = 0;

			String flightNumber = airlineAbr[airlineIterator] + j;
			String thisQuerry = querry +  flightNumber + "', '" + capacityArray[capacityArrayIterator] + "', '" + airlines[airlineIterator] + "');"; 
			System.out.println(thisQuerry);
			capacityArrayIterator++;
			
		}

		System.out.println("SELECT * FROM flight;");
		
	}

}
