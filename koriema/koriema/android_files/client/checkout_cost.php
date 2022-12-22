<?php

              include '../../include/connections.php';

              $clientID=$_POST['clientID'];
// delivery cost
               $select="SELECT * FROM delivery_cost";
              $records=mysqli_query($con,$select);
               if(mysqli_num_rows($records)>0){

    $results['status'] = "1";


   $row=mysqli_fetch_array($records);

          // order cost
        $select = "SELECT SUM(s.price*oi.quantity) cartTotal FROM order_items oi 
            INNER JOIN stock s on oi.stock_id = s.stock_id
          WHERE client_id='$clientID' AND item_status='0'";
        $response = mysqli_query($con,$select);
        $rowC=mysqli_fetch_array($response);

                   $results['orderCost']=$rowC['cartTotal'];  // order total cost
                   $results['deliveryCost'] = $row['cost'];   // delivery cost


        echo json_encode($results);
         }








?>