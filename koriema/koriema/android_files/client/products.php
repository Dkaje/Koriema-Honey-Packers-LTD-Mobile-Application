<?php

include '../../include/connections.php';

$select="SELECT * FROM stock";

$records=mysqli_query($con,$select);

       $results['status'] = "1";

       $results['products'] = array();

       while ($row=mysqli_fetch_array($records)){


    $temp['productID'] = $row['stock_id'];
    $temp['productName'] = $row['product_name'];
    $temp['price'] = $row['price'];
    $temp['stock'] = $row['stock'];
    $temp['image'] = $row['image'];
    $temp['desc'] = $row['description'];

    array_push($results['products'], $temp);

}


//displaying the result in json format
echo json_encode($results);







?>