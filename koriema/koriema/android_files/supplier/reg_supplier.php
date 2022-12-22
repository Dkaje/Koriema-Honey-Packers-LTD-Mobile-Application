<?php

include '../../include/connections.php';

//insert

if ($_SERVER['REQUEST_METHOD'] =='POST') {

    $firstname = $_POST['firstname'];
    $lastname = $_POST['lastname'];
    $username = $_POST['username'];
    $phoneNo = $_POST['phoneNo'];
    $email = $_POST['email'];
    $password = $_POST['password'];


    if (empty($firstname) || empty($lastname) || empty($username) || empty($email) || empty($phoneNo) || empty($password)) {
        $response["status"] = 0;
        $response["message"] = "Some details are missing ";
        echo json_encode($response);
        mysqli_close($con);

    } else {

        // check if username already exists

        $select = "SELECT username FROM clients WHERE username='$username'";
        $query = mysqli_query($con, $select);
        if (mysqli_num_rows($query) > 0) {

            $response["status"] = 0;
            $response["message"] = "Username is registered with another account";
            echo json_encode($response);

        } else {

            $select = "SELECT phone_no FROM clients WHERE phone_no='$phoneNo'";
            $query = mysqli_query($con, $select);
            if (mysqli_num_rows($query) > 0) {

                $response["status"] = 0;
                $response["message"] = "Phone number is registered with another account";
                echo json_encode($response);

            } else {
                $select = "SELECT email FROM clients WHERE email='$email'";
                $query = mysqli_query($con, $select);
                if (mysqli_num_rows($query) > 0) {

                    $response["status"] = 0;
                    $response["message"] = "Email is registered with another account";
                    echo json_encode($response);

                } else {

                    $insert = "INSERT INTO clients(first_name, last_name, username, phone_no, email,password,user)
                VALUES ('$firstname','$lastname','$username','$phoneNo','$email','$password','Supplier')";
                    if (mysqli_query($con, $insert)) {

                        $response["status"] = 1;
                        $response["message"] = "You have successfully registered";

                        echo json_encode($response);
//                    mysqli_close($con);

                    } else {

                        $response["status"] = 0;
                        $response["message"] = " Something went wong please try again";

                        echo json_encode($response);
//                    mysqli_close($con);
                    }

                }
            }
        }
    }
}




