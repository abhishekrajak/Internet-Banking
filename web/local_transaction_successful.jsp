<%@ page import="com.sbi.base_url" %>
<%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 26-03-2020
  Time: 09:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Local Transaction Successful</title>
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
<link rel="stylesheet" href="style.css">
<body onload="start()" style=" text-align: center;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;">

<%--<div style="width: 100%;height: fit-content;display: flex;    justify-content: center;--%>
<%--">--%>
<%--    <img src="error_image.png" width="50px">--%>
<%--</div>--%>


Local Transaction Successul <br>
You will be redirected to user account page after <span id = "count"></span>&nbsp;seconds...<br>
Click <a href="<%=base_url.url%>/sbi_account_welcome.jsp">here </a> to manually redirect.
</body>
</html>
