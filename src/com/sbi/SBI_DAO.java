package com.sbi;

import java.sql.*;

public class SBI_DAO {
    private String username;
    private String name;
    private float account_number;
    private float balance;

    private SBI_DAO() {
    }

    public SBI_DAO(String username, String name, float account_number, float balance){
        this.username = username;
        this.name = name;
        this.account_number = account_number;
        this.balance = balance;
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

    public float getAccount_number() {
        return account_number;
    }

    public void setAccount_number(long account_number) {
        this.account_number = account_number;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String toString(){
        return username + " " + name + " " + account_number + " " + balance;
    }
}
