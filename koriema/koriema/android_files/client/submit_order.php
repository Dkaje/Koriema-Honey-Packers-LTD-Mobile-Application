<?php
/**
 * Created by PhpStorm.
 * User: Mwafrika
 * Date: 10/12/2019
 * Time: 11:49 AM
 */

// Make payment

if ($_SERVER['REQUEST_METHOD'] =='POST') {

    include '../../include/connections.php';


    $clientID = $_POST['clientID'];
    $countyID = $_POST['countyID'];
    $townName = $_POST['townName'];
    $address = $_POST['address'];
    $deliveryCost = $_POST['shippingCost'];
    $totalCost = $_POST['totalCost'];
    $orderCost = $_POST['orderCost'];
    $mpesaCode = $_POST['mpesaCode'];



   if (empty($townName)) {
        $result['status'] = "0";
        $result['message'] = "Enter address";
        echo json_encode($result);
    } elseif (empty($address)) {
        $result['status'] = "0";
        $result['message'] = "Enter address";
        echo json_encode($result);
    } elseif (empty($mpesaCode)) {
        $result['status'] = "0";
        $result['message'] = "Enter Mpesa code";
        echo json_encode($result);

    } else {

        // get order id where client order status = cart
        $select = "SELECT order_id FROM orders WHERE client_id='$clientID'AND order_status='0'";
        $query = mysqli_query($con, $select);
        $row = mysqli_fetch_array($query);
        $orderID = $row['order_id'];  // order ID

        // get item price to insert to order items
        $select = "SELECT s.price FROM order_items oi INNER JOIN stock s on oi.stock_id = s.stock_id
        WHERE oi.order_id='$orderID'";
        $query = mysqli_query($con, $select);
        while ($row = mysqli_fetch_array($query)) {
            $price = $row['price'];  // item price

            // update order items price
            $update = "UPDATE order_items SET item_price='$price' WHERE order_id='$orderID'";
            if (mysqli_query($con, $update)) {

                    // get town id
                    $slect = "SELECT town_id FROM towns WHERE town_name='$townName' AND county_id='$countyID'";
                    $query = mysqli_query($con, $slect);
                    $row = mysqli_fetch_array($query);
                    $townID = $row['town_id'];


                    // check if client has already submitted delivery details
                    //if has not enter the delivery details entered by the client

                    $select = "SELECT * FROM delivery WHERE client_id='$clientID'";
                    $query = mysqli_query($con, $select);
                    if (mysqli_num_rows($query) < 1) {
                        // insert the delivery details
                        $insert = "INSERT INTO delivery(county_id, town_id, client_id,address) VALUES ('$countyID','$townID','$clientID','$address')";
                        mysqli_query($con, $insert);

                    } else {

                        // if user had submitted delivery details update delivery details in case user change delivery details
                        $update = "UPDATE delivery SET county_id='$countyID',town_id='$townID',address='$address'WHERE client_id='$clientID'";
                        mysqli_query($con, $update);

                    }


                    // update client order status and insert delivery details

                    // get current date
                    $update = "UPDATE orders SET county_id='$countyID',town_id='$townID',address='$address',order_date=CURRENT_TIMESTAMP ,order_status='1'
                    WHERE order_id='$orderID'AND client_id='$clientID'";
                    if (mysqli_query($con, $update)) {

                        // if order status updated statusfully update order items status
                        $update = "UPDATE order_items SET item_status='1' WHERE order_id='$orderID'";
                        mysqli_query($con, $update);

                        // insert Payment details
                        $insert = "INSERT INTO payment(order_id, mpesa_code, client_id, order_cost, delivery_cost, total_cost, status)
                          VALUES ('$orderID','$mpesaCode','$clientID','$orderCost','$deliveryCost','$totalCost','1')";
                        mysqli_query($con, $insert);

                        // update items stock levels

                        $select = "SELECT stock_id,quantity,item_price FROM order_items WHERE order_id='$orderID'";
                        $query = mysqli_query($con, $select);
                        while ($row = mysqli_fetch_array($query)) {

                            $stockID = $row['stock_id'];  // get item id
                            $quantity = $row['quantity']; // get item quantity

                            $update = "UPDATE stock SET stock=stock-'$quantity'WHERE stock_id='$stockID'";
                            mysqli_query($con, $update);

                        }

                        $result['status'] = "1";
                        $result['message'] = "Order submitted successfully";
                        echo json_encode($result);

                    }

            }else{
                $result['status'] = "0";
                $result['message'] = "Something went long. Please try again" ;
                echo json_encode($result);
            }
        }
    }
}