<?php

include '../../include/connections.php';

$select="SELECT * FROM clients WHERE user='Supplier' AND status='Approved'";
$query=mysqli_query($con,$select);
if(mysqli_num_rows($query)>0){
    $response['status']="1";
    $response['message']="Suppliers";
    $response['details']=array();
    while ($row=mysqli_fetch_array($query)){
        $index['username']=$row["username"];
        array_push($response['details'],$index);
    }
}else{
    $response['status']="0";
    $response['message']="No suppliers found";
}
print json_encode($response);