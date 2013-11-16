package edu.asu.spring.quadriga.email;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;

public interface IEmailNotificationManager {
	
	public void sendAccountDeactivationEmail(IUser user, String adminid);

	public void sendAccountActivationEmail(IUser user, String adminid);
	
	public void sendNewAccountRequestPlacementEmail(IUser admin, String userid);

	public void sendNewWorkspaceAddedToProject(IProject project, IWorkSpace workspace);

}
