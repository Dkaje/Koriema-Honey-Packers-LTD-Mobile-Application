<?php
/**
 * Created by PhpStorm.
 * User: Mwafrika
 * Date: 4/15/2020
 * Time: 12:54 PM
 */


if ($_SERVER['REQUEST_METHOD'] =='POST'){

    require_once '../../php_files/config.php';

    $clientID=$_POST['clientID']=1;
    $stockID=$_POST['stockID'];
    $itemID=$_POST['itemID'];
    $qty=$_POST['quantity'];

    if(empty($quantity)){
        $response["status"] = 0;
        $response["message"] = 'Enter quantity to update ';
        echo json_encode($response);
    }else{


    $select="SELECT stock FROM stock WHERE stock_id='$stockID'";
    $record=mysqli_query($con,$select);
    $row=mysqli_fetch_array($record);

    if($row['stock']< $qty){

        $response["status"] = 0;
        $response["message"] = 'Quantity should be less or equal to '.$row['stock'];
        echo json_encode($response);

    }else{

        $update="UPDATE order_items SET quantity='$quantity' WHERE item_id='$itemID'";
        if(mysqli_query($con,$update)){
            $response["status"] = 1;
            $response["message"] = 'Update successfully ';
            echo json_encode($response);
        }else{
            $response["status"] = 0;
            $response["message"] = 'Failed to update!! Please try again';
            echo json_encode($response);
        }

    }
}
}



?>
