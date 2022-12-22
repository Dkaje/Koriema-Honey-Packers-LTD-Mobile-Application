<?php
/**
 * Created by PhpStorm.
 * User: Mwafrika
 * Date: 10/12/2019
 * Time: 2:12 PM
 */

require_once '../../include/connections.php';


// remove from cart

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    $itemID=$_POST['itemID'];

    $update="DELETE FROM order_items WHERE item_id='$itemID'";
    if(mysqli_query($con,$update)){

        $response["status"] = 1;
        $response["message"] ='Item removed from cart';

        echo json_encode($response);
        mysqli_close($con);

    }else{

        $response["status"] = 0;
        $response["message"] ='Failed to update cart';

        echo json_encode($response);
        mysqli_close($con);

    }
}
?>



