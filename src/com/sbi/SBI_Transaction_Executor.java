package com.sbi;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Data{
    protected String sender;
    protected String receiver;
    protected double amount;
}

public class SBI_Transaction_Executor {

    public static synchronized Local_Transaction_Result execute(String sender, String receiver, double amount)  {
        System.out.println("EXECUTOR started transaction");
        Connection connection = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(base_url.dbms_url, "c##SBI", "XXXXX");
            Statement statement = connection.createStatement();
            String sql;
            SBI_DAO senderData = null;
            boolean senderTest = false;
            sql ="select * from customers where username = '" + sender + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            String Username = null;
            String name = null;
            long account_number = 0;
            double balance = 0;
            while(resultSet.next()){
                senderTest = true;
                Username = resultSet.getString("username");
                name = resultSet.getString("name");
                account_number = resultSet.getLong("accountnumber");
                balance = resultSet.getDouble("balance");
            }
            if(senderTest){
                senderData = new SBI_DAO(Username, name, account_number, balance);
            }else{
                return new Local_Transaction_Result("sender not found");
            }

            sql ="select * from customers where username = '" + receiver + "'";
            resultSet = statement.executeQuery(sql);
            boolean receiverTest = false;
            SBI_DAO receiverData = null;
            while(resultSet.next()){
                receiverTest = true;
                Username = resultSet.getString("username");
                name = resultSet.getString("name");
                account_number = resultSet.getLong("accountnumber");
                balance = resultSet.getDouble("balance");
            }
            if(receiverTest){
                receiverData = new SBI_DAO(Username, name, account_number, balance);
            }
            else{
                return new Local_Transaction_Result("receiver not found");
            }

            System.out.println("FIRST HALF COMPLETED");

            if(senderData.getBalance() < amount){
                return new Local_Transaction_Result("insufficient balance");
            }else{
                senderData.setBalance(senderData.getBalance() - amount);
                receiverData.setBalance(receiverData.getBalance() + amount);

                sql = "update customers set balance = " + senderData.getBalance() + " where username = '" + senderData.getUsername() + "'";
                resultSet = statement.executeQuery(sql);

                sql = "update customers set balance = " + receiverData.getBalance() + " where username = '" + receiverData.getUsername() + "'";
                resultSet = statement.executeQuery(sql);

                System.out.println("SEconnectionD HALD COMPLETED");

                sql = "insert into transaction values (?, ?, ?, ?, ?)";

                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select count(*) from transaction");
                rs.next();
                int count = rs.getInt(1);
                count++;
                PreparedStatement ps = connection.prepareStatement(sql);

                String transaction = "transaction" + String.format("%05d", count);
                ps.setString(1, transaction);
                ps.setString(2, sender);
                ps.setString(3, receiver);
                ps.setString(4, Double.toString(amount));
                Date date = new Date();
                ps.setString(5, date.toString());
                System.out.println(date.toString());

                int i = ps.executeUpdate();

                if(i==1){
                    System.out.println("Transaction update done");
                }else{
                    System.out.println("FAILURE in transaction update");
                }

                return new Local_Transaction_Result("success");

            }
        }catch (Exception e){
            System.out.println("Executor issue " + e.getLocalizedMessage() + " " + e.getMessage());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        return new Local_Transaction_Result("failure");
    }

    public static synchronized SBI_DAO generateData(String username, String password, boolean isTestedBefore) throws Exception{
        SBI_DAO sbi_dao = null;
        try {
            System.out.println("trying database for " + username);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(base_url.dbms_url, "c##SBI", "XXXXX");
            Statement statement = connection.createStatement();
            String sql;
            if(!isTestedBefore){
                sql = "select * from customers where username = '" + username + "' and password = '" + password + "'" ;
            }else{
                sql = "select * from customers where username = '" + username + "'";
            }
            ResultSet resultSet = statement.executeQuery(sql);
            String Username = null;
            String name = null;
            long account_number = 0;
            double balance = 0;
            boolean tested = false;
            while(resultSet.next()){
                tested = true;
                Username = resultSet.getString("username");
                name = resultSet.getString("name");
                account_number = resultSet.getLong("accountnumber");
                balance = resultSet.getDouble("balance");
            }
            if(tested){
                sbi_dao = new SBI_DAO(Username, name, account_number, balance);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Issue");
            throw new Exception("Database Error");
        }
        return sbi_dao;
    }

    public static synchronized LinkedList<String> getTransactions(String username){
        System.out.println("called get Transactions with useraname " + username);
        Connection connection;
        LinkedList<String> list = new LinkedList<>();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(base_url.dbms_url, "c##SBI", "XXXXX");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from transaction where debit_account_id =\'" + username +
                    "\' or credit_account_id = \'" + username + "\'");
            while (rs.next()) {
                String a = rs.getString("transaction_id");
                String b = rs.getString("debit_account_id");
                String c = rs.getString("credit_account_id");
                String d = rs.getString("balance");
                String e = rs.getString("transaction_date_time");
                String data = "<tr><td>" + a + "</td><td>" + b + "</td><td>" + c + "</td><td>"+ d + "</td><td>" + e + "</td></tr>";
                list.add(data);
            }

            }catch (Exception e){
            System.out.println("Exception caught");
        }
        return list;
    }

}
