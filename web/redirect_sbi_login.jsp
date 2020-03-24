<%@ page import="com.sbi.base_url" %><%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 22-03-2020
  Time: 06:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Redirect</title>
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
</head>
<body onload="start()">
Invalid credentials <br>
You will be redirected to sbi login page after <span id = "count"></span>&nbsp;seconds...<br>
Click <a href="<%=base_url.url%>/sbi.jsp">here </a> to manually redirect.
</body>
</html>
