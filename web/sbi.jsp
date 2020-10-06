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
        var username, password, captcha, Submit;
        function initialise() {
            username = document.getElementById("username");
            password = document.getElementById("password");
            Submit = document.getElementById("Submit");
            captcha = document.getElementById("captcha");
            setInterval(check, 100);
        }

        function check(){
             if(username.value == "" || password.value == "" || captcha.value == ""){
                 Submit.disabled = true;
             } else{
                 Submit.disabled = false;
             }
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
      }


       input[type=number], select {
               width: 100%;
           padding: 12px 20px;
           margin: 8px 0;
           display: inline-block;
           border: 1px solid #ccc;
           border-radius: 4px;
           box-sizing: border-box;
       }
      input[type=text], select {
              width: 100%;
          padding: 12px 20px;
          margin: 8px 0;
          display: inline-block;
          border: 1px solid #ccc;
          border-radius: 4px;
          box-sizing: border-box;
      }
      input[type=date], select {
              width: 100%;
          padding: 12px 20px;
          margin: 8px 0;
          display: inline-block;
          border: 1px solid #ccc;
          border-radius: 4px;
          box-sizing: border-box;
      }
      input[type=password], select {
              width: 100%;
          padding: 12px 20px;
          margin: 8px 0;
          display: inline-block;
          border: 1px solid #ccc;
          border-radius: 4px;
          box-sizing: border-box;
      }

      input[type=submit] {
              width: 100%;
          background-color: #4CAF50;
          color: white;
          padding: 14px 20px;
          margin: 8px 0;
          border: none;
          border-radius: 4px;
          cursor: pointer;
      }

      input[type=submit]:hover {
          background-color: #45a049;
      }

      div {
          border-radius: 5px;
          
          padding: 20px;
      }
    </style>

</head>

<body onload="initialise()">

    <div class="form_layout">
            <div class="inner_container">
            <div style="text-align: center">Welcome to SBI</div>
            <%
                String url = com.sbi.base_url.url;
                String action = url + "/sbi_login";
                if(session.getAttribute("sbi_dao") != null){
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("sbi_account_welcome.jsp");
                    requestDispatcher.forward(request, response);
                }else{

            %>
            <div class="login-page">
                <form class="form" action="<%= action %>" method="post">
                    Username <input id="username" name="username" type="text"><br><br>
                    Password <input id="password" name="password" type="password"><br><br>
                    <img src="http://localhost:8081/Internet-Banking/captcha"> <br><br>
                    Enter Captcha <input id="captcha" name="captcha" type="text"><br><br>
                    <input id="Submit" name="Submit" type="submit"><br><br>
                </form>
            </div>


            <a style="text-decoration: none;color: #4CAF50;margin-top: -20px" href="create_account.jsp">Create Account</a>

                    <%
                }
            %>
            </div>
        </div>
</body>
</html>