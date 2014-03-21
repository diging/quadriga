package edu.asu.spring.quadriga.web.workbench;


import java.security.Principal;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.impl.workbench.RetrieveJsonProjectsManager;
import edu.asu.spring.quadriga.service.workbench.ICheckProjectSecurity;
import edu.asu.spring.quadriga.service.workbench.IRetrieveJsonProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;


@Controller
public class RetrieveProjectController 
{
	private static final Logger logger = LoggerFactory
			.getLogger(RetrieveProjectController.class);
	
	@Autowired 
	IRetrieveProjectManager projectManager;
	
	@Autowired 
	IRetrieveJsonProjectManager jsonProjectManager;
	
	@Autowired
	ICheckProjectSecurity projectSecurity;
	
	@Autowired
	IListWSManager wsManager;
	
	public IListWSManager getWsManager() {
		return wsManager;
	}

	public void setWsManager(IListWSManager wsManager) {
		this.wsManager = wsManager;
	}

	public IRetrieveProjectManager getProjectManager(){
		return projectManager;
		
	}
	
	public void setProjectManager(IRetrieveProjectManager projectManager){
		
		this.projectManager = projectManager;
	}
	
	public void setProjectSecurity(ICheckProjectSecurity projectSecurity){
		
		this.projectSecurity = projectSecurity;
	}
	
	/**
	 *this method acts as a controller for handling all the activities on the workbench
	 *home page 
	 * @param 	model maps projectlist to view (jsp page) 
	 * @param   principal
	 * @return 	string for workbench url 
	 * @throws  QuadrigaStorageException
	 * @author 		rohit sukleshwar pendbhaje
	 * @throws JSONException 
	 */
	@RequestMapping(value="auth/workbench", method = RequestMethod.GET)
	public ModelAndView getProjectList(Principal principal) throws QuadrigaStorageException, JSONException
	{
		String userName;
		ModelAndView model;
		List<IProject> projectListAsOwner;
		List<IProject> projectListAsCollaborator;
		List<IProject> projectListAsWorkspaceOwner;
		List<IProject> projectListAsWSCollaborator;
		String projectListAsOwnerJson ;
		String allProjectListJson ;
		String projectListAsCollaboratorJson ;
		String projectListAsWorkspaceOwnerJson ;
		String projectListAsWSCollaboratorJson ;
		
		
		userName = principal.getName();
		
		//Fetch all the projects for which the user is owner
		projectListAsOwner = projectManager.getProjectList(userName);
		
		//Fetch all the projects for which the user is collaborator
		projectListAsCollaborator = projectManager.getCollaboratorProjectList(userName);
		
		//Fetch all the projects for which the user is associated workspace owner
		projectListAsWorkspaceOwner = projectManager.getProjectListAsWorkspaceOwner(userName);
		
		//Fetch all the projects for which the user is associated workspace collaborator
		projectListAsWSCollaborator = projectManager.getProjectListAsWorkspaceCollaborator(userName);
		
		projectListAsOwnerJson = jsonProjectManager.getProjectList(userName);
		
		allProjectListJson = jsonProjectManager.getAllProjects(userName);
		
		projectListAsCollaboratorJson = jsonProjectManager.getCollaboratorProjectList(userName);
		
		projectListAsWorkspaceOwnerJson = jsonProjectManager.getProjectListAsWorkspaceOwner(userName);
		
		projectListAsWSCollaboratorJson = jsonProjectManager.getProjectListAsWorkspaceCollaborator(userName);
		
		logger.info(projectListAsOwnerJson);
		
		model = new ModelAndView("auth/workbench");
		model.getModelMap().put("projectlistasowner", projectListAsOwner);
        model.getModelMap().put("projectlistascollaborator", projectListAsCollaborator);
        model.getModelMap().put("projectlistaswsowner", projectListAsWorkspaceOwner);
        model.getModelMap().put("projectlistaswscollaborator", projectListAsWSCollaborator);
        
        model.getModelMap().put("owner", projectListAsOwnerJson);
        model.getModelMap().put("allprojects", allProjectListJson);
        model.getModelMap().put("collaborator", projectListAsCollaboratorJson);
        model.getModelMap().put("wsowner", projectListAsWorkspaceOwnerJson);
        model.getModelMap().put("wscollaborator", projectListAsWSCollaboratorJson);
		
		return model;
	}
	
	@RequestMapping(value="auth/workbench/{projectid}", method = RequestMethod.GET)
	public ModelAndView getProjectDetails(@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model;
		String userName;
		IProject project;
		List<IWorkSpace> workspaceList;
		List<IWorkSpace> collaboratorWorkspaceList;
		
		model = new ModelAndView("auth/workbench/project");
		
		userName = principal.getName();
		project = projectManager.getProjectDetails(projectid);
		
		//retrieve all the workspaces associated with the project
		workspaceList = wsManager.listActiveWorkspace(projectid,userName);
		collaboratorWorkspaceList = wsManager.listActiveWorkspaceByCollaborator(projectid, userName);
		
		model.getModelMap().put("project", project);
		model.getModelMap().put("workspaceList",workspaceList);
		model.getModelMap().put("collabworkspacelist", collaboratorWorkspaceList);
		if(projectSecurity.checkProjectOwner(userName,projectid)){
			model.getModelMap().put("owner", 1);
		}else{
			model.getModelMap().put("owner", 0);
		}
		if(projectSecurity.checkProjectOwnerEditorAccess(userName, projectid)){
			model.getModelMap().put("editoraccess", 1);
		}else{
			model.getModelMap().put("editoraccess", 0);
		}
		return model;
	}
	
	/*@RequestMapping(value="sites/{ProjectUnixName}", method=RequestMethod.GET)
	public String showProject(@PathVariable("ProjectUnixName") String unixName,Model model) throws QuadrigaStorageException {
		
		
		
		IProject project = projectManager.getProjectDetailsByUnixName(unixName);
		if(project!=null){
			
			model.addAttribute("project", project);
			return "website";
		}
		else
			return "forbidden";
		
		
	}*/
}
