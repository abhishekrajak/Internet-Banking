package com.sbi;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "SBI_Login_Filter")
public class SBI_Login_Filter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username.matches("[a-z]+[0-9]*") && password.matches("[a-z]+[0-9]*")) {
            chain.doFilter(req, resp);
        }else{
            response.sendRedirect("redirect_sbi_login.jsp");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
