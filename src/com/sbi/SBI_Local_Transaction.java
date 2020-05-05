package com.sbi;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "SBI_Local_Transaction")
public class SBI_Local_Transaction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Timestamp timestamp = new Timestamp(new Date().getTime());

        SBI_DAO sbi_dao = (SBI_DAO)request.getSession().getAttribute("sbi_dao");
        float destination = Integer.parseInt(request.getParameter("destination"));
        float amount = Integer.parseInt(request.getParameter("balance"));
        request.getSession().setAttribute("destination", destination);
        request.getSession().setAttribute("balance", amount);

        Local_Transaction_Result result =
                SBI_Transaction_Executor.execute((int)sbi_dao.getAccount_number(), (int)destination, amount, base_url.bankId, base_url.bankId, timestamp, true);

        String message = result.getMessage();
        System.out.println("Message : " + message);
        if(message.equals("success")){
            sbi_dao = (SBI_DAO)request.getSession().getAttribute("sbi_dao");
            try {
                sbi_dao = SBI_Transaction_Executor.generateData(sbi_dao.getUsername(), null, true);
            } catch (Exception e) {
                System.out.println("USER not found error");
            }
            request.getSession().setAttribute("sbi_dao", sbi_dao);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("local_transaction_successful.jsp");
            requestDispatcher.forward(request, response);
        }else if(message.equals("insufficient balance")){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("insufficient_balance.jsp");
            requestDispatcher.forward(request, response);
        }else{
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("sbi_account_welcome.jsp");
            requestDispatcher.forward(request, response);
        }
    }


}
