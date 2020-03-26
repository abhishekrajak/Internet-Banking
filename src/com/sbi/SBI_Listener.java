package com.sbi;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

@WebListener()
public class SBI_Listener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public SBI_Listener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */

    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */

    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
      try{
          System.out.println("Added attribute : " + sbe.getName());
          if(sbe.getName().equals("local_transaction_result")){
              System.out.println("Listener caught local_transaction");
              HttpSession session = sbe.getSession();
              System.out.println("Got session");
              String sender = ((SBI_DAO)session.getAttribute("sbi_dao")).getUsername();
              System.out.println("Got sender " + sender);
              String receiver = (String)session.getAttribute("destination");
              System.out.println("Got receiver " + receiver);
              Double balance = (Double) session.getAttribute("balance");
              System.out.println("Got balance " + balance);
              System.out.println("calling Main transaction");
              Local_Transaction_Result local_transaction_result = SBI_Transaction_Executor.execute(sender, receiver, balance);
              session.setAttribute("local_transaction_result", local_transaction_result);
              System.out.println("Listener ended");
          }else{
              System.out.println("Other attribute added");
          }

      }catch (Exception e){
          System.out.println("Exception : " + e.getMessage());
      }

    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attribute
         is replaced in a session.
      */
    }
}
