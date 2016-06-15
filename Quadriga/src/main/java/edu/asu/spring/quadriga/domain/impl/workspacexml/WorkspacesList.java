package edu.asu.spring.quadriga.domain.impl.workspacexml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class to contain the dictionary item list from Quadriga
 * @author Ashwin Prabhu Verleker
 *
 */
@XmlRootElement(name="workspacesList", namespace="http://www.digitalhps.org/Quadriga")
public class WorkspacesList {


	private List<Workspace> workspaceList;


	
	@XmlElement(name="workspace", namespace="http://www.digitalhps.org/Quadriga")
	public List<Workspace> getWorkspaceList() {
		return workspaceList;
	}

	public void setWorkspaceList(List<Workspace> workspaceList) {
		this.workspaceList = workspaceList;
	}

	
}
