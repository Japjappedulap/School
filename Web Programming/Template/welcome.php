<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 26.06.2018
 * Time: 23:01
 */
include('Login/session.php');
?>
<html>

<head>
    <title>Welcome </title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link href="https://fonts.googleapis.com/css?family=Lato|PT+Sans" rel="stylesheet">
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf8" src="script.js"></script>
</head>

<body>
<h1>Welcome <?php echo $login_session; ?></h1>

<form>
    <label for="textBox">Object</label><input type="text" name="name" id="textBox">
    <input type="button" onclick="request()" value="Ask for object">
</form>
<button type="button" onclick="onPopular()">Popular</button>
<span id="info"></span>
<br>
<br>

<label>Most popular object has ID = <label id="popularIDGoesHere">__</label> | name =
    <label id="popularNameGoesHere">__</label> | COUNT = <label id="popularCountGoesHere">__</label> </label>

    <h2><a href="Login/logout.php">Sign Out</a></h2>


</body>

</html>