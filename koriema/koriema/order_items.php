<?php
session_start();
//error_reporting(E_ERROR);
include('include/connections.php');


?>
<!DOCTYPE html>
<html>

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
<h3><span class="semi-bold">Order items</span></h3>
</div>
<div class="row">
<div class="col-md-12">
<div class="grid simple ">

<div class="grid-body no-border">
    <div class="row">
        <div class="col-md-5">
            <div class="row">
                <div class="col-lg-12">
                    Order details
                    <?php
                    $select="SELECT * FROM clients c INNER JOIN orders o on c.client_id = o.client_id
                       RIGHT JOIN payment p on o.order_id = p.order_id WHERE  o.order_id=".$_GET['get'];
        $query=mysqli_query($con,$select);
       $row=mysqli_fetch_array($query);
                    ?>
                    <table class="table table-bordered">
                        <tr><td><b> Name</b></td><td><?php print $row['first_name']." ".$row['last_name']?></td></tr>
                        <tr><td><b> Phone number</b></td><td><?php print $row['phone_no']?></td></tr>
                        <tr><td><b> Total cost KES</b></td><td><?php print number_format($row['order_cost'])?></td></tr>
                        <tr><td><b> Mpesa code</b></td><td><?php print $row['mpesa_code']?></td></tr>
                        <tr><td><b> Status</b></td><td>
                                <?php
                                if($row['order_status']==1){
                                   print "Pending approval";
                                }
                                if($row['order_status']==2){
                                    print "Approved";
                                }
                                if($row['order_status']==3){
                                    print "Shipping";
                                }
                                if($row['order_status']==5){
                                    print "Delivered";
                                }
                                ?>
                            </td></tr>

                    </table>

                </div>
                <div class="col-lg-12">
                    <p>Shipping details</p>
                    <?php
                    $select="SELECT * FROM clients c INNER JOIN delivery d on c.client_id = d.client_id
                      RIGHT JOIN counties c2 on d.county_id = c2.county_id RIGHT JOIN towns t on c2.county_id = t.county_id
                      WHERE c.client_id=".$row['client_id'];
                    $qly=mysqli_query($con,$select);
                    $row2=mysqli_fetch_array($qly);
                    ?>
                    <table class="table table-bordered">
                    <tr><td><b> County</b></td><td><?php print $row2['county_name']?></td></tr>
                    <tr><td><b> Town</b></td><td><?php print $row2['town_name']?></td></tr>
                    <tr><td><b>Address</b></td><td><?php print $row2['address']?></td></tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-6">

            Order items
            <table class="table table-bordered">
                <th>Item </th>
                <th>Quantity </th>
                <?php
                $select="SELECT * FROM orders o INNER JOIN  order_items oi on o.order_id = oi.order_id
            RIGHT JOIN stock s on oi.stock_id = s.stock_id WHERE o.order_id=".$_GET['get'];
                $query=mysqli_query($con,$select);
                while($row3=mysqli_fetch_array($query)){
                    ?>
                   <tr>
                       <td><?php print $row3['product_name']?></td>
                       <td><?php print $row3['quantity']?></td>
                   </tr>
                <?php
                }
                ?>
            </table>

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
<script src="dist/js/jquery.dcjqaccordion.2.7.js"></script>
<link type="text/css" rel="stylesheet" href="dist/dataTablesCustom/jquery.dataTables.css?"/>
<script src="dist/dataTablesCustom/jquery.dataTables.min.js"></script>
<script src="dist/dataTablesCustom/dataTables.buttons.min.js"></script>
<script src="dist/dataTablesCustom/buttons.flash.min.js"></script>
<script src="dist/dataTablesCustom/jszip.min.js"></script>
<script src="dist/dataTablesCustom/pdfmake.min.js"></script>
<script src="dist/dataTablesCustom/vfs_fonts.js"></script>
<script src="dist/dataTablesCustom/buttons.html5.min.js"></script>
<script src="dist/dataTablesCustom/buttons.print.min.js"></script>
<script src="dist/dataTablesCustom/buttons.colVis.min.js"></script>

<script>


    $(document).ready(function () {

        var datatable = $('#dataTables-example').dataTable(

            {
                "dom": 'lBfrtip',
                "buttons": [
                    {
                        extend: 'pdf',
                        text:'Print PDF',
                        footer: true,
                        exportOptions: {
                            columns: [1,2,3,4,5,6]
                        }
                    },

                ]

            }
        );

    });
</script>
</html>