<%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 19-04-2020
  Time: 11:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sbi_global_transaction</title>
</head>
<script>
    var destination_username, destination_balance, transfer;
    function initialise() {
        destination_username = document.getElementsByName("destination")[0];
        destination_balance = document.getElementsByName("balance")[0];
        transfer = document.getElementsByName("transfer")[0];
        setInterval(check, 100);
    }

    function check(){
        if(destination_username.value == "" || destination_balance.value == ""){
            transfer.disabled = true;
        } else{
            transfer.disabled = false;
        }
    }


</script>

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
<body onload="initialise()"  style=" text-align: center;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;">
    <div class="inner_container">
        <%
            if(session.getAttribute("sbi_dao")==null) {
                response.sendRedirect("sbi_not_logged_in.jsp");
            }else {
        %>
        <form action="<%=com.sbi.base_url.url%>/sbi_global_transaction" method="post">
            Choose a bank:<select id="bank" name="bank">
                                <option value="idbi">IDBI</option>
                          </select><br>
            destination_account <input name="destination" type="number"><br>
            balance to be transferred <input name="balance" type="number"><br>
            <input name="transfer" type="submit">
        </form>
        <%
            }
        %>
</div>
</body>
</html>
