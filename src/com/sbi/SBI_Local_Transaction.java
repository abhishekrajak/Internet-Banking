package com.sbi;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SBI_Local_Transaction")
public class SBI_Local_Transaction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SBI_DAO sbi_dao = (SBI_DAO)request.getSession().getAttribute("sbi_dao");

        System.out.println("test 1");

        String destination = request.getParameter("destination");
        double amount = Double.parseDouble(request.getParameter("balance"));

        System.out.println("test 2");

        if(amount > sbi_dao.getBalance()){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("insufficient_balance.jsp");
            requestDispatcher.forward(request, response);
        }
        else{


        }


        request.getSession().setAttribute("sbi_dao", sbi_dao);


    }

}
