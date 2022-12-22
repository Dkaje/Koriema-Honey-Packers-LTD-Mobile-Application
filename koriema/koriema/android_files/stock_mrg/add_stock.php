<?php


     include '../../include/connections.php';

     $quantity=$_POST['quantity'];
     $stockID=$_POST['stockID'];

     $update="UPDATE stock SET stock=stock+'$quantity'WHERE stock_id='$stockID'";
     if(mysqli_query($con,$update)){
         $response['status']=1;
         $response['message']='Stock update successfully';
     }else{
         $response['status']=0;
         $response['message']='Please try again. Something went wrong';
     }
     echo json_encode($response);

     ?>