package com.dobo.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	public static void main(String[] args) throws AddressException,
			MessagingException {
		
		List<String> test = new LinkedList<String>();
		test.add("asdfa");
		test.add("vrvfd");
		test.add("vscx");
		test.add("htgbfb");
		
		String test1 = test.toString();
		System.out.println(test.toString());
		System.out.println(Double.valueOf(10/3));
		
		//String[] test2 = test1.
		
		/*System.setProperty("java.net.preferIPv4Stack", "true");
	    Properties mailpro = new Properties();
	    mailpro.setProperty("mail.smtp.host", "10.1.180.50");
	    Session session = Session.getDefaultInstance(mailpro);
	    session.setDebug(true);
	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress("ruixing@digitalchina.com"));
	    msg.setRecipients(RecipientType.TO, InternetAddress.parse("husba@digitalchina.com"));
	    msg.setSubject("测试免认证方式发送邮件！！！");
	    msg.setText("测试一下，邮件来自 http://www.donews.net/lizongbo ");
	    Transport.send(msg);*/
	}
}
