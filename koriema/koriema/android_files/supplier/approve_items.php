<?php

include "../../include/connections.php";


 if($_SERVER['REQUEST_METHOD']=='POST'){

    $requestID=$_POST['requestID'];

    $update="UPDATE request SET request_status='Approve'WHERE id='$requestID'";
    if(mysqli_query($con,$update)){

        $response['status']=1;
        $response['message']='Approved successfully';

    }else{
        $response['status']=0;
        $response['message']='Please try again';

    }
    echo json_encode($response);
}
?>