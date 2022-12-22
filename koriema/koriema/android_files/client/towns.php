<?php

include '../../include/connections.php';

$countyName=$_POST['countyName'];
$select="SELECT * FROM counties c INNER JOIN  towns t on c.county_id = t.county_id WHERE county_name='$countyName'";

$records=mysqli_query($con,$select);

       $results['status'] = "1";

       $results['towns'] = array();

       while ($row=mysqli_fetch_array($records)){


    $temp['townName'] = $row['town_name'];
    $temp['countyID'] = $row['county_id'];

    array_push($results['towns'], $temp);

}


//displaying the result in json format
echo json_encode($results);







?>