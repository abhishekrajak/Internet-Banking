package com.sbi;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

class Data{
    protected String sender;
    protected String receiver;
    protected double amount;
}

class Global_Data{
    protected float sender;
    protected String senderBank;
    protected String receiverBank;
    protected float receiver;
    protected float amount;
    protected String transaction_id;
    protected Timestamp timestamp;

    public String toString(){
        return senderBank + " ," + sender + " ," + receiverBank + " ," + receiver + " ," + amount + " ," + transaction_id + " ," + timestamp;
    }
}

public class SBI_Transaction_Executor {
    private static final String forName = "oracle.jdbc.driver.OracleDriver";
    private static final String user = base_url.user;
    private static final String password = base_url.password;

    public static synchronized Local_Transaction_Result execute(int sender, int receiver, float amount, String senderBank, String receiverBank, Timestamp timestamp, boolean committed)  {
        System.out.println("EXECUTOR  execute started transaction");
        Connection connection = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(base_url.dbms_url, user, password);
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            String sql;
            SBI_DAO senderData = null;
            boolean senderTest = false;
            sql ="select * from customers where accountnumber = " + sender;
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            String Username = null;
            String name = null;
            int account_number = 0;
            float balance = 0;
            while(resultSet.next()){
                senderTest = true;
                Username = resultSet.getString("username");
                name = resultSet.getString("name");
                account_number = resultSet.getInt("accountnumber");
                balance = resultSet.getFloat("balance");
            }
            if(senderTest){
                System.out.println("Sender data found");
                senderData = new SBI_DAO(Username, name, account_number, balance);
            }else{
                System.out.println("Sender data not found");
                connection.rollback();
                connection.close();
                return new Local_Transaction_Result("sender not found");
            }

            sql = "select * from customers where accountnumber = " + receiver;
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
            boolean receiverTest = false;
            SBI_DAO receiverData = null;
            while(resultSet.next()){
                receiverTest = true;
                Username = resultSet.getString("username");
                name = resultSet.getString("name");
                account_number = resultSet.getInt("accountnumber");
                balance = resultSet.getFloat("balance");
            }
            if(receiverTest){
                receiverData = new SBI_DAO(Username, name, account_number, balance);
            }
            else{
                System.out.println("receiver data not found");
                connection.rollback();
                connection.close();
                return new Local_Transaction_Result("receiver not found");
            }

            if(senderData.getBalance() < amount){
                return new Local_Transaction_Result("insufficient balance");
            }else{
                senderData.setBalance(senderData.getBalance() - amount);
                receiverData.setBalance(receiverData.getBalance() + amount);

                sql = "update customers set balance = " + senderData.getBalance() + " where accountnumber = " + sender;
                System.out.println(sql);
                resultSet = statement.executeQuery(sql);

                sql = "update customers set balance = " + receiverData.getBalance() + " where accountnumber = " + receiver;
                System.out.println(sql);
                resultSet = statement.executeQuery(sql);

                sql = "insert into transaction values (?, ?, ?, ?, ?, ?, ?, ?)";

                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select count(*) from transaction");
                rs.next();
                int count = rs.getInt(1);
                count++;
                PreparedStatement ps = connection.prepareStatement(sql);
                String transaction = null;
                transaction =  base_url.bankId + "_local_transaction_" + String.format("%05d", count);
                ps.setString(1, transaction);
                ps.setString(2, senderBank);
                ps.setString(3, Integer.valueOf(sender).toString());
                ps.setString(4, receiverBank);
                ps.setString(5, Integer.valueOf(receiver).toString());
                ps.setString(6, Float.toString(amount));
                ps.setTimestamp(7, timestamp);

                String isCommitted = (committed?"TRUE":"FALSE");
                ps.setString(8, isCommitted);

                int i = ps.executeUpdate();

                if(i==1){

                    sql = "insert into transaction_query values(?, ?, ?, ?)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, transaction);
                    ps.setString(2, Integer.valueOf(sender).toString());
                    ps.setString(3, Integer.toString((int) (senderData.getBalance()+(int)amount)));
                    ps.setString(4, Integer.toString((int)senderData.getBalance()));

                    System.out.println("SQL --------> " + ps);

                    int j = ps.executeUpdate();

                    System.out.println("j : " + j);


                    sql = "insert into transaction_query values(?, ?, ?, ?)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, transaction);
                    ps.setString(2, Integer.valueOf(receiver).toString());
                    ps.setString(3, Integer.toString((int) (receiverData.getBalance()-(int)amount)));
                    ps.setString(4, Integer.toString((int)receiverData.getBalance()));

                    System.out.println("SQL --------> " + ps);

                    j = ps.executeUpdate();

                    System.out.println("j : " + j);

                    return new Local_Transaction_Result("success");
                }else{
                    throw new Exception("DATABASE Error");
                }
            }
        }catch (Exception e){
            System.out.println("Executor issue " + e.getLocalizedMessage() + " " + e.getMessage());
            try{
                connection.rollback();
                connection = null;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }finally {
            try {
                if(connection != null){

                    System.out.println("TRANSACTION IS COMMITTED");

                    connection.commit();
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        return new Local_Transaction_Result("Some unexpected failure");
    }

    public static synchronized SBI_DAO generateData(String username, String passwordX, boolean isTestedBefore) throws Exception{
        SBI_DAO sbi_dao = null;
        try {
            System.out.println("trying database for " + username);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(base_url.dbms_url, user, password);
            Statement statement = connection.createStatement();
            String sql;
            if(!isTestedBefore){
                sql = "select * from customers where username = '" + username + "' and password = '" + passwordX + "'" ;
            }else{
                sql = "select * from customers where username = '" + username + "'";
            }
            ResultSet resultSet = statement.executeQuery(sql);
            String Username = null;
            String name = null;
            float account_number = 0;
            float balance = 0;
            boolean tested = false;
            while(resultSet.next()){
                tested = true;
                Username = resultSet.getString("username");
                name = resultSet.getString("name");
                account_number = resultSet.getFloat("accountnumber");
                balance = resultSet.getFloat("balance");
            }
            if(tested){
                System.out.println("DATABASE FOUND");
                sbi_dao = new SBI_DAO(Username, name, account_number, balance);
            }else{
                System.out.println("DATABASE IS NOT FOUND");
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Issue" + e.getLocalizedMessage() + e.getMessage() + e.toString());
        }
        return sbi_dao;
    }

    public static synchronized LinkedList<String> getTransactions(float useraccountnumber){
        System.out.println("called get Transactions with useraccountnumber " + useraccountnumber);
        Connection connection;
        LinkedList<String> list = new LinkedList<>();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(base_url.dbms_url, user, password);
            Statement stmt = connection.createStatement();
//            String sql = "with X as (select * from transaction where debit_account_number = " + useraccountnumber +
//                    " or credit_account_number = " + useraccountnumber +
//                    " UNION " + "select * from global_transaction where ( debit_bank_id = '" + base_url.bankId + "' and  debit_account_number = " + useraccountnumber +
//                    ") or ( credit_bank_id = '" + base_url.bankId + "' and credit_account_number = " + useraccountnumber + ")) " +
//                    "select * from X order by date_of_transaction desc " ;

            String sql = "with X as " +
                    "((select * from transaction where ( debit_bank_id = '" + base_url.bankId + "' and  debit_account_number = " + useraccountnumber +
                        ") or ( credit_bank_id = '" + base_url.bankId + "' and credit_account_number = " + useraccountnumber +
                    ")) UNION " +
                    "select * from global_transaction where ( debit_bank_id = '" + base_url.bankId + "' and  debit_account_number = " + useraccountnumber +
                    ") or ( credit_bank_id = '" + base_url.bankId + "' and credit_account_number = " + useraccountnumber + "))" +
                    "select X.*, Y.previous_amount, Y.current_amount from X, transaction_query Y " +
                    "where X.transaction_id = Y.transaction_id AND Y.account_number = " + useraccountnumber +
                    " order by date_of_transaction desc ";

            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql) ;
            while (rs.next()) {
                String a = rs.getString("transaction_id");
                String b = rs.getString("debit_bank_id");
                String c = String.format("%05d", (int)rs.getFloat("debit_account_number"));
                String d = rs.getString("credit_bank_id");
                String e = String.format("%05d", (int)rs.getFloat("credit_account_number"));
                String f = rs.getString("amount");
                String g = rs.getString("date_of_transaction");

                String h = rs.getString("previous_amount");
                String i = rs.getString("current_amount");

                System.out.println("previous : " + h);
                System.out.println("current : " + i);

                String data = "<tr><td>" + a + "</td><td>" + b + "</td><td>" + c + "</td><td>"+ d + "</td><td>" + e +
                        "</td><td>" + f + "</td><td>" + g + "</td><td>" + h + "</td><td>" + i + "</td></tr>";
                list.add(data);
            }

            }catch (Exception e){
            System.out.println("Exception caught" + e.toString() + e.getMessage() + e.getLocalizedMessage());
        }
        return list;
    }



    public static synchronized boolean initiate(Global_Data global_data){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String customerQuery = "insert into global_transaction values (?, ?, ?, ?, ?, ?, ?, ?)";

            Connection con = DriverManager.getConnection(base_url.dbms_url, user, password);
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(customerQuery);

            ps.setString(1, global_data.transaction_id);
            ps.setString(2, global_data.senderBank);
            ps.setFloat(3, global_data.sender);
            ps.setString(4, global_data.receiverBank);
            ps.setFloat(5, global_data.receiver);
            ps.setFloat(6, global_data.amount);
            ps.setTimestamp(7, global_data.timestamp);
            ps.setString(8, "FALSE");

            int i = ps.executeUpdate();

            System.out.println("global update : " + i);

            if(i == 1) {
                return true;
            }
        }
        catch(Exception e) {
            System.out.println("Exception at global transaction");
            System.out.println(e.getCause() + e.getMessage() + e.toString());
        }
        return false;
    }

    public static synchronized boolean holdSender(Global_Data global_data){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String addBalance = "update customers set balance = balance - " + global_data.amount + " where accountnumber = " + global_data.sender;
            Connection con = DriverManager.getConnection(base_url.dbms_url, user, password);
            PreparedStatement ps = con.prepareStatement(addBalance);
            int i = ps.executeUpdate();
            if (i == 1) {
//
                String username = base_url.bankId + String.format("%05d", (int)global_data.sender);
                System.out.println("global hold Sender + " + username);
                SBI_DAO sbi_dao = generateData(username, "", true);
                String sql = "insert into transaction_query values(?, ?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, global_data.transaction_id);
                ps.setString(2, Float.valueOf(global_data.sender).toString());
                ps.setString(3, Integer.toString((int) (sbi_dao.getBalance() + global_data.amount)));
                ps.setString(4, Integer.toString((int)sbi_dao.getBalance()));

                i = ps.executeUpdate();

                System.out.println("solution for global_transaction_update : " + i);

//

                return true;
            }
        }
        catch(Exception e) {
            System.out.println(e.getCause() + e.getMessage());
        }
        return false;
    }

