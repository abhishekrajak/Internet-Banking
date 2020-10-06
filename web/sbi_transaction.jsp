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
<body>
    <%
        if(session.getAttribute("sbi_dao")==null){
            response.sendRedirect("sbi_not_logged_in.jsp");
        }else{
    %>
    Local transaction click <a href="sbi_local_transaction.jsp">here</a><br>
    Global transaction click <a href="sbi_global_transaction.jsp">here</a>
    <%
        }
    %>

</body>
</html>
