<?php
	$mysqli = new mysqli("localhost", "root", "", "test");

	/* check connection */
	if ($mysqli->connect_errno) 
	{
		printf("Connect failed: %s\n", $mysqli->connect_error);
		exit();
	}
	
	$query = "SELECT id, city, state, code FROM airport";
	$result = $mysqli->query($query);

	/* numeric array */
	$numberOfRows = $result->num_rows;
	$id = null;
	$city = null;
	$state = null;
	$airportCode = null;
	
	for($i=0; $i<$numberOfRows; $i++)
	{
		$row = $result->fetch_array(MYSQLI_ASSOC);
		$id[$i] = $row["id"];
		$city[$i] = $row["city"];
		$state[$i] = $row["state"];
		$airportCode[$i] = $row["code"];
	}
?>

<html>
    <head>
		<h1><center>My Kayak<center></h1>
    </head>
    <body><form method="post" action="result.php"><center>
    	</br></br> From:
		<select name="from">
		<?php
			for($i=0; $i<$numberOfRows; $i++)
			{ 
				$airportName = $airportCode[$i] . " - " . $city[$i] . ", " . $state[$i];
				printf("<option value=%s>%s</option>\n", $id[$i], $airportName);
			}
		?>
		</select>
		
		</br></br>To:
		<select name="to">
		<?php
			for($i=($numberOfRows-1); $i>-1; $i--)
			{ 
				$airportName = $airportCode[$i] . " - " . $city[$i] . ", " . $state[$i];
				printf("<option value=%s>%s</option>\n", $id[$i], $airportName);
			}
		?>
		</select>
		
		</br></br> Date:
		<input type="date" name="date"> 
		</input>
		
		
		</br></br> Number of Passengers
		<select name="passengers">
			<option value="1"> 1 </option>
			<option value="2"> 2 </option>
			<option value="3"> 3 </option>
			<option value="4"> 4 </option>
			<option value="5"> 5 </option>
			<option value="6"> 6 </option>
		</select>
		
		</br></br> <button type="submit">Submit</button>
	</center></form></body>
</html>