    public static synchronized boolean commitReceiver(Global_Data global_data){
        try {

            System.out.println("commit receiver is called");

            Class.forName("oracle.jdbc.driver.OracleDriver");
            String commitQuery = "update global_transaction set committed = 'TRUE' where transaction_id = '" + global_data.transaction_id + "'";
            String addBalance = "update customers set balance = balance + " + global_data.amount + " where accountnumber = " + global_data.receiver;

            Connection con = DriverManager.getConnection(base_url.dbms_url, user, password);
            PreparedStatement ps = con.prepareStatement(addBalance);
            int i = ps.executeUpdate();{
                if (i == 1) {
                    ps = con.prepareStatement(commitQuery);
                    i = ps.executeUpdate();
                    if (i == 1) {

//
                        String username = base_url.bankId + String.format("%05d", (int)global_data.receiver);
                        System.out.println("global commit receiver + " + username);
                        SBI_DAO sbi_dao = generateData(username, "", true);
                        String sql = "insert into transaction_query values(?, ?, ?, ?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, global_data.transaction_id);
                        ps.setString(2, Float.valueOf(global_data.receiver).toString());
                        ps.setString(3, Integer.toString((int) (sbi_dao.getBalance() - global_data.amount)));
                        ps.setString(4, Integer.toString((int)sbi_dao.getBalance()));

                        i = ps.executeUpdate();

                        System.out.println("solution for global_transaction_update : " + i);

//

                        return true;
                    }
                }
            }

            System.out.println("commit receiver ended");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean commitSender(Global_Data global_data){
        System.out.println("commit sender called");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String commitQuery = "update global_transaction set committed = 'TRUE' where transaction_id = '" + global_data.transaction_id + "'";
            Connection con = DriverManager.getConnection(base_url.dbms_url, user, password);
            PreparedStatement ps = con.prepareStatement(commitQuery);
            int i = ps.executeUpdate();
            System.out.println("UPDATED # of tables : " + i);
            if (i == 1 || i ==2 ) {
                System.out.println("commit sender passed");
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("commit sender failed");
        return false;
    }

    public static synchronized boolean createAccount(Customer c, String[] accountNo){

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(base_url.dbms_url, user, password);

            String customerQuery = "insert into customers values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            if(c.getMiddleName() == null) {
                c.setMiddlename("-");
            }

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(username) from customers");
            rs.next();
            int count = rs.getInt(1);
            count++;
            String name = c.getFirstName() + " " + c.getMiddleName() + " " + c.getLastName() + "";
            PreparedStatement ps = con.prepareStatement(customerQuery);
            c.setUsername(base_url.bankId + String.format("%05d", count));
            System.out.println(c.getUsername());

            ps.setString(1, c.getUsername());
            ps.setString(2, name);
            ps.setString(3, String.valueOf(c.getBalance()));
            ps.setString(4, c.getPassword());
            ps.setString(5, (accountNo[0] = String.valueOf(String.format("%05d", count))));
            ps.setString(6, c.getEmail());
            ps.setString(7, c.getAddress());
            ps.setString(8, c.getGender());
            ps.setString(9, c.getPhoneNo());

            System.out.println("original : " + c.getDateOfBirth());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(c.getDateOfBirth());

            System.out.println("date : " + date.toString());

            DateFormat requiredDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String dob = requiredDateFormat.format(date);

            System.out.println(dob);
            c.setDateOfBirth(dob);
            ps.setString(10, dob);

            int i = ps.executeUpdate();

            if(i == 1)
                return true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}