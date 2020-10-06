<%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 09-04-2020
  Time: 03:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Creation Successful</title>
</head>
<script type="application/javascript">
    var x;
    var count = 5;
    function start() {
        setInterval(counter, 1000);
        x = document.getElementById("count");
        x.innerText = count.toString(10);
    }
    function counter() {
        count--;
        x.innerText = count.toString(10);
        if(count === 0){
            window.location.href = "sbi.jsp";
        }
    }
</script>

<body onload="start()">
Account Created Successfully....<br>
<%
    String username = (String)session.getAttribute("username");
    String accountNo = (String)session.getAttribute("accountNo");
    session.removeAttribute("username");
    session.removeAttribute("accountNo");
%>
    username : <%=username%><br>
    account no. : <%=accountNo%><br>
You will be redirected to sbi login page after <span id = "count"></span>&nbsp;seconds...<br>
</body>
</html>
