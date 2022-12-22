<?php

include '../../include/connections.php';

$clientID=$_POST['clientID'];
//creating a query
$select = "SELECT s.product_name,s.stock,s.stock_id,s.price,s.image,oi.item_id,oi.quantity FROM stock s 
  INNER JOIN order_items oi on s.stock_id = oi.stock_id RIGHT JOIN
  orders o on oi.order_id = o.order_id WHERE oi.client_id='$clientID' AND o.order_status='0'";

  $query=mysqli_query($con,$select);
  if(mysqli_num_rows($query)>0){

      $item= array();
      $item['status'] = "1";
      $item['items'] = array();
      while ($row=mysqli_fetch_array($query)){
          $temp = array();

          $temp['itemID'] = $row['item_id'];
          $temp['productID'] = $row['stock_id'];
          $temp['quantity'] = $row['quantity'];
          $temp['price'] = $row['price'];
          $temp['productName'] = $row['product_name'];
          $temp['image'] = $row['image'];
          $temp['stock'] = $row['stock'];

          $temp['subTotal'] = $temp['price'] * $temp['quantity'];


          array_push($item['items'], $temp);

      }
   
      // calculate cart total

      $select = "SELECT SUM(s.price*oi.quantity) cartTotal FROM order_items oi INNER JOIN stock s on oi.stock_id = s.stock_id
          WHERE client_id='$clientID' AND item_status='0'";
      $response = mysqli_query($con, $select);

      while ($row = mysqli_fetch_array($response)) {
          $item['cartTotal'] = $row['cartTotal'];


      }
  }else{
    $item['status'] = "0";
}

echo json_encode($item);







?>