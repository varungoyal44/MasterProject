<?php
	$mysqli = new mysqli("localhost", "root", "", "test");

	/* check connection */
	if ($mysqli->connect_errno) 
	{
		printf("Connect failed: %s\n", $mysqli->connect_error);
		exit();
	}
	
	$from = $_POST["from"];
	$to = $_POST["to"];
	$depdate = $_POST["date"];
	$passengers = $_POST["passengers"];
	
	$query = "Select id, departure_date, departure_time, duration_mins, arrival_date, arrival_time, flight_id from schedule where from_airport_id = $from  and to_airport_id = $to;";
	
	$result = $mysqli->query($query);
	
	/* numeric array */
	$numberOfRows = $result->num_rows;
	$id = null;
	$departure_date = null;
	$departure_time = null;
	$duration_mins = null;
	
	for($i=0; $i<$numberOfRows; $i++)
	{
		$row = $result->fetch_array(MYSQLI_ASSOC);
		$id[$i] = $row["id"];
		$departure_date[$i] = $row["departure_date"];
		$departure_time[$i] = $row["departure_time"];
		$duration_mins[$i] = $row["duration_mins"];
	}
?>


<HTML>
<head> depDate = <?php echo $depdate ?> </head>
	<body>


<?php
	for($i=0; $i<$numberOfRows; $i++)
	{ 
		printf(" </br> departure_date: %s, departure_time: %s", $departure_date[$i], $departure_time[$i]);
	}
?>

	</body>
</HTML>