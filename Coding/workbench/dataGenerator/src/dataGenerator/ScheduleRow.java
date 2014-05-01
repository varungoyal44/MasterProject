package dataGenerator;

public class ScheduleRow 
{
	public String from_airport_id; 
	public String to_airport_id; 
	public String departure_date;
	public String departure_time; 
	public int duration_mins; 
	public String arrival_date; 
	public String arrival_time; 
	public String flight_id; 
	public int number_of_seats_left; 
	public float price;

	public String getQuery()
	{
		// initial part of the insert query...
		String query = "INSERT INTO test.schedule (from_airport_id, to_airport_id, departure_date, "
				+ "departure_time, duration_mins, arrival_date, arrival_time, flight_id, number_of_seats_left, price) VALUES("
				+ from_airport_id + ", " + to_airport_id  + ", '" + departure_date + "', '" + departure_time + "', " + 
				duration_mins + ", '" + arrival_date + "', '" + arrival_time + "', " + flight_id + ", " + number_of_seats_left
				 + ", " + price + ");";

		return query;
	}

}
