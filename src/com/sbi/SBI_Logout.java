package com.sbi;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SBI_Logout")
public class SBI_Logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("sbi_dao", null);
        request.getSession().invalidate();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("sbi.jsp");
        requestDispatcher.forward(request, response);
    }
}