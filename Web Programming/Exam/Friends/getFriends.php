<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 29.06.2018
 * Time: 01:24
 */
include('../Login/session.php');
include("../Database/function.php");


if (isset($_GET['level'])) {
    if ($_GET['level'] == 1) {
        echo json_encode(get1stLevelFriends($login_session));
    }
    if ($_GET['level'] == 2) {
        echo json_encode(get2ndLevelFriends($login_session));
    }
}