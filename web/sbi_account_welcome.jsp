<%@ page import="com.sbi.SBI_DAO" %><%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 22-03-2020
  Time: 10:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sbi_account</title>
</head>
<body>
    <div style="text-align: center">Welcome to User Page</div>
    <%
        if(session.getAttribute("sbi_dao")==null){
            response.sendRedirect("sbi_not_logged_in.jsp");
        }else{
        SBI_DAO sbi_dao = (SBI_DAO)session.getAttribute("sbi_dao");
    %>
    Username :  <%=sbi_dao.getUsername()%><br>
    Name : <%=sbi_dao.getName()%><br>
    Account No. : <%=sbi_dao.getAccount_number()%><br>
    Balance : <%=sbi_dao.getBalance()%><br>
    To make transaction click <a href="sbi_transaction.jsp">here</a>
    <%
        }
    %>
</body>
</html>
