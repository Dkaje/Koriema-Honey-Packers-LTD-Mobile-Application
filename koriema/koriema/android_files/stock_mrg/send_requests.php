<?php


include '../../include/connections.php';

$username=$_POST['username'];
$items=$_POST['items'];

  $select="SELECT * FROM clients WHERE username='$username'";
  $query=mysqli_query($con,$select);
  $row=mysqli_fetch_array($query);
  $id=$row["client_id"];

   $insert="INSERT INTO request(client_id, items)VALUES ('$id','$items')";
  if(mysqli_query($con,$insert)){
    $response['status']=1;
    $response['message']='Submitted successfully';
    }else{
    $response['status']=0;
    $response['message']='Please try again. Something went wrong';
  }
echo json_encode($response);
