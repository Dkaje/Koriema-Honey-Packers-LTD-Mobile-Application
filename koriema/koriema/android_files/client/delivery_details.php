<?php

include '../../include/connections.php';


$clientID=$_POST['clientID'];
//creating a query
$select = "SELECT c.county_id,c.county_name,t.town_name,t.town_name,d.address FROM delivery d 
          INNER JOIN towns t on d.town_id = t.town_id
     RIGHT JOIN counties c ON c.county_id=d.county_id WHERE d.client_id='$clientID'";

  $query=mysqli_query($con,$select);
  if(mysqli_num_rows($query)>0){
      $results= array();
      $results['status'] = "1";
      $results['details'] = array();
      while ($row=mysqli_fetch_array($query)){
          $temp = array();

          $temp['county_ID'] = $row['county_id'];
          $temp['county'] = $row['county_name'];
          $temp['town'] = $row['town_name'];
          $temp['ship_address'] = $row['address'];
          array_push($results['details'], $temp);

      }


  }else{
    $results['status'] = "0";
      $results['message'] = "Delivery details missing";

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