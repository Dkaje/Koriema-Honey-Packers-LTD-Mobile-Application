<?php

include '../include/connections.php';

$orderID=$_POST['orderID'];

//creating a query
$select = "SELECT * FROM orders WHERE order_id='$orderID'";

  $query=mysqli_query($con,$select);
  if(mysqli_num_rows($query)>0){


      $rowp=mysqli_fetch_array($query);
      $item['status'] = "1";
      if($rowp['order_status']==1){
          $item['orderStatus'] = "Pending";
      }elseif ($rowp['order_status']==2){
          $item['orderStatus'] = "Confirmed";
      }elseif ( $rowp['order_status']==3){
          $item['orderStatus'] = "Delivered";
      }
      $item['status'] = "1";
      $item['message'] = "Order items";
      $item['details'] = array();

      $select = "SELECT s.product_name,s.stock_id,s.price,oi.item_id,oi.quantity FROM stock s 
  INNER JOIN order_items oi on s.stock_id = oi.stock_id  WHERE oi.order_id='$orderID'";
      $query=mysqli_query($con,$select);
      while ($row=mysqli_fetch_array($query)){
          $temp = array();

          $temp['quantity'] = $row['quantity'];
          $temp['itemPrice'] = $row['price'];
          $temp['itemName'] = $row['product_name'];
          $temp['subTotal'] = $row['price'] * $row['quantity'];
          array_push($item['details'], $temp);

      }


      // calculate cart total
      $select = "SELECT SUM(s.price*oi.quantity) cartTotal FROM order_items oi INNER JOIN stock s on oi.stock_id = s.stock_id
          WHERE order_id='$orderID'";
      $response = mysqli_query($con, $select);
      while ($row = mysqli_fetch_array($response)) {
          $_SESSION['cartTotal'] = $row['cartTotal'];

      }
      $item['cartTotal'] = $_SESSION['cartTotal'];
  }else{
    $item['status'] = "0";
    $item['message'] = "No record found";
}
//displaying the result in json format
echo json_encode($item);







?>