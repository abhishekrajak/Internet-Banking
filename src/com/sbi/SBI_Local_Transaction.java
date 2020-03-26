package com.sbi;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SBI_Local_Transaction")
public class SBI_Local_Transaction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SBI_DAO sbi_dao = (SBI_DAO)request.getSession().getAttribute("sbi_dao");

        System.out.println("test 1");

        String destination = request.getParameter("destination");
        double amount = Double.parseDouble(request.getParameter("balance"));
        request.getSession().setAttribute("destination", destination);
        request.getSession().setAttribute("balance", amount);

        System.out.println("test 2");

        request.getSession().setAttribute("local_transaction_result", new Local_Transaction_Result("start"));

        System.out.println("test 3");

        int count = 0;
        while(((Local_Transaction_Result)request.getSession().getAttribute("local_transaction_result")).getMessage().equals("start")){
            try{
                System.out.println("started to sleep " + count++);
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("reached end");
        String mesage = ((Local_Transaction_Result)request.getSession().getAttribute("local_transaction_result")).getMessage();

        if(mesage.equals("success")){
            sbi_dao = (SBI_DAO)request.getSession().getAttribute("sbi_dao");
            try {
                sbi_dao = SBI_Transaction_Executor.generateData(sbi_dao.getUsername(), null, true);
            } catch (Exception e) {
                System.out.println("USER not found error");
            }
            System.out.println("BALANCE : " + sbi_dao.getBalance());
            request.getSession().setAttribute("sbi_dao", sbi_dao);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("local_transaction_successful.jsp");
            requestDispatcher.forward(request, response);
        }else{
            response.getWriter().write("SOME ERROR OCCURED");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("sbi_account_welcome.jsp");
            requestDispatcher.forward(request, response);
        }

    }

}
