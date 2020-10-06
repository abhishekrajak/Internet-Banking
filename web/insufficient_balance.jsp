<%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 24-03-2020
  Time: 01:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

<title>Redirect</title>
<link rel="stylesheet" href="style.css">
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
<body onload="start()" style=" text-align: center;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;">

<div style="width: 100%;height: fit-content;display: flex;    justify-content: center;
">
    <img src="error_image.png" width="50px">
</div>


Insufficient balance <br>
You will be redirected to sbi login page after <span id = "count"></span>&nbsp;seconds...<br>
Click <a href="<%=com.sbi.base_url.url%>/sbi.jsp">here </a> to manually redirect.
</body>
</html>
