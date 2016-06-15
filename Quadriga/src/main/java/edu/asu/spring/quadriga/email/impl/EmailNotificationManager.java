package edu.asu.spring.quadriga.email.impl;

import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.email.IEmailNotificationManager;

/**
 * This class manages all the outgoing emails from the Quadriga system.
 * @author Ram Kumar Kumaresan
 */
@Service
public class EmailNotificationManager implements IEmailNotificationManager{

	private static final Logger logger = LoggerFactory
			.getLogger(EmailNotificationManager.class);
	
	@Autowired
	private EmailNotificationSender emailSender;
	
	@Resource(name = "uiMessages")
	private Properties emailMessages;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendAccountDeactivationEmail(IUser user, String adminid) {
		if(user.getEmail()!= null && !user.getEmail().equals(""))
		{
			emailSender.sendNotificationEmail(user.getEmail(), emailMessages.getProperty("email.account_deactivation_subject"), emailMessages.getProperty("email.account_deactivation_msg"));
			logger.info("The admin <<"+adminid+">> sent a deactivation email to <"+user.getUserName()+">");
		}	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendAccountActivationEmail(IUser user, String adminid) {
		if(user.getEmail()!= null && !user.getEmail().equals(""))
		{
			emailSender.sendNotificationEmail(user.getEmail(), emailMessages.getProperty("email.account_activation_subject"), emailMessages.getProperty("email.account_activation_msg"));
			logger.info("The admin <<"+adminid+">> sent an activation email to <"+user.getUserName()+">");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendNewAccountRequestPlacementEmail(IUser admin, String userid) {
		if(admin.getEmail()!=null && !admin.getEmail().equals("") && userid!=null && !userid.equals(""))
		{
			String message = emailMessages.getProperty("email.account_request_msg_head")+userid+" "+emailMessages.getProperty("email.account_request_msg_tail");
			emailSender.sendNotificationEmail(admin.getEmail(), emailMessages.getProperty("email.account_request_subject"), message );
			logger.info("The system sent a user request email to <<"+admin.getUserName()+">> for the request placed by <"+userid+">");
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendNewWorkspaceAddedToProject(IProject project, IWorkSpace workspace){
		IUser projectOwner = project.getOwner();
		if(projectOwner.getEmail() != null && !projectOwner.getEmail().equals(""))
		{
			//Build the message to be sent to the user
			StringBuilder message = new StringBuilder(emailMessages.getProperty("email.new_workspace_msg_part1"));
			message.append(project.getProjectName()+".");
			message.append(emailMessages.getProperty("email.new_workspace_msg_part2"));
			message.append(emailMessages.getProperty("email.new_workspace_msg_part3"));
			message.append(workspace.getWorkspaceName());
			message.append(emailMessages.getProperty("email.new_workspace_msg_part4"));
			message.append(workspace.getDescription());
			message.append(emailMessages.getProperty("email.new_workspace_msg_part5"));
			message.append(workspace.getOwner().getUserName());

			emailSender.sendNotificationEmail(projectOwner.getEmail(), emailMessages.getProperty("email.new_workspace_subject"), message.toString());
			logger.info("The system sent an email notification to <<"+projectOwner.getEmail()+">> for the workspace <"+workspace.getWorkspaceName()+"> added by <"+workspace.getOwner().getUserName()+">");
		}
	}

}
