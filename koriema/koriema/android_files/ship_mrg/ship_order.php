<?php

include "../../include/connections.php";


 if($_SERVER['REQUEST_METHOD']=='POST'){

     $orderID=$_POST['orderID'];
     $username=$_POST['username'];

     $select="SELECT * FROM employees WHERE username='$username'";
     $query=mysqli_query($con,$select);
     $row=mysqli_fetch_array($query);

     $empID=$row['emp_id'];

     $update="UPDATE orders SET order_status='3'WHERE order_id='$orderID'";
     if(mysqli_query($con,$update)){

         $insert="INSERT INTO assign ( emp_id, order_id)VALUES ('$empID','$orderID')";
         mysqli_query($con,$insert);

         $response['status']=1;
         $response['message']='Shipped successfully';

     }else{
         $response['status']=0;
         $response['message']='Please try again';


     }
     echo json_encode($response);
      }
?>