<?php

include '../../php_files/config.php';

      // Get shipping cost

      $sel="SELECT cost FROM delivery_cost LIMIT 1";
      $qry=mysqli_query($con,$sel);

$item= array();
$item['status'] = "1";
$item = array();

      while($rowD=mysqli_fetch_array($qry)){


          // calculate cart total

          $clientID=$_POST['clientID'];

          $select = "SELECT SUM(s.price*oi.quantity) orderCost FROM order_items oi INNER JOIN stock s on oi.stock_id = s.stock_id
          WHERE client_id='$clientID' AND item_status='0'";
          $response = mysqli_query($con, $select);
          $row = mysqli_fetch_array($response);

          $temp['dlyCost']=$rowD['cost']; // delivery cost
          $temp['orderCost'] = $row['orderCost']; // order cost
          $temp['totalCost'] = $row['orderCost']+ $temp['dlyCost']; //  total Cost

          array_push($item, $temp);

      }


  echo json_encode($item);







?>