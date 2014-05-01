package dataGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DurationGenerator 
{
	public static void main (String args[]) throws Exception
	{
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String airportID [];

		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://127.0.0.1:3306/?"
							+ "user=root");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();


			// to get airport ids...
			resultSet = statement
					.executeQuery("select id from test.airport");
			int rowCount = resultSet.last() ? resultSet.getRow() : 0;
			resultSet.first();
			airportID = new String[rowCount];
			int i = 0;

			do
			{
				airportID[i] = resultSet.getString("id");
				i++;
			}while (resultSet.next());

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}
		}

		// to create a random duration array...
		int durationArray[] = new int[101];
		int j = 60;
		for(int i=0; i<101; i++)
		{
			durationArray[i] = j;
			j = j + 15;
		}

		// random generator...
		Random randomGenerator = new Random();
		
		// initial part of the query...
		String querry = "INSERT INTO duration (from_airport_id, to_airport_id, duration_mins) VALUES(";

		for(int i=0; i<airportID.length; i++)
		{
			for(int k=i+1; k<(airportID.length-i-1); k++)
			{
				int departureRandomizer = 0;
				while(departureRandomizer == 0)
				{
					departureRandomizer = randomGenerator.nextInt(durationArray.length);
				}
				
				String newQuerry = querry + airportID[i] + ", " + airportID[k] + ", " + durationArray[departureRandomizer] + ");";
				System.out.println(newQuerry);
			}
		}




	}

}
