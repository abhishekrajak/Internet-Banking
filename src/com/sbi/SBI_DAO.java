package com.sbi;

import java.sql.*;

public class SBI_DAO {
    private String username;
    private String name;
    private long account_number;
    private double balance;

    private SBI_DAO() {
    }

    private SBI_DAO(String username, String name, long account_number, double balance){
        this.username = username;
        this.name = name;
        this.account_number = account_number;
        this.balance = balance;
    }

    public static SBI_DAO generateTransactionData(String username) throws Exception{
        SBI_DAO sbi_dao = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(base_url.dbms_url, "c##SBI", "XXXXX");
            Statement statement = connection.createStatement();
            String sql = "select * from customers where username = '" + username + "'" ;
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                sbi_dao = new SBI_DAO();
                sbi_dao.username = resultSet.getString("username");
                sbi_dao.name = resultSet.getString("name");
                sbi_dao.account_number = resultSet.getLong("accountnumber");
                sbi_dao.balance = resultSet.getDouble("balance");
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Issue");
            throw new Exception("Database Error");
        }
        return sbi_dao;
    }

    public static SBI_DAO generateData(String username, String password) throws Exception{
        SBI_DAO sbi_dao = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(base_url.dbms_url, "c##SBI", "XXXXX");
            Statement statement = connection.createStatement();
            String sql = "select * from customers where username = '" + username + "' and password = '" + password + "'" ;
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                sbi_dao = new SBI_DAO();
                sbi_dao.username = resultSet.getString("username");
                sbi_dao.name = resultSet.getString("name");
                sbi_dao.account_number = resultSet.getLong("accountnumber");
                sbi_dao.balance = resultSet.getDouble("balance");
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Issue");
            throw new Exception("Database Error");
        }
        return sbi_dao;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAccount_number() {
        return account_number;
    }

    public void setAccount_number(long account_number) {
        this.account_number = account_number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String toString(){
        return username + " " + name + " " + account_number + " " + balance;
    }
}
