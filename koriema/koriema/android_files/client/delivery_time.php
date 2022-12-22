<?php

include '../../php_files/config.php';

//creating a query
$stmt = $con->prepare("SELECT start_time,end_time FROM delivery_time;");

//executing the query
$stmt->execute();

//binding results to the query
$stmt->bind_result($startTime,$endTime);

$item= array();
$item['status'] = "1";
$item = array();

//traversing through all the result
while($stmt->fetch()){
    $temp = array();

    $temp['startTime'] = $startTime;
    $temp['endTime'] = $endTime;

    array_push($item, $temp);
}


//displaying the result in json format
echo json_encode($item);







?>