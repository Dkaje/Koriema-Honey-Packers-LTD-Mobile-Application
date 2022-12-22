<?php
/**
 * Created by PhpStorm.
 * User: Mwafrika
 * Date: 4/15/2020
 * Time: 12:54 PM
 */


if ($_SERVER['REQUEST_METHOD'] =='POST'){

    require_once '../../include/connections.php';

    $clientID=$_POST['clientID'];
    $productID=$_POST['productID'];
    $qty=$_POST['quantity'];


    $select="SELECT stock FROM stock WHERE stock_id='$productID'";
    $record=mysqli_query($con,$select);
    $row=mysqli_fetch_array($record);

    if($row['stock']< $qty){

        $response["status"] = 0;
        $response["message"] = 'Quantity cannot be more than '.$row['stock'];
        echo json_encode($response);

    }else{

// check if item exist in client shopping cart

        $select= "SELECT * FROM order_items WHERE client_id='$clientID' AND stock_id='$productID' AND item_status='0'";
        $records=mysqli_query($con,$select);
        if($get=mysqli_num_rows($records) >0){
            $response["status"] = 0;
            $response["message"] ='Item already in  cart !!!';
            echo json_encode($response);
        }else{
            // check if client has an active order
            $select="SELECT * FROM orders WHERE client_id='$clientID' AND order_status='0'";
            $record=mysqli_query($con,$select);
            if ($row=mysqli_num_rows($record) < 1 ){
                // if no active order create a new order
                $insert="INSERT INTO orders(client_id)VALUES ('$clientID')";

                mysqli_query($con,$insert);
                // get the order id of the last inserted id
                $orderNo=mysqli_insert_id($con);

                // insert the order items
                $insrt= "INSERT INTO order_items (client_id, order_id, stock_id, quantity)
             VALUES ('$clientID','$orderNo', '$productID', '$qty')";
                if (mysqli_query($con,$insrt)) {
                    $response["status"] = 1;
                    $response["message"] ='Added to shopping cart successfully';
                    echo json_encode($response);

                }else{
                    $response["status"] = 0;
                    $response["message"] ='Failed to add to cart';
                    echo json_encode($response);
                }

            }else{

                // if client has an active order get the order id

                $select="SELECT order_id,order_status FROM orders WHERE client_id='$clientID' AND order_status='0'";
                $record=mysqli_query($con,$select);
                $rowC=mysqli_fetch_assoc($record);
                $orderNo=$rowC['order_id'];

                // insert order id and item id, client id and quantity   in to order items

                $insert= "INSERT INTO order_items (client_id, order_id, stock_id,quantity)
                  VALUES ('$clientID', '$orderNo', '$productID', '$qty')";
                if (mysqli_query($con,$insert)) {
                    $response["status"] = 1;
                    $response["message"] ='Added to shopping cart successfully';
                    echo json_encode($response);
                }else{
                    $response["status"] = 0;
                    $response["message"] =$productID. 'Failed please try again'. $clientID;
                    echo json_encode($response);

                }
            }
        }
    }
}



?>

<!--<form method="post">-->
<!--    <button type="submit">send</button>-->
<!--</form>-->