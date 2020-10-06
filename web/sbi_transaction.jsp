<%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 24-03-2020
  Time: 12:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sbi_transaction</title>
</head>

<style>
    .button {
        background-color: #4CAF50;
        border: none;
        color: white;
        padding: 10px 25px;
        border-radius: 4px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 20px;
        margin: 4px 2px;
        cursor: pointer;
    }
</style>
<link rel="stylesheet" href="style.css">
<body  style=" text-align: center;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;">
<div class="inner_container">
    <%
        if(session.getAttribute("sbi_dao")==null){
            response.sendRedirect("sbi_not_logged_in.jsp");
        }else{
    %>
    Local transaction click <a style="text-decoration: none;
    color: white;" class="button" href="sbi_local_transaction.jsp">here</a><br>
    Global transaction click <a style="text-decoration: none;
    color: white;" class="button" href="sbi_global_transaction.jsp">here</a>
    <%
        }
    %>
</div>
</body>
</html>
