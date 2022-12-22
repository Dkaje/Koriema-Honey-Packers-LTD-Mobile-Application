<?php
session_start();
error_reporting(E_ERROR);
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


if(isset($_POST['updateBtn'])){
    $hID=$_POST['hID'];
    $county=$_POST['county'];


    if(empty($county)){
        $swal="error";
        $fb="Please enter county name";
    }else {
        // check if county exist
        $select = "SELECT * FROM counties WHERE county_name='$county'AND NOT county_id='$hID'";
        $query = mysqli_query($con, $select);
        if ($check = mysqli_num_rows($query) > 0) {
            $swal = "error";
            $fb = "County name exist";
        } else {
            $update="UPDATE counties SET county_name='$county'WHERE county_id='$hID'";
            if (mysqli_query($con, $update)) {
                $swal = 'success';
                $fb = 'Updated successfully';
            } else {
                $swal = 'error';
                $fb = 'Failed to add please try again';
            }
        }
    }
}
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
<h3><span class="semi-bold">Counties</span></h3>
</div>
<div class="row">
<div class="col-md-12">
<div class="grid simple ">
<div class="grid-title no-border">

</div>
<div class="grid-body no-border">
    <a href="add_county.php"class="btn-danger btn-xs btn">Add county</a>

    <?php
    if(!empty($fb)){
        echo '<div class="alert alert-info text-center">'. $fb.'</div>';
    }

    ?>

    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
        <thead>
        <tr>
            <th>County ID</th>
            <th>County name</th>
            <th>Edit</th>
        </tr>
        </thead>
        <tbody>
        <?php
        $select="SELECT * FROM counties";
        $query=mysqli_query($con,$select);
        while($row=mysqli_fetch_array($query)){
            ?>
            <tr class="odd gradeX">

                <td><?php echo $row['county_id']?> </td>
                <td><?php echo $row['county_name']?> </td>

                <td><a href="#" data-toggle="modal" data-target="#update<?php echo $row['county_id']?>">Update</a> </td>
            </tr>
            <?php
            ?>
            <!--     ======================================   UPDATE USER ======================================-->

            <div class="modal fade" id="update<?php echo $row['county_id']?>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Update <?php echo $row['county_name']?></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form method="post" autocomplete="off">
                                <label>County name</label>
                                <input type="hidden"name="hID"value="<?php echo $row['county_id']?>" class="form-control">
                                <input type="text"name="county"value="<?php echo $row['county_name']?>" class="form-control">

                                <br>

                                <input type="submit" name="updateBtn"value="Update"class="btn btn-info form-control">

                            </form>

                        </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-danger"data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

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
                        text:'<i class="fa fa-print btn-xs btn-danger"> Print </i>',
                        footer: true,
                        exportOptions: {
                            columns: [0,1]
                        }
                    },

                ]

            }
        );

    });
</script>
</html>