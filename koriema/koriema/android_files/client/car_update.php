<?php



// update cart items

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    require_once '../../include/connections.php';

    $qty=$_POST['quantity'];
    $stockID=$_POST['productID'];
    $itemID=$_POST['itemID'];

    // check item quantity

    $select="SELECT stock FROM stock WHERE stock_id='$stockID'";
    $record=mysqli_query($con,$select);
    $row=mysqli_fetch_array($record);

    if($qty >$row['stock']){

        $response["status"] = 0;
        $response["message"] = 'Quantity should not be more than stock available';
        echo json_encode($response);

    }else{

        $update="UPDATE order_items SET quantity='$qty' WHERE item_id='$itemID'";
        if(mysqli_query($con,$update)){

            $response["status"] = 1;
            $response["message"] = 'Cart updated Successfully';
            echo json_encode($response);

        }else{
            $response["status"] = 0;
            $response["message"] ='Failed to update cart';
            echo json_encode($response);
        }
    }

}



?>

<!--<form method="post">-->
<!--    <button type="submit">hkj</button>-->
<!--</form>-->
