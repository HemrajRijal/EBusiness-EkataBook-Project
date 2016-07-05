package com.mvc.dao;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.Properties;
public class SendEmail {

	public String sendmail(String rcptmail,String tbldata,String cust_name)
	{
		try
		{
			/////////////////////////////////////////
			final String username = "hemraj.rijal2.c6@gmail.com";
			final String password = "d@ngpare6u";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "465");

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });

		

			MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username,"ekataBookStore.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(rcptmail));
				message.setSubject("Order Confirmation-ekataBookStore.com");
				
				StringBuilder sb = new StringBuilder();
				sb.append("<div>");
				sb.append("<p> Hi,"+cust_name+",</p><br> Your Order for following Items have been Confirmed..!<br>");
				sb.append("<div>");
				sb.append("<html><body><br> ");
				sb.append(tbldata);
				sb.append("</body>");
				sb.append("<div>");
				sb.append("<br> Thank you For Visiting Wish Us !");
				sb.append("<br> -ekataBookStore.com");
				sb.append("<div>");
				sb.append("</html>");
				
				message.setContent(sb.toString(), "text/html" );
				Transport.send(message);
				System.out.println("Message Sent");
				return "ok";
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Even Though Confirmation E-Mail cannot be Sent Due to Some Technical Reason, Your Order has now Been Confirmed !";
		}
	}
	
}

