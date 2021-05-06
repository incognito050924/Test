package com.example.test.outlook;

import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.simplejavamail.MailException;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;


public class Testoutlook {

	public static void main(String[] args) throws Exception {
//		sendMailOutlook();
//		sendMail();
		simpleMailTest();
		
	}
	
	private static void sendMail() {
		final String username = "odasd43@ecoletree.com";
        final String password = "lemons1945!";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("odasd43@ecoletree.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("odasd42@gmail.com"));
            message.setSubject("Test");
            message.setText("HI, this message is for the test.");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    
	}

	public static void sendMailOutlook() throws Exception {
		
		Properties props = null;
	   
        props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", "odasd42@outlook.kr");
        props.put("mail.smtp.pwd", "lemons1945");
	        
	    Session session = Session.getInstance(props, null);
	    session.setDebug(true);
	    Message msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress("odasd42@outlook.kr"));
	    msg.setSubject("Test Mail");
	    msg.setText("Hey, this is the testing email.");
	    msg.setRecipient(Message.RecipientType.TO, new InternetAddress("odasd42@gmail.com"));
	    Transport transport = session.getTransport("smtp");
	    transport.connect("smtp-mail.outlook.com", 587, "odasd42@outlook.kr", "lemons1945");
	    transport.sendMessage(msg, msg.getAllRecipients());
	    System.out.println("Mail sent successfully at " + "odasd42@gmail.com");
	    transport.close();
	}
		
	public static void simpleMailTest() {
		
		Email email = EmailBuilder.startingBlank()
				.from("테스트 아웃룩", "odasd42@outlook.kr")
				.to("박한빈", "odasd43@ecoletree.com")
				.withSubject("outLook Test")
				.withPlainText("We should meet up!")
				.buildEmail();
		
		Mailer mailer = MailerBuilder
				.withSMTPServer("smtp-mail.outlook.com", 587, "odasd42@outlook.kr", "lemons1945")
				.withTransportStrategy(TransportStrategy.SMTP)
				.buildMailer();
		
		try {
			mailer.sendMail(email);
			System.out.println("Exiting...");
		} catch(MailException e) {
			e.printStackTrace();
		}
	}
	
}
