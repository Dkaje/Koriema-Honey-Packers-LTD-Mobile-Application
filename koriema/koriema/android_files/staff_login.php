<?php

include '../include/connections.php';

//insert

if ($_SERVER['REQUEST_METHOD'] =='POST') {

    $username = $_POST['username'];
    $password = $_POST['password'];


    if ( empty($username) ||empty($password)) {
        $response["status"] = 0;
        $response["message"] = "Enter both username and password ";
        echo json_encode($response);
        mysqli_close($con);
    } else {
        // check if username already exists
        $select = "SELECT emp_id, f_name, l_name, username, contact, email, status,
       date_added, password,userlevel FROM employees 
WHERE username='$username' AND password='$password' ";
        $query = mysqli_query($con, $select);
        if (mysqli_num_rows($query) > 0) {
            while ($row=mysqli_fetch_array($query)){

                    $response['status'] = "1";
                    $response['details'] = array();
                    $response["message"] = "Login successful";
                    $index['clientID']=$row['emp_id'];
                    $index['firstname']=$row['f_name'];
                    $index['lastname']=$row['l_name'];
                    $index['username']=$row['username'];
                    $index['phoneNo']=$row['contact'];
                    $index['email']=$row['email'];
                    $index['dateCreated']=$row['date_added'];
                    $index['user']=$row['userlevel'];
                    array_push($response['details'],$index);
                    echo json_encode($response);
                }

        } else {
            $response["status"] = 0;
            $response["message"] = "Please confirm your username and password";
            echo json_encode($response);
                }
            }
}




