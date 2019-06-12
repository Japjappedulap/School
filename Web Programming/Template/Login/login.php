<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 26.06.2018
 * Time: 21:50
 */

session_start();

if ($_SERVER['REQUEST_METHOD'] === "POST") {
    $_SESSION['login_user'] = $_POST['ID'];
    header("location: ../welcome.php");
}
?>
<html>

<head>
    <title>Login Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="../styles.css">
    <link href="https://fonts.googleapis.com/css?family=Lato|PT+Sans" rel="stylesheet">
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
</head>

<body bgcolor="#FFFFFF">

<div align="center">
    <div style="width:300px; border: solid 1px #333333; " align="left">
        <div style="background-color:#333333; color:#FFFFFF; padding:3px;"><b>Login</b></div>

        <div style="margin:30px">

            <form action="" method="post">
                <label>UserName :<input type="text" name="ID" class="box"/></label><br/><br/>
                <input type="submit" value=" Submit "/><br/>
            </form>

        </div>

    </div>

</div>

</body>
</html>