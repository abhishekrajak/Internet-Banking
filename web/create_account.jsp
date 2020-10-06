<%@ page import="com.sbi.base_url" %><%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 09-04-2020
  Time: 02:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
</head>
<style>
    input[type=number], select {
        width: 40%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }
    input[type=text], select {
        width: 40%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }
    input[type=date], select {
        width: 40%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }
    input[type=password], select {
        width: 40%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    input[type=submit] {
        width: 40%;
        background-color: #4CAF50;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    input[type=submit]:hover {
        background-color: #45a049;
    }

    div {
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;
    }
</style>
<body style=" text-align: center;

display: flex;
flex-direction: column;
justify-content: center;">
<div class="inner_container">

<h1 style="text-align: center; color: blue; ">Create Your Account</h1>

<form action = <%=base_url.url%>/sbi_create_account method = "post" >
    First Name    : <input type = "text" name = "fname" required><br><br>
    Middle Name   : <input type = "text" name = "mname" ><br><br>
    Last Name     : <input type = "text" name = "lname" required><br><br>
    Gender        : <input type = "radio" id = "male" name = "gender" value = "male" >
    <label for = "male" >Male    </label>
    <input type = "radio" id = "female" name = "gender" value = "female">
    <label for = "female">Female   </label>
    <input type = "radio" id = "other" name = "gender" value = "other">
    <label for = "Other">Other</label><br><br>
    Phone no.     : <input type = "text" name = "phno" required><br><br>
    Address       : <input type = "text" name = "address" required><br><br>
    email id      : <input type = "text" name = "email" required><br><br>
    Date of Birth : <input type = "date" name = "dob" required><br><br>
<%--    account no.   : <input type = "number" name = "acno" required><br><br>--%>
    Enter Starting
    balance       : <input type = "number" name = "bal" required><br><br>
    Enter password: <input type = "password" name = "password" required minlength="4"><br><br>
    <input type = "submit" value = "Create Account">
</form>

    </div>
</body>
</html>
