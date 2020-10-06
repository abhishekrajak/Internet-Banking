package com.sbi;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "SBI_Local_Transaction_Display")
public class SBI_Local_Transaction_Display extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LinkedList<String> list = new LinkedList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("sbi_dao")==null){
            response.sendRedirect("sbi_not_logged_in.jsp");
        }else {
            SBI_DAO sbi_dao = (SBI_DAO) session.getAttribute("sbi_dao");
            list = SBI_Transaction_Executor.getTransactions(sbi_dao.getAccount_number());
        }
        request.getSession().setAttribute("transaction", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("transaction.jsp");
        requestDispatcher.forward(request, response);
    }

}
