<%@ page import="com.sbi.SBI_DAO" %>
<%@ page import="com.sbi.base_url" %><%--
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
<script>
    var reqURL = "SBI_Update.jsp";
    function ajaxAsyncRequest() {
        var xmlhttp;
        if (window.XMLHttpRequest){
            xmlhttp = new XMLHttpRequest();
        } else {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.open("GET", reqURL, true);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4) {
                if (xmlhttp.status == 200)
                {
                    document.getElementById("message").innerHTML = xmlhttp.responseText;
                    console.log("success")
                }
                else {
                    alert('Something is wrong !!');
                }
            }
        };

        xmlhttp.send(null);
    }

</script>
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
    Account No. : <%=String.format("%05d", sbi_dao.getAccount_number())%><br>
    Balance : <span id="message"><%=sbi_dao.getBalance()%></span><br>
    Update balance click <button type="button" id="update" onclick="ajaxAsyncRequest()">here</button><br>
    To make transaction click <a href="sbi_transaction.jsp">here</a><br>
    <form action="<%=base_url.url%>/sbi_logout" method="post">
        <input type="submit" name="logout" value="logout"/>
    </form>
    <%
        }
    %>
</body>
</html>
