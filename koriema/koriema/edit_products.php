<?php
session_start();
error_reporting(E_ERROR);
include('include/connections.php');
ob_start();

if(empty($_SESSION['IDstaff'])){
    header('location:index.php');
}

if(isset($_POST['submitBtn'])){

    $price=$_POST['price'];
    $name=$_POST['product'];
    $desc=$_POST['desc'];
    $hID=$_POST['hID'];

    if(empty($price)||empty($name)||empty($desc)){
        $swal='error';
        $fb='No empty fields allowed';
    }elseif(!is_numeric($price)){
        $swal='error';
        $fb='Price should contain numbers';
    }else{


        $update= "UPDATE stock SET product_name='$name', price='$price',description='$desc' WHERE stock_id='$hID'";
        if(mysqli_query($con,$update)){
            $fb=" Updated successfully";
        }else{
            $swal='error';
            $fb='Failed to update stock';
        }
    }
}


// Change image

if(isset($_POST['imgBtn'])){
    $image=$_FILES['itemImage']['name'];
    $hID=$_POST['hID'];
    $oldImage=$_POST['oldImage'];

    $upload_dir='upload_products/';

    if(empty($image)){
        $swal='error';
        $fb='Please select an image';
    }
    else{
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


                $update="UPDATE  stock SET image='$image' WHERE  stock_id='$hID'";

                if(mysqli_query($con,$update)){
                    //  move_uploaded_file($imgTmp ,$upload_dir.$image);
                    unlink($upload_dir.$oldImage);// remove the old image
                    move_uploaded_file($fileTemp ,$upload_dir.$newName);

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


                $fb=" Updated successfully";

                $product='';


            }else{


                $set='error';
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
    <a href="products.php"class="btn-danger btn-xs btn">Back</a>

    <div class="row">
        <?php
        if(!empty($fb)){
            echo '<div class=alert alert-info>'. $fb.'</div>';
        }

        ?>
        <div class="col-md-offset-1 col-md-4">
            <?php
            $select="SELECT * FROM stock WHERE  stock_id=".$_GET['get'];
            $query=mysqli_query($con,$select);
            $row=mysqli_fetch_array($query);
            ?>
            <form method="post"autocomplete="off" enctype="multipart/form-data">

                <label>Product name</label>
                <input type="hidden"name="hID"value="<?php echo $row['stock_id']?>" class="form-control">
                <input type="text"name="product"value="<?php echo $row['product_name']?>" class="form-control">

                <label>Price</label>
                <input type="text"name="price"value="<?php echo $row['price']?>" class="form-control">
                <label>Product description</label>
                <textarea class="form-control"name="desc"><?php echo $row['description']?></textarea>

                <br>

                <input type="submit" name="submitBtn"value="Update"class="btn btn-info btn-sm">

            </form>

        </div>
        <div class="col-md-offset-1 col-md-4">
            <label>Update image</label>
            <form method="post"autocomplete="off" enctype="multipart/form-data">
                <img src="upload_products/<?php echo $row['image']?>"style="width: 100px;height: 100px">
                <hr>
                <input type="hidden"name="oldImage"value="<?php echo $row['image']?>">
                <input type="hidden"name="hID"value="<?php echo $row['stock_id']?>">
                <label>Image</label>
                <input type="file"name="itemImage"value="<?php echo $image;?>" class="form-control" required>
                <br>

                <input type="submit" name="imgBtn"value="Update image"class="btn btn-info btn-sm">

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