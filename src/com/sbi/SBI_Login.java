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
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("generating sbi login");
        SBI_DAO sbi_dao;
        try {
            sbi_dao = SBI_Transaction_Executor.generateData(username, password, false);
        } catch (Exception e) {
            System.out.println("Exception sent");
            response.getWriter().write(e.getMessage());
            System.out.println("XOXO");
            response.sendRedirect("Database_Error.html");
            return;
        }

        System.out.println("getting session");
        HttpSession session = request.getSession();
        if(sbi_dao == null){
            response.sendRedirect("redirect_sbi_login.jsp");
            System.out.println("Setting sbi_dao");
            session.setAttribute("sbi_dao", null);
            System.out.println("setting done");
        }else{
            System.out.println("Setting sbi_dao 2");
            session.setAttribute("sbi_dao", sbi_dao);
            System.out.println("setting done 2");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("sbi_account_welcome.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
