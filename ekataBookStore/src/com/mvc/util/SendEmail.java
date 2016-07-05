package com.mvc.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class SendEmail {

	public void sendmail(String rcptmail)
	{
		try
		{
			String uname="[Hemraj Rijal]";
			uname=uname.substring(1,uname.length());
			uname=uname.substring(0,uname.length()-1);
			System.out.println(uname);
			
			/////////////////////////////////////////
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
	SendEmail obj=new SendEmail();
	obj.sendmail("hemraj.rijal2.c6@gmail.com");

	}
}


