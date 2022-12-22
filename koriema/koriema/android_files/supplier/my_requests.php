<?php


include '../../include/connections.php';

$userID=$_POST["userID"];
$select="SELECT * FROM request WHERE client_id='$userID' ORDER BY id DESC";
$query=mysqli_query($con,$select);
if(mysqli_num_rows($query)>0){
    $response['status']=1;
    $response['details']=array();
    $response['message']='Request';
while($row=mysqli_fetch_array($query)){
    $index["requestID"]=$row["id"];
    $index["items"]=$row["items"];
    $index["requestStatus"]=$row["request_status"];
    $index["requestDate"]=$row["request_date"];

    array_push($response["details"],$index);

}

}else{
    $response['status']=0;
    $response['message']='Please try again. Something went wrong';
}
echo json_encode($response);
