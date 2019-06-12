<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 26.06.2018
 * Time: 23:02
 */

session_start();

if (session_destroy()) {
    header("Location: login.php");
}
