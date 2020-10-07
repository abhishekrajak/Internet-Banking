package com.sbi;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

@WebServlet(name = "SBI_Global_Transaction")
public class SBI_Global_Transaction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        float destination_account = Float.valueOf(request.getParameter("destination"));
        float balance = Float.valueOf(request.getParameter("balance"));


        SBI_DAO sbi_dao = (SBI_DAO)request.getSession().getAttribute("sbi_dao");

        Global_Data global_data = new Global_Data();
        global_data.amount = balance;
        global_data.senderBank = base_url.bankId;
        global_data.receiverBank = request.getParameter("bank");
        global_data.receiver = destination_account;
        global_data.sender = sbi_dao.getAccount_number();
        global_data.timestamp = new Timestamp(new Date().getTime());
        global_data.transaction_id = base_url.bankId + "_global_" + global_data.sender + "_" + global_data.receiver + global_data.timestamp.toString();
        System.out.println(global_data);

        boolean status = false;

        PrintWriter pw = response.getWriter();
        for(int i=0; i<5 && !status; i++){
            pw.write("Trying to connect to Foreign Bank....\n");
            status = callWebServer(global_data, "initialise");
            if(status){
                System.out.println("Connection successfull...\nTrying to initiate the transaction...\n");
            }else{
                System.out.println("Connection is not successful trying again attempt # : " + (i+1) + "\n");
            }
        }

        if(!status){
            request.getSession().setAttribute("error_message", "Could not initiate the transaction try again later");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ErrorRedirect.jsp");
            requestDispatcher.forward(request, response);
        }

        System.out.println("status 1 : " + status);

        boolean status2 = false;
        try {
            pw.write("Initiating transaction message on Current Bank Server\n");
            status = SBI_Transaction_Executor.initiate(global_data);
            pw.write("Success\nDeduction of amount from current Bank");
            status2 = SBI_Transaction_Executor.holdSender(global_data);
            pw.write("Success\n");
        }catch (Exception e){
            System.out.println(e.getCause() + e.getMessage() + e.toString());
        }

        if(!status){
            pw.write("Issue in writing transaction row in global_transaction_table");
            request.getSession().setAttribute("error_message", "Issue in writing transaction row in global_transaction_table");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ErrorRedirect.jsp");
            requestDispatcher.forward(request, response);
        }

        if(!status2){
            pw.write("Issue in deducation of balance");
            request.getSession().setAttribute("error_message", "Issue in deducation of balance");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ErrorRedirect.jsp");
            requestDispatcher.forward(request, response);
        }

        status = callWebServer(global_data, "commit");
        System.out.println("status 2 : " + status);

        if(status){
            pw.write("Foreign Bank confirmed the transaction\n");
        }else{
            pw.write("Foreign Bank did not confirm the transaction\n");
            request.getSession().setAttribute("error_message", "Foreign Bank did not confirm the transaction\n");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ErrorRedirect.jsp");
            requestDispatcher.forward(request, response);
        }

        status = SBI_Transaction_Executor.commitSender(global_data);

        if(status){
            pw.write("Transaction Complete\n");
        }else{
            pw.write("Current Bank did not commit the transaction\n");
            request.getSession().setAttribute("error_message", "Current Bank did not commit the transaction\n\n");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ErrorRedirect.jsp");
            requestDispatcher.forward(request, response);
        }

        pw.write("TRANSACTION SUCCESSFUL\n");
        request.getSession().setAttribute("error_message", "TRANSACTION SUCCESSFUL\n");
        request.getSession().removeAttribute("error_message");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("local_transaction_successful.jsp");
        requestDispatcher.forward(request, response);

    }
    protected boolean callWebServer(Global_Data global_data, String function){
        int status = 0;
        try{
            URL url = new URL(base_url.globalBank);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            Map<String,String> arguments = new HashMap<>();
            arguments.put("username", base_url.bankId);
            arguments.put("password", "sjh76HSn!");
            arguments.put("sender_account", Float.toString(global_data.sender));
            arguments.put("receiver_account", Float.toString(global_data.receiver));
            arguments.put("sender_bank", global_data.senderBank);
            arguments.put("receiver_bank", global_data.receiverBank);
            arguments.put("balance", Float.toString(global_data.amount));
            arguments.put("transaction_id", global_data.transaction_id);
            arguments.put("date_of_transaction", Long.toString(global_data.timestamp.getTime()));
            arguments.put("function", function);
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            long length = out.length;

            http.setFixedLengthStreamingMode(length);
            System.out.println("Trying to connect");
            http.connect();
            System.out.println("connection success");
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            System.out.println("status : " + (status = http.getResponseCode()));
            System.out.println("status message " + http.getResponseMessage());
            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String str;
            while((str = br.readLine()) != null){
                System.out.println(str);
            }
        }catch (Exception e){
            System.out.println("Exception caught for function : " + function);
            System.out.println(e.getCause() + e.getMessage() + e.toString());
        }


        return (status==200);
    }
}
