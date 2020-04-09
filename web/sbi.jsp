<%@ page import="com.sbi.base_url" %><%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 22-03-2020
  Time: 04:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SBI</title>
    <script>
        var username, password, Submit;
        function initialise() {
            username = document.getElementById("username");
            password = document.getElementById("password");
            Submit = document.getElementById("Submit");
            setInterval(check, 100);
        }

        function check(){
             if(username.value == "" || password.value == ""){
                 Submit.disabled = true;
             } else{
                 Submit.disabled = false;
             }
        }
    </script>
</head>

<body onload="initialise()">
    <div style="text-align: center">Welcome to SBI</div>
    <%
        String url = com.sbi.base_url.url;
        String action = url + "/sbi_login";
        if(session.getAttribute("sbi_dao") != null){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("sbi_account_welcome.jsp");
            requestDispatcher.forward(request, response);
        }else{

    %>
    <form action="<%= action %>" method="post">
        Username <input id="username" name="username" type="text"><br><br>
        Password <input id="password" name="password" type="password"><br>
        <input id="Submit" name="Submit" type="submit">
    </form>
    <a href="create_account.jsp">Create Account</a>
    <%
        }
    %>
</body>
</html>