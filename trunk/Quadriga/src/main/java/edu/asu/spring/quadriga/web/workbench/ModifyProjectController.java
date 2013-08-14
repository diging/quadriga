package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.RetrievalMethod;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.ICheckProjectSecurity;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.validator.ProjectValidator;
import edu.asu.spring.quadriga.web.StringConstants;
import edu.asu.spring.quadriga.web.rest.NetworkRestController;

@Controller
public class ModifyProjectController 
{
	@Autowired
	IModifyProjectManager projectManager;
	
	
	@Autowired
	IRetrieveProjectManager retrieveProjectManager;
	
	@Autowired 
	IProjectFactory projectFactory;
	
	@Autowired
	ICheckProjectSecurity projectSecurity;
	
	@Autowired 
	IUserManager userManager;
	
	@Autowired
	ProjectValidator validator;
	

	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	private static final Logger logger = LoggerFactory
			.getLogger(ModifyProjectController.class);

	/**
	 * Attach the custom validator to the Spring context
	 */
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {

		binder.setValidator(validator);
	}
	
	/**
	 * This method is called during the load of add project request form
	 * @param  model -  model object
	 * @return String - the URL of the form
	 * @author Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/addproject", method=RequestMethod.GET)
	public ModelAndView addProjectRequestForm()
	{
		ModelAndView model = new ModelAndView("auth/workbench/addproject");
		model.getModelMap().put("project",projectFactory.createProjectObject());
		model.getModelMap().put("unixnameurl",StringConstants.PROJECT_UNIX_NAME_URL);
		return model;
	}

	/**
	 * This method call the usermanager to insert the record in
	 * the database on form submission
	 * @param  project - object containing the form details.
	 * @param  model
	 * @param  principal
	 * @return String - the URL on success and failure
	 * @throws QuadrigaStorageException 
	 * @author Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/addproject", method = RequestMethod.POST)
	public ModelAndView addProjectRequest(@Validated @ModelAttribute("project")Project project, 
			BindingResult result,Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model;
		if(result.hasErrors())
		{
			model = new ModelAndView("auth/workbench/addproject");
			model.getModelMap().put("project",project);
			model.getModelMap().put("unixnameurl",StringConstants.PROJECT_UNIX_NAME_URL);
			return model;
		}
		else
		{
			model = new ModelAndView("auth/workbench/addProjectStatus");
			IUser user = userManager.getUserDetails(principal.getName());
            project.setOwner(user);
			
			projectManager.addProjectRequest(project);
			model.getModelMap().put("success", 1);
			return model;
		}
	}

	/**
	 *This method is called during editing a project.
	 * @param   projectid - project internal id.
	 * @param   model 
	 * @return  String - URL for project editing page.
	 * @throws  QuadrigaStorageException 
	 * @author  Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, method = RetrievalMethod.BY_ID, paramName = "projectid", userRole = { "Quadriga_Admin" } )})
	@RequestMapping(value="auth/workbench/modifyproject/{projectid}", method = RequestMethod.GET)
	public ModelAndView updateProjectRequestForm(@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		IProject project;
		String userName = principal.getName();
		boolean chkAccess;
		
		//check if the user has access to update the project
		chkAccess = projectSecurity.checkProjectAccess(userName, projectid);
		
		if(chkAccess)
		{
			model = new ModelAndView("auth/workbench/modifyproject");
			project = retrieveProjectManager.getProjectDetails(projectid);
			model.getModelMap().put("project", project);
			model.getModelMap().put("unixnameurl",StringConstants.PROJECT_UNIX_NAME_URL);
			return model;
		}
		else
		{
			throw new QuadrigaAccessException();
		}
	}
	
	/**
	 * This method is called during editing a project.
	 * @param  projectid - project internal id.
	 * @param  project - Spring Project object.
	 * @param  model 
	 * @param  principal
	 * @return String - URL for project editing page.
	 * @throws QuadrigaStorageException 
	 * @author Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@RequestMapping(value = "auth/workbench/modifyproject/{projectid}", method = RequestMethod.POST)
	public ModelAndView updateProjectRequest(@Validated @ModelAttribute("project")Project project,BindingResult result,
			@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		String userName = principal.getName();
		boolean chkAccess;
		
		//check if the user has access to update the project
		chkAccess = projectSecurity.checkProjectAccess(userName, projectid);
		
		if(chkAccess)
		{
			if(result.hasErrors())
			{
				model = new ModelAndView("auth/workbench/modifyproject");
				model.getModelMap().put("project", project);
				model.getModelMap().put("unixnameurl",StringConstants.PROJECT_UNIX_NAME_URL);
				return model;
			}
			else
			{
				project.setInternalid(projectid);
				projectManager.updateProjectRequest(project, userName);
				model = new ModelAndView("auth/workbench/modifyProjectStatus");
				model.getModelMap().put("success", 1);
				return model;
			}
		}
		else
		{
			throw new QuadrigaAccessException();
		}
	}
	
	/**
	 * This method is called during form load to display all projects.
	 * @param   model
	 * @param   principal
	 * @return  String - URL on success or failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/deleteproject", method=RequestMethod.GET)
	public String deleteProjectRequestForm(Model model,Principal principal) throws QuadrigaStorageException
	{
		String userName;
		List<IProject> projectlist;
		
		userName = principal.getName();
		
		projectlist = retrieveProjectManager.getProjectList(userName); 
		
		    //adding the project details to the model
			model.addAttribute("projectlist", projectlist);
			return "auth/workbench/deleteproject";
	}
	
	/**
	 * This method calls the user manager to delete the projects.
	 * @param    req
	 * @param    model
	 * @param    principal
	 * @return   String - URL on success and failure.
	 * @throws   QuadrigaStorageException 
	 * @author   Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@RequestMapping(value = "auth/workbench/deleteproject", method = RequestMethod.POST)
	public String deleteProjectRequest(HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		String[] values;
		String projIdList = "";
		String errmsg;
		String userName;
		boolean chkAccess;
		List<IProject> projectlist;
		
		// fetch the selected values
		values = req.getParameterValues("projchecked");
		for(String projid : values)
		{
			projIdList = projIdList + "," + projid;
		}
		//removing the first ',' value
		projIdList = projIdList.substring(1,projIdList.length());
		
		//check if the user has access to delete the rows
		userName = principal.getName();
		
		chkAccess = projectSecurity.checkProjectAccess(userName, projIdList);
		
		if(chkAccess)
		{
			errmsg = projectManager.deleteProjectRequest(projIdList);
			
			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg","Projects deleted successfully.");
				return "auth/workbench/deleteProjectStatus";
			}
			else
			{
				
				projectlist = retrieveProjectManager.getProjectList(userName); 
			
			    //adding the project details to the model
			    model.addAttribute("projectlist", projectlist);
			    model.addAttribute("success", 0);
			    model.addAttribute("errormsg", errmsg);
			    return "auth/workbench/deleteproject";
			}
		}
		else
		{
			throw new QuadrigaAccessException();
		}
	}
	
	/**
	 * This controller function would assign editor roles to project owner
	 * @param projectId
	 * @param model
	 * @param principal
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@RequestMapping(value = "auth/workbench/assignownereditor/{projectid}", method = RequestMethod.GET)
	public String assignOwnerEditorRole(@PathVariable("projectid") String projectId, ModelMap model,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		IUser user = userManager.getUserDetails(principal.getName());
		logger.info("Came in here " + projectId);
		String userName =user.getUserName();
		String msg=projectManager.assignEditorToOwner(projectId, userName);
		IProject project = retrieveProjectManager.getProjectDetails(projectId);
		
		//retrieve all the workspaces associated with the project
		List <IWorkSpace> workspaceList = wsManager.listActiveWorkspace(projectId,userName);
		if(projectSecurity.checkProjectOwner(userName, projectId)){
			model.addAttribute("owner", 1);
		}else{
			model.addAttribute("owner", 0);
		}
		if(projectSecurity.checkProjectOwnerEditorAccess(userName, projectId)){
			model.addAttribute("editoraccess", 1);
		}else{
			model.addAttribute("editoraccess", 0);
		}
		model.addAttribute("project", project);
		model.addAttribute("workspaceList",workspaceList);
		if(msg.equals("")){
			model.addAttribute("AssignEditorSuccess",1);
		}else if(msg.equals("Owner already assigned as owner")){
			model.addAttribute("AssignEditorSuccess",2);
		}else{
			logger.error("Failure " +msg);
			model.addAttribute("AssignEditorSuccess",0);
		}
		return "auth/workbench/project";
	}
}
