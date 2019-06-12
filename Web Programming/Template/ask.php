<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 29.06.2018
 * Time: 10:46
 */

include('Login/session.php');
include("Database/function.php");

if (isset($_GET['name'])) {
    $name = $_GET['name'];
    if (ObjectSaved($name)) {
        AddAccess($name, $login_session);
        echo 'OK';
    } else {
        AddObject($name);
        AddAccess($name, $login_session);
        echo 'OK - added';
    }
}