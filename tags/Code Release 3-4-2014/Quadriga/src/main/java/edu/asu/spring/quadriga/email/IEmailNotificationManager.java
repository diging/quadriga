package edu.asu.spring.quadriga.email;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;

/**
 * The purpose of this interface is to manage all the outgoing mails in the system.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IEmailNotificationManager {
	
	/**
	 * Send an account deactivation email to the user.
	 * 
	 * @param user		The user object whose account is deactivated. Email field in the user object will be used to send the email.
	 * @param adminid	The username of the admin who deactivated the account of the user.
	 */
	public void sendAccountDeactivationEmail(IUser user, String adminid);

	/**
	 * Send an account activation email to the user.
	 * 
	 * @param user		The user object whose account is activated. Email field in the user object will be used to send the email.
	 * @param adminid	The username of the admin who approved the account of the user.
	 */
	public void sendAccountActivationEmail(IUser user, String adminid);
	
	/**
	 * Send a notifcation to the admin saying that a new account request has been placed.
	 * 
	 * @param admin		The user object of the admin to whom the notification is to be sent. Email field in the user object will be used to send the email.
	 * @param userid	The username of the user who placed a new account request.
	 */
	public void sendNewAccountRequestPlacementEmail(IUser admin, String userid);

	/**
	 * Send an email to the project owner when a new workspace has been added to the project.
	 * 
	 * @param project	The project object to which the new workspace was added. Email field of the project owner will be used to send the email.
	 * @param workspace	The workspace object which was added to the project.
	 */
	public void sendNewWorkspaceAddedToProject(IProject project, IWorkSpace workspace);

}
