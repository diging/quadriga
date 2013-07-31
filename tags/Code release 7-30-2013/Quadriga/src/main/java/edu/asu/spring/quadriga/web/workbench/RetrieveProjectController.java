package edu.asu.spring.quadriga.web.workbench;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;


@Controller
public class RetrieveProjectController 
{
	
	@Autowired 
	IRetrieveProjectManager projectManager;
	
	@Autowired
	IListWSManager wsManager;
	
	public IRetrieveProjectManager getProjectManager(){
		return projectManager;
		
	}
	
	public void setProjectManager(IRetrieveProjectManager projectManager){
		
		this.projectManager = projectManager;
	}
	
	/**
	 *this method acts as a controller for handling all the activities on the workbench
	 *home page 
	 * @param 	model maps projectlist to view (jsp page) 
	 * @param   principal
	 * @return 	string for workbench url 
	 * @throws  QuadrigaStorageException
	 * @author 		rohit sukleshwar pendbhaje
	 */
	@RequestMapping(value="auth/workbench", method = RequestMethod.GET)
	public String getProjectList(ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String username;
		List<IProject> projectList;
		
		username = principal.getName();
		projectList = projectManager.getProjectList(username);
		
		model.addAttribute("projectlist", projectList);
		
		return "auth/workbench";
	}
	
	@RequestMapping(value="auth/workbench/{projectid}", method = RequestMethod.GET)
	public String getProjectDetails(@PathVariable("projectid") String projectid, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String userName;
		IProject project;
		List<IWorkSpace> workspaceList;
		
		userName = principal.getName();
		project = projectManager.getProjectDetails(projectid);
		
		//retrieve all the workspaces associated with the project
		workspaceList = wsManager.listActiveWorkspace(projectid,userName);
		
		model.addAttribute("project", project);
		model.addAttribute("workspaceList",workspaceList);
		
		return "auth/workbench/project";
	}
}
