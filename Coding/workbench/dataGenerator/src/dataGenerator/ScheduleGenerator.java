package dataGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ScheduleGenerator 
{
	public static void main (String args[]) throws Exception
	{
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String airportID [], flightID[];
		int totalNumberOfSeats[];

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


			// to get flight number...
			resultSet = statement
					.executeQuery("select id, number_of_seats from test.flight");
			rowCount = resultSet.last() ? resultSet.getRow() : 0;
			resultSet.first();
			flightID = new String[rowCount];
			totalNumberOfSeats = new int[rowCount];
			i = 0;

			do
			{
				flightID[i] = resultSet.getString("id");
				totalNumberOfSeats[i] = Integer.parseInt(resultSet.getString("number_of_seats"));
				i++;
			}while (resultSet.next());



			// to create date variables...
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			Calendar cal = Calendar.getInstance();

			// to get flight duration...
			resultSet = statement
					.executeQuery("select from_airport_id, to_airport_id, duration_mins from test.duration");
			rowCount = resultSet.last() ? resultSet.getRow() : 0;
			resultSet.first();

			System.out.print("\nProcessing.");

			// To generate and execute the insert queries... 
			Statement insertStatement = connect.createStatement();

			// To create new row for Schedule table...
			ScheduleRow newRow = new ScheduleRow();
			ScheduleRow newReverseRow = new ScheduleRow();
			
			int flightIterator = -1;
			i = 0;
			do
			{
				System.out.print("\n" + i++);
				
				// 5 flights to-and-fro...
				for(int planeNumber = 0; planeNumber<6; planeNumber++)
				{
					// To increment the flight number...
					flightIterator ++;
					
					// To reset the departure date...
					Date departureDate = new Date();
					cal.setTime(departureDate);
					cal.add(Calendar.MINUTE, (5*planeNumber));
					departureDate = cal.getTime();
					
					
					// for 300 rounds for each flight...
					for(int round=0; round<200; round++)	
					{
						// To add landing + takeoff + refuel + ground time to the departure...
						cal.setTime(departureDate);
						cal.add(Calendar.MINUTE, 100);
						departureDate = cal.getTime();

						// To print from_airport, to_airport, departure date, departure time and duration...
						newRow.from_airport_id = resultSet.getString("from_airport_id");
						newRow.to_airport_id = resultSet.getString("to_airport_id");
						newRow.departure_date = dateFormat.format(departureDate);
						newRow.departure_time = timeFormat.format(departureDate);
						newRow.duration_mins = Integer.parseInt(resultSet.getString("duration_mins"));

						// get arrival date...
						cal.setTime(departureDate);
						cal.add(Calendar.MINUTE, newRow.duration_mins);
						Date arrivalDate = cal.getTime();
						newRow.arrival_date = dateFormat.format(arrivalDate);
						newRow.arrival_time = timeFormat.format(arrivalDate);

						// To print flight id...
						newRow.flight_id = flightID[flightIterator];

						// To print number of seats left and price...
						newRow.number_of_seats_left = (int) (totalNumberOfSeats[flightIterator] * 0.6);
						newRow.price = (float) (newRow.duration_mins * 0.8);

						// Inserting the data into the table...
						System.out.print(".");
						insertStatement.execute(newRow.getQuery());

						// To replace the departure date/time with arrival date/time...
						departureDate = arrivalDate;


						//------------------------------------------------------------------------------------------
						// FOR REVERSE JOURNEY....
						
						// To add landing + takeoff + refuel + ground time to the departure...
						cal.setTime(departureDate);
						cal.add(Calendar.HOUR, 2);
						departureDate = cal.getTime();

						// To print from_airport, to_airport, departure date, departure time and duration...
						newReverseRow.from_airport_id = resultSet.getString("to_airport_id");
						newReverseRow.to_airport_id = resultSet.getString("from_airport_id");
						newReverseRow.departure_date = dateFormat.format(departureDate);
						newReverseRow.departure_time = timeFormat.format(departureDate);
						newReverseRow.duration_mins = Integer.parseInt(resultSet.getString("duration_mins"));

						// get arrival date...
						cal.setTime(departureDate);
						cal.add(Calendar.MINUTE, newReverseRow.duration_mins);
						Date reverseArrivalDate = cal.getTime();
						newReverseRow.arrival_date = dateFormat.format(reverseArrivalDate);
						newReverseRow.arrival_time = timeFormat.format(reverseArrivalDate);

						// To print flight id...
						newReverseRow.flight_id = flightID[flightIterator];

						// To print number of seats left and price...
						newReverseRow.number_of_seats_left = (int) (totalNumberOfSeats[flightIterator] * 0.63);
						newReverseRow.price = (float) (newReverseRow.duration_mins * 0.8);
						
						// Inserting the data into the table...
						System.out.print(".");
						insertStatement.execute(newReverseRow.getQuery());
						
						// To replace the departure date/time with arrival date/time...
						departureDate = reverseArrivalDate;

					}
				}

			}while (resultSet.next());

			System.out.print("\nDone.");














			/*
			// To generate and execute the insert queries... 
			Statement insertStatement = connect.createStatement();
			for(int day=0; day<365; day++)
			{
				int flightIterator = 0;
				do
				{
					// to reset the query...
					restOfTheQuerry = "";

					// To print from_airport, to_airport, departure date, departure time and duration...
					int duration = Integer.parseInt(resultSet.getString("duration_mins"));
					restOfTheQuerry = restOfTheQuerry + (resultSet.getString("from_airport_id") + ", " + 
							resultSet.getString("to_airport_id") + ", '" +
							dateFormat.format(departureDate) + "', '" +
							timeFormat.format(departureDate) + "', " +
							duration + ", "
							);

					// get arrival date...
					cal.setTime(departureDate);
					cal.add(Calendar.MINUTE, duration);
					Date arrivalDate = cal.getTime();
					restOfTheQuerry = restOfTheQuerry + ("'" + dateFormat.format(arrivalDate) + "', '" + timeFormat.format(arrivalDate) + "', ");

					// To print flight id...
					restOfTheQuerry = restOfTheQuerry + (flightID[flightIterator] + ", ");

					// To print number of seats left and price...
					int numberOfSeats = (int) (totalNumberOfSeats[flightIterator] * 0.6);
					float price = (float) (duration * 0.8);
					restOfTheQuerry = restOfTheQuerry + (numberOfSeats + ", " + price +");");


					// Inserting the data into the table...
					restOfTheQuerry = querry + restOfTheQuerry;
					insertStatement.execute(restOfTheQuerry);

					// To increment the flight number...
					flightIterator ++;




					// FOR REVERSE JOURNEY....
					// to reset the query...
					restOfTheQuerry = "";

					// To print from_airport, to_airport, departure date, departure time and duration...
					restOfTheQuerry = restOfTheQuerry + (resultSet.getString("to_airport_id") + ", " + 
							resultSet.getString("from_airport_id") + ", '" +
							dateFormat.format(departureDate) + "', '" +
							timeFormat.format(departureDate) + "', " +
							duration + ", "
							);

					// get arrival date...
					restOfTheQuerry = restOfTheQuerry + ("'" + dateFormat.format(arrivalDate) + "', '" + timeFormat.format(arrivalDate) + "', ");

					// To print flight id...
					restOfTheQuerry = restOfTheQuerry + (flightID[flightIterator] + ", ");

					// To print number of seats left and price...
					numberOfSeats = (int) (totalNumberOfSeats[flightIterator] * 0.6);
					restOfTheQuerry = restOfTheQuerry + (numberOfSeats + ", " + price +");");

					// Inserting the data into the table...
					restOfTheQuerry = querry + restOfTheQuerry;
					insertStatement.execute(restOfTheQuerry);

					// To increment the flight number...
					flightIterator ++;
				}while (resultSet.next());

				// next departure date
				cal.setTime(departureDate);
				cal.add(Calendar.HOUR, 24);
				departureDate = cal.getTime();

				// To reset the result set
				resultSet.first();
				System.out.println(day);

			}

			System.out.print("\nDone.");
			 */

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



	}
}
