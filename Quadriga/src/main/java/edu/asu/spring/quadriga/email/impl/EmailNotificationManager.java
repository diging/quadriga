package edu.asu.spring.quadriga.email.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.email.IEmailNotificationManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaNotificationException;
import edu.asu.spring.quadriga.velocity.IVelocityBuilder;

/**
 * This class manages all the outgoing emails from the Quadriga system.
 * 
 * @author Ram Kumar Kumaresan
 */
@Service
public class EmailNotificationManager implements IEmailNotificationManager {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationManager.class);

    @Autowired
    private EmailNotificationSender emailSender;

    @Autowired
    private IVelocityBuilder velocityBuilder;

    @Resource(name = "uiMessages")
    private Properties emailMessages;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAccountDeactivationEmail(IUser user, String adminid) {
        if (user.getEmail() != null && !user.getEmail().equals("")) {
            emailSender.sendNotificationEmail(user.getEmail(),
                    emailMessages.getProperty("email.account_deactivation_subject"),
                    emailMessages.getProperty("email.account_deactivation_msg"));
            logger.info("The admin <<" + adminid + ">> sent a deactivation email to <" + user.getUserName() + ">");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAccountActivationEmail(IUser user, String adminid) {
        if (user.getEmail() != null && !user.getEmail().equals("")) {
            emailSender.sendNotificationEmail(user.getEmail(),
                    emailMessages.getProperty("email.account_activation_subject"),
                    emailMessages.getProperty("email.account_activation_msg"));
            logger.info("The admin <<" + adminid + ">> sent an activation email to <" + user.getUserName() + ">");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendNewAccountRequestPlacementEmail(IUser admin, String userid) {
        if (admin.getEmail() != null && !admin.getEmail().equals("") && userid != null && !userid.equals("")) {
            String message = emailMessages.getProperty("email.account_request_msg_head") + userid + " "
                    + emailMessages.getProperty("email.account_request_msg_tail");
            emailSender.sendNotificationEmail(admin.getEmail(),
                    emailMessages.getProperty("email.account_request_subject"), message);
            logger.info("The system sent a user request email to <<" + admin.getUserName()
                    + ">> for the request placed by <" + userid + ">");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendNewWorkspaceAddedToProject(IProject project, IWorkspace workspace) {
        IUser projectOwner = project.getOwner();
        if (projectOwner.getEmail() != null && !projectOwner.getEmail().equals("")) {
            // Build the message to be sent to the user
            StringBuilder message = new StringBuilder(emailMessages.getProperty("email.new_workspace_msg_part1"));
            message.append(project.getProjectName() + ".");
            message.append(emailMessages.getProperty("email.new_workspace_msg_part2"));
            message.append(emailMessages.getProperty("email.new_workspace_msg_part3"));
            message.append(workspace.getWorkspaceName());
            message.append(emailMessages.getProperty("email.new_workspace_msg_part4"));
            message.append(workspace.getDescription());
            message.append(emailMessages.getProperty("email.new_workspace_msg_part5"));
            message.append(workspace.getOwner().getUserName());

            emailSender.sendNotificationEmail(projectOwner.getEmail(),
                    emailMessages.getProperty("email.new_workspace_subject"), message.toString());
            logger.info(
                    "The system sent an email notification to <<" + projectOwner.getEmail() + ">> for the workspace <"
                            + workspace.getWorkspaceName() + "> added by <" + workspace.getOwner().getUserName() + ">");
        }
    }

    @Override
    public void sendAccountCreatedEmail(String name, String username, String adminName, String adminEmail)
            throws QuadrigaNotificationException {
        Map<String, Object> contextProperties = new HashMap<String, Object>();

        contextProperties.put("createdUser", name);
        contextProperties.put("createdUsername", username);
        contextProperties.put("admin", adminName);

        try {
            String msg = velocityBuilder.getRenderedTemplate("velocitytemplates/email/newAccount.vm",
                    contextProperties);
            emailSender.sendNotificationEmail(adminEmail, emailMessages.getProperty("email.account_created.subject"),
                    msg);
        } catch (ResourceNotFoundException e) {
            throw new QuadrigaNotificationException(e);
        } catch (ParseErrorException e) {
            throw new QuadrigaNotificationException(e);
        } catch (Exception e) {
            throw new QuadrigaNotificationException(e);
        }
    }

    @Override
    public void sendAccountProcessedEmail(IUser user, boolean approved) throws QuadrigaNotificationException {
        Map<String, Object> contextProperties = new HashMap<String, Object>();
        contextProperties.put("user", user.getName());
        contextProperties.put("username", user.getUserName());

        String msg;
        String subject;
        try {
            if (approved) {
                msg = velocityBuilder.getRenderedTemplate("velocitytemplates/email/accountApproved.vm",
                        contextProperties);
                subject = emailMessages.getProperty("email.account_approved.subject");
            } else {
                msg = velocityBuilder.getRenderedTemplate("velocitytemplates/email/accountRejected.vm",
                        contextProperties);
                subject = emailMessages.getProperty("email.account_rejected.subject");
            }
            emailSender.sendNotificationEmail(user.getEmail(), subject, msg);
        } catch (Exception e) {
            // this method actually throws simply 'Exception'
            throw new QuadrigaNotificationException(e);
        }
    }

}
