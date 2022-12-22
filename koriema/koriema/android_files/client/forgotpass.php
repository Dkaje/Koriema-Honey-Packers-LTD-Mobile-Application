<?php

include '../../include/connections.php';

//insert

if ($_SERVER['REQUEST_METHOD'] =='POST') {

   
    $email = $_POST['email'];
    $password = $_POST['password'];


    if (empty($email) || empty($password)) {
        $response["status"] = 0;
        $response["message"] = "Some details are missing ";
        echo json_encode($response);
        mysqli_close($con);

    } else {

                $select = "SELECT email FROM clients WHERE email='$email'";
                $query = mysqli_query($con, $select);
                if (mysqli_num_rows($query) > 0) {

                     $insert = "UPDATE  clients SET password='$password' WHERE email='$email'";
                    if (mysqli_query($con, $insert)) {

                        $response["status"] = 1;
                        $response["message"] = "Password reset sucessful";

                        echo json_encode($response);
//                    mysqli_close($con);

                    } else {

                        $response["status"] = 0;
                        $response["message"] = " Something went wong please try again";

                        echo json_encode($response);
//                    mysqli_close($con);
                    }

                } else {

                     $response["status"] = 0;
                    $response["message"] = "Email not found";
                    echo json_encode($response);
                    
                }
            }
        }




