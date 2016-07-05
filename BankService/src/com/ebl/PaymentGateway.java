package com.ebl;
import java.sql.*;
public class PaymentGateway {

	ResultSet rs;
	Connection con;
	Statement stmt;
	String retmsg="";
	public String checkBalance(String actno,float amount)
	{
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con =DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb","root","zxcvbnm");
			stmt=con.createStatement();
			rs=stmt.executeQuery("select accno,balance from account where accno='"+actno+"'");
			if(rs.next())
			{
				//System.out.println("Accno="+rs.getString(1));
				float bal=rs.getFloat(2);
				
				//System.out.println("Balance="+bal);
				//System.out.println("Payment Amount="+amount);
				if(amount<=bal)
				{
					retmsg="ok";
					try{con.close();stmt.close();rs.close();}catch(Exception e){}
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					con =DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb","root","zxcvbnm");
					stmt=con.createStatement();
					con.setAutoCommit(false);
					stmt.executeUpdate("UPDATE account set balance=balance-"+amount+" where accno='"+actno+"' ");
					con.commit();
					
				}
				else
				{
					retmsg="Insufficient Balanace";
				}
				
			}
			else
			{
				retmsg="account Does Not Exist !";
			}
			
		}
		catch(Exception e)
		{
			retmsg="Service is Down";
			e.printStackTrace();
		}
		finally
		{
			
		}
		
		System.out.println("Returned Message is:"+retmsg);
		return retmsg;
	}
	
}
