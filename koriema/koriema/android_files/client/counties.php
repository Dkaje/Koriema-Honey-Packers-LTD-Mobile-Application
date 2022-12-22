<?php

include '../../include/connections.php';

$select="SELECT * FROM counties";

$records=mysqli_query($con,$select);

       $results['status'] = "1";

       $results['counties'] = array();

       while ($row=mysqli_fetch_array($records)){


    $temp['countyName'] = $row['county_name'];

    array_push($results['counties'], $temp);

}


//displaying the result in json format
echo json_encode($results);







?>