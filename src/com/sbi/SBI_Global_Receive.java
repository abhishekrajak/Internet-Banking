package com.sbi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "SBI_Global_Receive")
public class SBI_Global_Receive extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Global_Data global_data = new Global_Data();

//        arguments.put("username", "SBI");
//        arguments.put("password", "sjh76HSn!");
//        arguments.put("sender_account", Float.toString(global_data.sender));
//        arguments.put("receiver_account", Float.toString(global_data.receiver));
//        arguments.put("sender_bank", global_data.senderBank);
//        arguments.put("receiver_bank", global_data.receiverBank);
//        arguments.put("balance", Float.toString(global_data.amount));
//        arguments.put("transaction_id", global_data.transaction_id);
//        arguments.put("date_of_transaction", Long.toString(global_data.timestamp.getTime()));
//        arguments.put("function", function);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("username : " + username);
        System.out.println("password : " + password);

        if(!password.equals(base_url.bankPassword)){
            response.setStatus(404);
            return;
        }

        global_data.sender = Float.parseFloat(request.getParameter("sender_account"));
        global_data.receiver = Float.parseFloat(request.getParameter("receiver_account"));
        global_data.senderBank = request.getParameter("sender_bank");
        global_data.receiverBank = request.getParameter("receiver_bank");
        global_data.amount = Float.parseFloat(request.getParameter("balance"));
        global_data.transaction_id = request.getParameter("transaction_id");
        global_data.timestamp = new Timestamp(Long.parseLong(request.getParameter("date_of_transaction")));

        String function = request.getParameter("function");

        if(function.equals("initialise")){
            boolean status = SBI_Transaction_Executor.initiate(global_data);

            if(!status){
                response.setStatus(404);
            }

        }else if(function.equals("commit")){
            boolean status = SBI_Transaction_Executor.commitReceiver(global_data);

            if(!status){
                response.setStatus(404);
            }

        }else{
            System.out.println("Invalid function");
            response.setStatus(404);
        }
    }

}
