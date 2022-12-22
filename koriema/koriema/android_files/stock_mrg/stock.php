  <?php


  include '../../include/connections.php';

  $select="SELECT * FROM stock";
  $query=mysqli_query($con,$select);


    if(mysqli_num_rows($query)>0){
        $response['status']=1;
        $response['message']="Stock";
        $response['details']=array();

        while ($row=mysqli_fetch_array($query)){
            $index['stockID']=$row['stock_id'];
            $index['productName']=$row['product_name'];
            $index['price']=$row['price'];
            $index['stock']=$row['stock'];

            array_push($response['details'],$index);
        }
        echo json_encode($response);
    }
    ?>