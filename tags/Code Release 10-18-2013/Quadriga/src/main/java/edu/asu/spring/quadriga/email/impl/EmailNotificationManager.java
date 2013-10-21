package edu.asu.spring.quadriga.email.impl;

import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.email.IEmailNotificationManager;

@Service
public class EmailNotificationManager implements IEmailNotificationManager{

	private static final Logger logger = LoggerFactory
			.getLogger(EmailNotificationManager.class);
	
	@Autowired
	private EmailNotificationSender emailSender;
	
	@Resource(name = "uiMessages")
	private Properties emailMessages;
	
	@Override
	public void sendAccountDeactivationEmail(IUser user, String adminid) {
		if(user.getEmail()!= null && !user.getEmail().equals(""))
		{
			emailSender.sendNotificationEmail(user.getEmail(), emailMessages.getProperty("email.account_deactivation_subject"), emailMessages.getProperty("email.account_deactivation_msg"));
			logger.info("The admin "+adminid+" sent a deactivation email to "+user.getUserName());
		}	
	}
	
	@Override
	public void sendAccountActivationEmail(IUser user, String adminid) {
		if(user.getEmail()!= null && !user.getEmail().equals(""))
		{
			emailSender.sendNotificationEmail(user.getEmail(), emailMessages.getProperty("email.account_activation_subject"), emailMessages.getProperty("email.account_activation_msg"));
			logger.info("The admin "+adminid+" sent an activation email to "+user.getUserName());
		}
	}

	@Override
	public void sendNewAccountRequestPlacementEmail(IUser admin, String userid) {
		if(admin.getEmail()!=null && !admin.getEmail().equals("") && userid!=null && !userid.equals(""))
		{
			String message = emailMessages.getProperty("email.account_request_msg_head")+userid+" "+emailMessages.getProperty("email.account_request_msg_tail");
			emailSender.sendNotificationEmail(admin.getEmail(), emailMessages.getProperty("email.account_request_subject"), message );
			logger.info("The system sent a user request email to "+admin.getUserName()+" for the request placed by "+userid);
		}
		
	}

}
