<?php

include '../../include/connections.php';


$clientID=$_POST['userID'];

//creating a query
$select = "SELECT * FROM feedback f INNER JOIN clients c ON f.client_id=c.client_id
WHERE c.client_id='$clientID'";

  $query=mysqli_query($con,$select);
  if(mysqli_num_rows($query)>0){

      $results= array();
      $results['status'] = "1";
      $results['details'] = array();
      $results['message']="Feedback";
      while ($row=mysqli_fetch_array($query)){
          $temp = array();

          $temp['comment'] = $row['comment'];
          if($row['fb']==""){
              $temp['reply'] = 0;
          }else{
              $temp['reply'] = $row['fb'];
          }


          array_push($results['details'], $temp);

      }


  }else{
    $results['status'] = "0";
      $results['message'] = "Nothing found";

}
//displaying the result in json format
echo json_encode($results);



?>