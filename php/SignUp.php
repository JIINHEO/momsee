<?php
 $con = mysqli_connect("localhost","k2h0508","rudgns0508","k2h0508");
 $userEmail = $_POST["userEmail"];
 $userPassword = $_POST["userPassword"];
 $userName = $_POST["userName"];
 
 $statement = mysqli_prepare($con, "INSERT INTO USER VALUES(?, ?, ?)");
 mysqli_stmt_bind_param($statement, "sss", $userEmail, $userPassword, $userName);
 mysqli_stmt_execute($statement);
 
 $response = array();
 $response["success"] = true;
  
 echo json_encode($response);

?>