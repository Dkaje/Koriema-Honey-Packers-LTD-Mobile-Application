<?php
session_start();
error_reporting(E_ERROR);
include('include/connections.php');


if(isset($_POST['updateBtn'])){
    $hID=$_POST['hID'];
    $firstname=$_POST['firstname'];
    $lastname=$_POST['lastname'];
    $username=$_POST['username'];
    $phoneno=$_POST['phoneno'];
    $email=$_POST['email'];
    $role=$_POST['role'];
    $password=$_POST['password'];


    if(empty($firstname)|| empty($lastname)|| empty($username)|| empty($phoneno)|| empty($email)||  empty($password)){
        $swal="error";
        $fb="Please enter all the details";
    }else{
        // check if user name exist
        $select="SELECT * FROM employees WHERE username='$username' AND NOT emp_id='$hID'";
        $query=mysqli_query($con,$select);
        if($check=mysqli_num_rows($query)>0){
            $swal="error";
            $fb="Username is in use";
        }else{

            // check if phone no exist
            $select="SELECT * FROM employees WHERE contact='$phoneno' AND NOT emp_id='$hID'";
            $query=mysqli_query($con,$select);
            if($check=mysqli_num_rows($query)>0){
                $swal="error";
                $fb="Phone number is in use";
            }else{
                // check if email exist
                $select="SELECT * FROM employees WHERE email='$email' AND NOT emp_id='$hID'";
                $query=mysqli_query($con,$select);
                if($check=mysqli_num_rows($query)>0){
                    $swal="error";
                    $fb="Email is in use";
                }else{




                    $update="UPDATE employees SET f_name='$firstname',l_name='$lastname',username='$username',
                     contact='$phoneno',email='$email',password='$password',userlevel='$role' WHERE emp_id='$hID'";
                    if(mysqli_query($con,$update)){
                        $swal='success';
                        $fb='Updated successfully';
                    }else{
                        $swal='error';
                        $fb='Failed to add please try again';
                    }
                }
            }
        }
    }
}
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
<h3><span class="semi-bold">Employees</span></h3>
</div>
<div class="row">
<div class="col-md-12">
<div class="grid simple ">
<div class="grid-title no-border">

</div>
<div class="grid-body no-border">
    <a href="add_staff.php"class="btn-link btn-xs btn pull-right">New employee</a>

    <?php
    if(!empty($fb)){
        echo '<div class="alert alert-info text-center">'. $fb.'</div>';
    }

    ?>
    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
        <thead>
        <tr>
            <th>Edit</th>
            <th>#</th>
            <th>Name</th>
            <th>phone number</th>
            <th>Username</th>
            <th>Email</th>
            <th>Userlevel</th>
            <th>Status</th>
            <th>Date added</th>
        </tr>
        </thead>
        <tbody>
        <?php
        $select="SELECT * FROM employees WHERE NOT userlevel='Admin'";
        $query=mysqli_query($con,$select);
        while($row=mysqli_fetch_array($query)){
            ?>
            <tr class="odd gradeX">
                <td><a class="btn-link" href="#" data-toggle="modal" data-target="#update<?php echo $row['emp_id']?>">Edit</a> </td>
                <td><?php echo $row['emp_id']?> </td>
                <td><?php echo $row['f_name']. ' '. $row['l_name']?> </td>
                <td><?php echo $row['contact']?> </td>
                <td><?php echo $row['username']?> </td>
                <td><?php echo $row['email']?> </td>
                <td><?php echo $row['userlevel']?> </td>
                <td><?php echo $row['status']?> </td>
                <td><?php echo $row['date_added']?> </td>
            </tr>
            <?php
            ?>
            <!--     ======================================   UPDATE USER ======================================-->

            <div class="modal fade" id="update<?php echo $row['emp_id']?>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Edit</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form method="post" autocomplete="off">
                                <label>First name</label>
                                <input type="hidden"name="hID"value="<?php echo $row['emp_id']?>" class="form-control">
                                <input type="text"name="firstname"value="<?php echo $row['f_name']?>" class="form-control">
                                <label>Last name</label>
                                <input type="text"name="lastname"value="<?php echo $row['l_name'];?>" class="form-control">
                                <label>Userlevel</label>
                                <select class="form-control"name="role">
                                    <option><?php echo $row['userlevel']?></option>
                                    <option>Finance</option>
                                    <option>Shipping manager</option>
                                    <option>Store manager</option>
                                    <option>Driver</option>
                                </select>
                                <label>Username</label>
                                <input type="text"name="username"value="<?php echo $row['username'];?>" class="form-control">
                                <label>Phone no</label>
                                <input type="text"name="phoneno"value="<?php echo $row['contact'];?>" class="form-control">
                                <label>Email</label>
                                <input type="text"name="email"value="<?php echo $row['email'];?>" class="form-control">
                                <label>Password</label>
                                <input type="password"name="password"value="<?php echo $row['password'];?>" class="form-control">

                                <br>

                                <input type="submit" name="updateBtn"value="Edit"class="btn btn-primary ">

                            </form>

                        </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-default"data-dismiss="modal">X</button>
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
                            columns: [0,1,2,3,4,5,6]
                        }
                    },

                ]

            }
        );

    });
</script>
</html>