package com.sbi;

import java.sql.*;
import java.sql.DriverManager;


public class CreateDao 
{
	private String accountNo;
	public boolean check(Customer c) 
	{
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String customerQuery = "insert into customers values (?, ?, ?, ?, ?)";

			if(c.getMiddleName() == null) {
				c.setMiddlename("-");
			}
		
			Connection con = DriverManager.getConnection(base_url.dbms_url, "c##SBI", "XXXXX");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select count(username) from customers");
			rs.next();
			int count = rs.getInt(1);
			count++;
			String name = c.getFirstName() + " " + c.getMiddleName() + " " + c.getLastName() + "";
			PreparedStatement ps = con.prepareStatement(customerQuery);
			c.setUsername(String.format("sbi%05d", count));
			System.out.println(c.getUsername());

			ps.setString(1, c.getUsername());
			ps.setString(2, name);
			ps.setString(3, String.valueOf(c.getBalance()));
			ps.setString(4, c.getPassword());
			ps.setString(5, (accountNo = String.valueOf(String.format("%05d", count))));
			int i = ps.executeUpdate();
		
			if(i == 1)
				return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getAccountNo(){
		return accountNo;
	}
	
	
}
