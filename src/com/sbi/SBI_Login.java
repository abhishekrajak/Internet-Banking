package com.sbi;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "SBI_Login")
public class SBI_Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String captcha = request.getParameter("captcha");
        String captchaSol = (String)request.getSession().getAttribute("captchaSol");

        if(!captcha.equals(captchaSol)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("incorrectCaptcha.jsp");
            requestDispatcher.forward(request, response);
        }else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            SBI_DAO sbi_dao;
            try {
                sbi_dao = SBI_Transaction_Executor.generateData(username, password, false);
            } catch (Exception e) {
                response.getWriter().write(e.getMessage());
                response.sendRedirect("Database_Error.html");
                return;
            }

            HttpSession session = request.getSession();
            if (sbi_dao == null) {
                response.sendRedirect("redirect_sbi_login.jsp");
                session.setAttribute("sbi_dao", null);
            } else {
                session.setAttribute("sbi_dao", sbi_dao);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("sbi_account_welcome.jsp");
                requestDispatcher.forward(request, response);
            }
        }
    }
}