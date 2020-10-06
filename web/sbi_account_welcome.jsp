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
<style>
    .form_layout{
        display: flex;
        flex-direction: column;
        height: 100%;
        width: 100%;
        justify-content: center;
        align-items: center;
        background-color: powderblue;
    }
    .inner_container{
        background-color: white;
        padding: 3%;
        border-radius: 10px;
        font-size: 20px;
    }
    .button {
        background-color: #4CAF50;
        border: none;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
    }
</style>

<body style=" text-align: center;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;">

<div class="form_layout">
    <div class="inner_container">
            <%
                response.setHeader("Cache-Control","no-cache");
                response.setHeader("Pragma","no-cache");
                response.setDateHeader ("Expires", -1);
            %>
            <div style="text-align: center;
    font-size: 30px;
    color: green;
    margin-bottom: 20px;
    margin-left: 20px;">Welcome to User Page</div>


            <%
                if(session.getAttribute("sbi_dao")==null){
                    response.sendRedirect("sbi_not_logged_in.jsp");
                }else{
                SBI_DAO sbi_dao = (SBI_DAO)session.getAttribute("sbi_dao");
            %>

            Username :  <%=sbi_dao.getUsername()%><br>
            Name : <%=sbi_dao.getName()%><br>
            Account No. : <%=String.format("%05d", (int)sbi_dao.getAccount_number())%><br>
            Balance : <span id="message"><%=sbi_dao.getBalance()%></span><br>
            Update balance click <button class="button" style="    padding: 5px 10px; border-radius: 4px;" type="button" id="update" onclick="ajaxAsyncRequest()">here</button><br>
        create transaction click <p class="button"   style="    padding: 5px 10px; border-radius: 4px;text-decoration: none"><a style="text-decoration: none;
    color: white;" href="sbi_transaction.jsp"  >here</a></p><br>
        <%--    Inter-bank transaction click <a href="sbi_global_transaction.jsp">here </a><br>--%>
            <form action="<%=base_url.url%>/display" method="post">
                <input class="button" style="    padding: 5px 10px; border-radius: 4px;" type="submit" name="transactions" value="List transaction"> <br>
            </form>
            <form action="<%=base_url.url%>/sbi_logout" method="post">
                <input class="button" style="width: 100%;
    border-radius: 4px;" type="submit" name="logout" value="logout"/>
            </form>
            <%
                }
            %>
    </div>
</div>
</body>
</html>
