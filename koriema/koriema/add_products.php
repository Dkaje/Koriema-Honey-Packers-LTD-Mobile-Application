<?php
session_start();
error_reporting(E_ERROR);
include('include/connections.php');
ob_start();

if(empty($_SESSION['IDstaff'])){
    header('location:index.php');
}


// add a new product

if(isset($_POST['add_Btn'])){

    $upload_dir='upload_products/';// image directory



    $imgName = $_FILES['itemImage']['name'];
    $product=$_POST['product'];
    $price=$_POST['price'];
    $desc=$_POST['desc'];

    if(empty($product)||empty($imgName)||empty($price)){
        $swal='error';
        $fb="Please enter all the details";
    }elseif(!is_numeric($price)){
        $swal='error';
        $fb='Price should have numeric characters';
    }else{
        //image update

        $imgName = $_FILES['itemImage']['name'];
        $imgTmp = $_FILES['itemImage']['tmp_name'];
        $imgSize = $_FILES['itemImage']['size'];

        if($imgName){
            //get image extension
            $imgExt = strtolower(pathinfo($imgName, PATHINFO_EXTENSION));
            //allow extenstion
            $allowExt  = array('jpeg', 'jpg', 'png', 'gif');
            //random new name for photo
            $image = time().'_'.rand(1000,9999).'.'.$imgExt;
            //check a valid image
            if(in_array($imgExt, $allowExt)){

            }else{

                $swal='error';
                $fb="please select a valid Image file";


            }
        }

        if(!$set=='error'){

            // Resize uploaded images size

            function resizeImage($resourceType,$image_width,$image_height) {
                $resizeWidth = 300;
                $resizeHeight = 300;
                $imageLayer = imagecreatetruecolor($resizeWidth,$resizeHeight);
                imagecopyresampled($imageLayer,$resourceType,0,0,0,0,$resizeWidth,$resizeHeight, $image_width,$image_height);
                return $imageLayer;
            }


            if(is_array($_FILES)) {


                $sourceProperties = getimagesize($imgTmp);
                $image = time().'_'.rand(1000,9999).'.'.$imgExt; // change image name

                //$select="SELECT * FROM category WHERE category_name='$category'";
                //$query=mysqli_query($con,$select);
                //$row=mysqli_fetch_array($query);
                //$catID=$row['category_id'];

                $insert= "INSERT INTO stock(product_name, price, image,stock,description) 
                 VALUES ( '$product', '$price', '$image','0','$desc')" ;

                if(mysqli_query($con,$insert)){
                    //  move_uploaded_file($imgTmp ,$upload_dir.$image);


                    $uploadImageType = $sourceProperties[2];
                    $sourceImageWidth = $sourceProperties[0];
                    $sourceImageHeight = $sourceProperties[1];


                    switch ($uploadImageType) {

                        case IMAGETYPE_JPEG:
                            $resourceType = imagecreatefromjpeg($imgTmp);
                            $imageLayer = resizeImage($resourceType,$sourceImageWidth,$sourceImageHeight);
                            imagejpeg($imageLayer,$upload_dir.$image);
                            break;

                        case IMAGETYPE_GIF:
                            $resourceType = imagecreatefromgif($imgTmp);
                            $imageLayer = resizeImage($resourceType,$sourceImageWidth,$sourceImageHeight);
                            imagegif($imageLayer,$upload_dir.$image);
                            break;

                        case IMAGETYPE_PNG:
                            $resourceType = imagecreatefrompng($imgTmp);
                            $imageLayer = resizeImage($resourceType,$sourceImageWidth,$sourceImageHeight);
                            imagepng($imageLayer,$upload_dir.$image);
                            break;

                        default:

                            break;
                    }
                }

                $swal='success';
                $fb=" Submitted successfully";

                $product='';
                $price='';
                $quantity='';
                $desc='';

            }else{


                $swal='error';
                $fb='Failed. please try again';
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


    <div class="row">
        <div class="col-md-12">
            <?php
            if(!empty($fb)){
                echo '<div class="alert alert-info">'. $fb.'</div>';
            }

            ?>
        </div>
        <div class="col-md-offset-1 col-md-4">

            <form method="post"autocomplete="off" enctype="multipart/form-data">

                <label>Name</label>
                <input type="text"name="product"value="<?php echo $product?>" class="form-control">

                <label>Cost KES</label>
                <input type="text"name="price"value="<?php echo $price?>" class="form-control">

        </div>
        <div class="col-md-4">
            <label>Image</label>
            <input type="file"name="itemImage"value="<?php echo $image;?>" class="form-control" required>
            <label>Product description</label>
            <textarea class="form-control"name="desc"><?php echo $desc?></textarea>
            <br>

            <input type="submit" name="add_Btn"value="Submit"class="btn btn-primary btn-sm">

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