<?php

include "../../include/connections.php";


 if($_SERVER['REQUEST_METHOD']=='POST'){

     $orderID=$_POST['orderID'];


     $update="UPDATE orders SET order_status='5'WHERE order_id='$orderID'";
     if(mysqli_query($con,$update)){

         $update="UPDATE assign SET assign_status='Delivered'WHERE order_id='$orderID'";
         mysqli_query($con,$update);

         $response['status']=1;
         $response['message']='Submitted successfully';

     }else{
         $response['status']=0;
         $response['message']='Please try again';


     }
     echo json_encode($response);
      }
?>