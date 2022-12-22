<?php

include '../../include/connections.php';

//insert

if ($_SERVER['REQUEST_METHOD'] =='POST') {


    

    $feedback = $_POST['feedback'];
    $userID = $_POST['userID'];


    $select = "SELECT * FROM employees WHERE emp_id='$userID'";
          $response = mysqli_query($con, $select);
          $row = mysqli_fetch_array($response);

          $userlevel= $row['userlevel']; 
  
    $insert="UPDATE feedback SET fb = '$feedback' 
        WHERE staff_id = '$userlevel'";

        if(mysqli_query($con,$insert)){
        $response["status"] = 1;
        $response["message"] = "Sent";

        } else {
        $response["status"] = 0;
        $response["message"] = "something went wrong";

    }
    echo json_encode($response);
}




