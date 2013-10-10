package edu.asu.spring.quadriga.email;

import edu.asu.spring.quadriga.domain.IUser;

public interface IEmailNotificationManager {
	
	public void sendAccountDeactivationEmail(IUser user, String adminid);

	public void sendAccountActivationEmail(IUser user, String adminid);

}
