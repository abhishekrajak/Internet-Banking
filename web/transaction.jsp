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
<body>
<%
    LinkedList<String> list = (LinkedList)request.getSession().getAttribute("transaction");
%>
username : <%
                SBI_DAO sbi_dao = (SBI_DAO)session.getAttribute("sbi_dao");
                out.print(sbi_dao.getUsername());
            %><br>

<table border=1 width=50% height=50%>
    <tr><th>Transaction_id</th><th>Debit_Account</th><th>Credit Account</th><th>Amount</th><th>Time</th></tr>
    <%
        int n = list.size();
        for(int i=0; i < n; i++){
            out.println(list.get(i));
        }

    %>
</table>

<a href="sbi_account_welcome.jsp">back</a>

</body>
</html>
