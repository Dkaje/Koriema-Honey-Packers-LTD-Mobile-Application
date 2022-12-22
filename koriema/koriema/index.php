<?php
session_start();
if (!isset($_SESSION['username'])) {
    $_SESSION['msg'] = "You must log in first";
    header('location: userlogin.php');
  }
  if (isset($_GET['logout'])) {
    session_destroy();
    unset($_SESSION['username']);
    header("location: userlogin.php");
  }
error_reporting(E_ERROR);
include('include/connections.php');
?>
<!DOCTYPE html>
<html>

<!-- Mirrored from webarch.revox.io/3.0/html/tables.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 29 Jan 2019 22:58:56 GMT -->
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <meta charset="utf-8" />
    <title></title>
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
    <a href="add_products.php"class="btn-link pull-right">New Product</a>


    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
        <thead>
        <tr>

            <th>Action</th>
            <th>Name</th>
            <th>Price KES</th>
            <th>Stock</th>
            <th>Image</th>
        </tr>
        </thead>
        <tbody>
        <?php
        $select="SELECT * FROM stock";
        $query=mysqli_query($con,$select);
        while($row=mysqli_fetch_array($query)){
            ?>
            <tr class="odd gradeX">
                <td>
                    <a href="edit_products.php?get=<?php echo $row['stock_id']?>"class="btn-link btn-xs btn-info">Edit</a>  </td>
                <td><?php echo $row['product_name']?> </td>
                <td ><?php echo number_format($row['price'])?> Ksh</td>
                <td>
                    <?php echo number_format($row['stock'])?></td>
                <td><img class="img-circle" src="upload_products/<?php echo $row['image']?>"style="width: 100px;height: 100px"></td>
            </tr>
            <?php

        }
        ?>

        </tbody>
    </table>

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
                            columns: [1,2,3,4]
                        }
                    },

                ]

            }
        );

    });
</script>
</html>