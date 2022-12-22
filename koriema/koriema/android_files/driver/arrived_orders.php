<?php

include '../../include/connections.php';



    $staffID=$_POST['staffID'];

//creating a query
$select = "SELECT o.order_id,o.order_status,o.order_date,o.county_id,o.town_id,o.address,
c2.first_name,c2.last_name FROM orders o INNER JOIN assign a ON o.order_id = a.order_id
          RIGHT JOIN clients c2 on o.client_id = c2.client_id  WHERE o.order_status='4'AND a.emp_id='$staffID' ORDER BY o.order_id DESC";

  $query=mysqli_query($con,$select);
  if(mysqli_num_rows($query)>0){
      $results= array();
      $results['status'] = "1";
      $results['details'] = array();
      $results['message']="Order to ship";
      while ($row=mysqli_fetch_array($query)){
          $temp = array();

          $temp['orderID'] = $row['order_id'];
          $temp['clientName'] = $row['first_name'].' '.$row['last_name'];
          $temp['orderDate'] = $row['order_date'];
          $temp['address'] = $row['address'];
          $temp['orderStatus'] = "Delivery pending confirmation";

          // get county

          $sel="SELECT county_name FROM counties WHERE county_id='".$row['county_id']."'";
          $qury=mysqli_query($con,$sel);
          $rowC=mysqli_fetch_array($qury);
          $temp['county'] = $rowC['county_name'];

          // get town name

          $selT="SELECT town_name FROM towns WHERE town_id='".$row['town_id']."'";
          $quryT=mysqli_query($con,$selT);
          $rowT=mysqli_fetch_array($quryT);
          $temp['town'] = $rowT['town_name'];

          array_push($results['details'], $temp);

      }


  }else{
      $results['status'] = "0";
      $results['message'] = "Nothing found";

}
//displaying the result in json format
echo json_encode($results);



//$today = date('Ymd');
//$startDate = date('Ymd', strtotime('-100 days'));
//$range = $today - $startDate;
//$rand = rand(100, 999);
//echo $rand;
//echo "</br>";
//$random = substr(md5(mt_rand()), 0, 2);
//echo $random;

?>