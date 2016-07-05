package edu.asu.spring.quadriga.email.impl;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * This class is used to send out notification emails.
 * 
 * @author Julia Damerow
 *
 */
public class EmailNotificationSender {

	private static final Logger logger = LoggerFactory
			.getLogger(EmailNotificationSender.class);

	@Autowired
	private JavaMailSender mailSender;
	
	private boolean enabled = false;
	
	private String fromAddress;
	
	public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Resource(name = "uiMessages")
	private Properties emailMessages;

	public void sendNotificationEmail(String emailaddress, String subject, String msgText) {    	
	    if (enabled) {
        	try {
    			MimeMessage message = mailSender.createMimeMessage();
    			MimeMessageHelper helper = new MimeMessageHelper(message, true);
    			helper.setTo(new InternetAddress(emailaddress));
    			helper.setSubject(subject);
    			helper.setFrom(new InternetAddress(fromAddress));
    			
    			//Adding tail to the message text
    			msgText += emailMessages.getProperty("email.tail");
    			
    			helper.setText(msgText);
    			mailSender.send(message);
    			logger.debug("Send email to " + emailaddress + " with subject \"" + subject + "\"");
    		} catch (MessagingException ex) {
    			logger.error("Notification email could not be sent.", ex);
    		}
	    }
	}
}
