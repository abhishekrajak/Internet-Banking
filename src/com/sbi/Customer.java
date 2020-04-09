package com.sbi;

import java.sql.Date;

public class Customer
{
    String firstName;
    String middleName;
    String lastName;
    String gender;
    String phoneNo;
    String address;
    String email;
    String dateOfBirth;

    String password;
    String username;
    double balance;

    public Customer(String fname,String mname,String lname,String gdr,String phno,String addr,String eml,String dob,String pwd,Double bal)
    {
        firstName = fname;
        middleName = mname;
        lastName = lname;
        gender = gdr;
        phoneNo = phno;
        address= addr;
        email = eml;
        dateOfBirth = dob;
        password = pwd;
        balance = bal;
    }

    public void setFirstname(String data)
    {
        firstName = data;
    }
    public void setMiddlename(String data)
    {
        middleName = data;
    }
    public void setLastname(String data)
    {
        lastName = data;
    }
    public void setGender(String data)
    {
        gender = data;
    }
    public void setPhoneNo(String data)
    {
        phoneNo = data;
    }
    public void setAddress(String data)
    {
        address = data;
    }
    public void setEmail(String data)
    {
        email = data;
    }
    public void setDateOfBirth(String data)
    {
        dateOfBirth = data;
    }

    public void setPassword(String data)
    {
        password = data;
    }
    public void setUsername(String data)
    {
        username = data;
    }
    public void setBalance(float data)
    {
        balance = data;
    }

    public String getFirstName()
    {
        return firstName;
    }
    public String getMiddleName()
    {
        return middleName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public String getGender()
    {
        return gender;
    }
    public String getPhoneNo()
    {
        return phoneNo;
    }
    public String getEmail()
    {
        return email;
    }
    public String getAddress()
    {
        return address;
    }
    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public String getPassword()
    {
        return password;
    }
    public String getUsername()
    {
        return username;
    }
    public double getBalance() { return balance; }
}
