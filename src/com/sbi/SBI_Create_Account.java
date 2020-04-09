package com.sbi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SBI_Create_Account")
public class SBI_Create_Account extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String fname = request.getParameter("fname");
        String mname = request.getParameter("mname");
        String lname = request.getParameter("lname");
        String gdr = request.getParameter("gender");
        String phno = request.getParameter("phno");
        String addr = request.getParameter("address");
        String eml = request.getParameter("email");
        String dob = request.getParameter("dob");
        String pwd = request.getParameter("password");
        double bal = Float.parseFloat(request.getParameter("bal"));
        Customer c = new Customer(fname,mname,lname,gdr,phno,addr,eml,dob,pwd,bal);
        CreateDao cd = new CreateDao();
        if(cd.check(c)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", c.getUsername());
            session.setAttribute("accountNo", cd.getAccountNo());
            response.sendRedirect("create_account_successful.jsp");
        }
        else{
            response.sendRedirect("create_account.jsp");
        }
    }
}