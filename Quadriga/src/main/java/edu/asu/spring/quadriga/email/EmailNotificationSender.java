package edu.asu.spring.quadriga.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * This class is used to send out notification emails.
 * 
 * @author Julia Damerow
 *
 */
@Service
@PropertySource("classpath:email.properties")
public class EmailNotificationSender {

	private static final Logger logger = LoggerFactory
			.getLogger(EmailNotificationSender.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JavaMailSender mailSender;

    public void sendNotificationEmail(String emailaddress, String subject, String msgText) {
    	/*
    	 * We need a valid from email address to be able to send an email.
    	 */
    	String from = env.getProperty("from_email_address");
    	if (from == null || from.trim().isEmpty()) {
    		logger.error("Notification email couldn't be sent. No valid sender email address specified.");
    		return;
    	}
    	
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(new InternetAddress(emailaddress));
            helper.setFrom(new InternetAddress(from));
            helper.setSubject(subject);
            helper.setText(msgText);
            mailSender.send(message);
            logger.debug("Send email to " + emailaddress + " with subject \"" + subject + "\"");
        } catch (MessagingException ex) {
            logger.error("Notification email could not be sent.", ex);
        }
    }
    
    public String getWebappHost() {
    	String host = env.getProperty("webapp_url");
    	if (!host.endsWith("/"))
    		host = host + "/";
    	return host;
    }
}
