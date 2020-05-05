package com.sbi;

import java.sql.*;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateDao 
{
	protected String[] accountNo = new String[1];
	public boolean check(Customer c) {
		return SBI_Transaction_Executor.createAccount(c, accountNo);
	}

	public String getAccountNo(){
		return accountNo[0];
	}
}
