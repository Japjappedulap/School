<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 29.06.2018
 * Time: 02:56
 */

include('../Login/session.php');
include("../Database/function.php");


if (isset($_GET['friend'])) {
    removeFriend($login_session, $_GET['friend']);
}