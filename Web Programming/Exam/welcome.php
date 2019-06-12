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


<table id="1stLevelFriends" class="display">
    <thead>
    <tr>
        <th>Direct friends</th>
    </tr>
    </thead>
</table>

<table id="2ndLevelFriends" class="display">
    <thead>
    <tr>
        <th>Friends of friends</th>
    </tr>
    </thead>
</table>

<form>
    <label>
        User:
        <input type="text" id="textBox">
    </label>
    <button type="button" id="makeButton">Make Friend</button>
    <button type="button" id="removeButton">Remove friend</button>
</form>


<h2><a href="Login/logout.php">Sign Out</a></h2>


</body>

</html>