<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 26.06.2018
 * Time: 21:52
 * @param $ID
 * @param $Password
 * @return bool
 */

include("config.php");

function ObjectSaved($name) {
    $sql = "SELECT * FROM Exam.CacheObject WHERE name = '$name'";
    $result = mysqli_query($GLOBALS['DB_CONNECTION'], $sql);
    $count = mysqli_num_rows($result);
    if($count == 1) {
        return true;
    }else {
        return false;
    }
}

function getObjectIDbyName($name) {
    $sql = "SELECT IDObject FROM Exam.CacheObject WHERE name = '$name'";
    $result = mysqli_query($GLOBALS['DB_CONNECTION'], $sql);
    $response = null;
    while ($row = $result->fetch_assoc()) {
        $response = $row['IDObject'];
    }
    return $response;
}

function AddAccess($name, $user) {
    $IDObject = getObjectIDbyName($name);
    $sql = "INSERT INTO Exam.AccessHistory (IDCacheObject, timeofAccess, user) VALUES ('$IDObject', SYSDATE(), '$user')";
    mysqli_query($GLOBALS['DB_CONNECTION'], $sql);

    $sql = "UPDATE CacheObject SET latestUpdate = SYSDATE() WHERE IDObject = '$IDObject'";
    mysqli_query($GLOBALS['DB_CONNECTION'], $sql);
}

function AddObject($name) {

    $conn1 = new mysqli($GLOBALS['DB_SERVER'], $GLOBALS['DB_USERNAME'], $GLOBALS['DB_PASSWORD'], $GLOBALS['DB_DATABASE']);
    if ($conn1->connect_error) die("Connection failed: " . $conn1->connect_error);
    $sql1 = "SELECT COUNT(*) AS 'COUNT' FROM CacheObject";
    $result1 = $conn1->query($sql1);
    $response1 = null;
    if ($result1->num_rows > 0)
        while($row = $result1->fetch_assoc())
            $response1 = $row['COUNT'];
    $conn1->close();
    echo "\n". "Count is = " .$response1 . "\n";


    if ($response1 >= 2) {
        $conn2 = new mysqli($GLOBALS['DB_SERVER'], $GLOBALS['DB_USERNAME'], $GLOBALS['DB_PASSWORD'], $GLOBALS['DB_DATABASE']);
        if ($conn2->connect_error) die("Connection failed: " . $conn2->connect_error);
        $sql2 = "CALL LeastRecentlyAccessed()";
        $result2 = $conn2->query($sql2);
        $response2 = null;
        if ($result2->num_rows > 0)
            while($row = $result2->fetch_assoc())
                $response2 = $row['IDObject'];
        $conn2->close();
        echo "\n" . "LRA result:".$response2 . "\n";


        $conn3 = new mysqli($GLOBALS['DB_SERVER'], $GLOBALS['DB_USERNAME'], $GLOBALS['DB_PASSWORD'], $GLOBALS['DB_DATABASE']);
        if ($conn3->connect_error) die("Connection failed: " . $conn3->connect_error);
        $sql3 = "DELETE FROM CacheObject WHERE IDObject = '$response2'";
        $conn3->query($sql3);
        $conn3->close();
    }
    $length = rand(1, 1000);


    $conn4 = new mysqli($GLOBALS['DB_SERVER'], $GLOBALS['DB_USERNAME'], $GLOBALS['DB_PASSWORD'], $GLOBALS['DB_DATABASE']);
    if ($conn4->connect_error) die("Connection failed: " . $conn3->connect_error);
    $sql4 = "INSERT INTO CacheObject (length, name, content, latestUpdate) VALUES ($length, '$name', NULL , SYSDATE())";
    $conn4->query($sql4);
    $conn4->close();
}

function Popular() {
    $sql = "CALL MostFrequentlyAccessed()";
    $result = mysqli_query($GLOBALS['DB_CONNECTION'], $sql) or die(mysqli_error($GLOBALS['DB_CONNECTION']));
    $response = null;
    while ($row = $result->fetch_assoc())
        $response = array("IDObject" => $row['IDObject'], "Count" => $row['Count'], "name" => $row['name']);
    echo json_encode($response);
}