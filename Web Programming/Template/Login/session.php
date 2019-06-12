<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 26.06.2018
 * Time: 22:59
 */
session_start();

if (!isset($_SESSION['login_user'])) {
    header("location:/Exam/Login/login.php");
}
$login_session = $_SESSION['login_user'];


