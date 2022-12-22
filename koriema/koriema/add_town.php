<?php
session_start();
error_reporting(E_ERROR);
include('include/connections.php');
ob_start();

if(empty($_SESSION['IDstaff'])){
    header('location:index.php');
}




if(isset($_POST['addBtn'])){
    $county=$_POST['county'];
    $town=$_POST['town'];


    if(empty($county)|| empty($town)){
        $swal="error";
        $fb="Please enter all details";
    }else{

        $select="SELECT * FROM counties WHERE county_name='$county'";
        $query=mysqli_query($con,$select);
        $row=mysqli_fetch_array($query);
        $countyID=$row['county_id'];

        $set="SELECT * FROM towns WHERE county_id='$countyID'AND town_name='$town'";
        $query=mysqli_query($con,$set);
        if($check=mysqli_num_rows($query)>0){
            $swal="error";
            $fb=$town." in ".$county." county exist in the system";
        }else{
            $insert="INSERT INTO towns(town_name, county_id) VALUES ('$town','$countyID')";
            if(mysqli_query($con,$insert)){
                $swal="success";
                $fb="Submitted successfully";
            }else{
                $swal="error";
                $fb="Something went wrong. Please try again";
            }

        }

    }

}

ob_end_flush();

?>
<!DOCTYPE html>
<html>

<!-- Mirrored from webarch.revox.io/3.0/html/tables.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 29 Jan 2019 22:58:56 GMT -->
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <meta charset="utf-8" />
    <title><?php echo $siteName?></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta content="" name="description" />
    <meta content="" name="author" />

    <script src="../../cdn-cgi/apps/head/QJpHOqznaMvNOv9CGoAdo_yvYKU.js"></script>
    <link href="assets/plugins/pace/pace-theme-flash.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="assets/plugins/bootstrapv3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/plugins/bootstrapv3/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="assets/plugins/animate.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/plugins/jquery-scrollbar/jquery.scrollbar.css" rel="stylesheet" type="text/css" />
    <link href="webarch/css/webarch.css" rel="stylesheet" type="text/css" />

    <script>
        if ( window.history.replaceState ) {
            window.history.replaceState( null, null, window.location.href );
        }
    </script>
</head>


<body class="">
<?php
include 'include/navbar.php';
?>

<a href="#" class="scrollup">Scroll</a>
<div class="footer-widget">
<div class="progress transparent progress-small no-radius no-margin">
<div class="progress-bar progress-bar-success animate-progress-bar" data-percentage="79%" style="width: 79%;"></div>
</div>

</div>
<div class="page-content">

<div class="clearfix"></div>
<div class="content">

<div class="page-title">

</div>
<div class="row">
<div class="col-md-12">
<div class="grid simple ">
<div class="grid-title no-border">

</div>
<div class="grid-body no-border">
    <a href="products.php"class="btn-danger btn-xs btn">Back</a>

    <div class="row">
        <div class="col-md-offset-4 col-md-4">
            <?php
            if(!empty($fb)){
                echo '<div class="alert alert-info">'. $fb.'</div>';
            }

            ?>
            <form method="post" autocomplete="off">
                <label>County name</label>
                <select class="form-control"name="county">
                    <option><?php echo $county?></option>
                    <?php
                    $select="SELECT * FROM counties";
                    $query=mysqli_query($con,$select);
                    while ($rowC=mysqli_fetch_array($query)){
                        ?>
                        <option><?php echo $rowC['county_name']?></option>
                        <?php
                    }
                    ?>
                </select>
                <label>Town name</label>
                <input type="text"name="town"value="<?php echo $town;?>" class="form-control">
                <br>

                <input type="submit" name="addBtn"value="Submit"class="btn btn-primary form-control">

            </form>

        </div>
    </div>

</div>
</div>
</div>
</div>

</div>

</div>


</div>
<script src="assets/plugins/pace/pace.min.js" type="text/javascript"></script>

<script src="assets/plugins/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrapv3/js/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-block-ui/jqueryblockui.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-unveil/jquery.unveil.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-scrollbar/jquery.scrollbar.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-numberAnimate/jquery.animateNumbers.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-select2/select2.min.js" type="text/javascript"></script>


<script src="webarch/js/webarch.js" type="text/javascript"></script>
<script src="assets/js/chat.js" type="text/javascript"></script>

<script src="assets/plugins/jquery-sparkline/jquery-sparkline.js"></script>
<script>
    //TODO AT TO API
    $('table .checkbox input').click(function()
    {
        if ($(this).is(':checked'))
        {
            $(this).parent().parent().parent().toggleClass('row_selected');
        }
        else
        {
            $(this).parent().parent().parent().toggleClass('row_selected');
        }
    });
    // Demo charts - not required 
    $('.customer-sparkline').each(function()
    {
        $(this).sparkline('html',
            {
                type: $(this).attr("data-sparkline-type"),
                barColor: $(this).attr("data-sparkline-color"),
                enableTagOptions: true
            });
    });
</script>
</body>

<!-- Mirrored from webarch.revox.io/3.0/html/tables.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 29 Jan 2019 22:58:56 GMT -->
</html>