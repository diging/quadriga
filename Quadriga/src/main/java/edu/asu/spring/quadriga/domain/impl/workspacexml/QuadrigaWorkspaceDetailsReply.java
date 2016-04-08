package edu.asu.spring.quadriga.domain.impl.workspacexml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that would contains the response data for individual
 * Workspaces.
 * @author Lohith Dwaraka
 *
 */
@XmlRootElement(name="QuadrigaReply", namespace="http://www.digitalhps.org/Quadriga")
public class QuadrigaWorkspaceDetailsReply {

	private WorkspacesList workspacesList;

	public WorkspacesList getWorkspacesList() {
		return workspacesList;
	}

	@XmlElement(namespace="http://www.digitalhps.org/Quadriga")
	public void setWorkspacesList(WorkspacesList workspacesList) {
		this.workspacesList = workspacesList;
	}
}
