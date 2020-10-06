<%@ page import="java.util.LinkedList" %>
<%@ page import="javax.sound.sampled.Line" %>
<%@ page import="com.sbi.SBI_DAO" %><%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 16-04-2020
  Time: 10:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transaction</title>
</head>
<style>
    #transactions_table {
        font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    #transactions_table td, #transactions_table th {
        border: 1px solid #ddd;
        padding: 8px;
    }

    #transactions_table tr:nth-child(even){background-color: #f2f2f2;}

    #transactions_table tr:hover {background-color: #ddd;}

    #transactions_table th {
        padding-top: 12px;
        padding-bottom: 12px;
        text-align: left;
        background-color: #4CAF50;
        color: white;
    }
</style>
<body>
<%
    LinkedList<String> list = (LinkedList)request.getSession().getAttribute("transaction");
%>

<div style="width: 100%;margin-top: 30px;margin-bottom: 20px;display: flex;" >
username : <%
                SBI_DAO sbi_dao = (SBI_DAO)session.getAttribute("sbi_dao");
                out.print(sbi_dao.getUsername());
            %><br>

</div>
<table  id="transactions_table" border=1 width=75% height=10%>
    <tr><th>Transaction_id</th><th>Debit_Bank_Id</th>
        <th>Debit_Account</th><th>Credit_Bank_Id</th><th>Credit_Account</th>
        <th>Amount</th><th>Time</th><th>Previous Amount</th><th>Current Amount</th></tr>
    <%
        int n = list.size();
        for(int i=0; i < n; i++){
            out.println(list.get(i));
        }

    %>
</table>

<span style="width: 100%;text-align: center"><p style="
    background: #4CAF50;
    width: fit-content;margin: 10px 25px;">
<a href="sbi_account_welcome.jsp" style="margin: 30px 50px;
    height: 30px;color: white;text-decoration: none;">back</a>
</p></span>
</body>
</html>
