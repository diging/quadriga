package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IModifyProjectFormFactory;
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
import edu.asu.spring.quadriga.web.login.RoleNames;

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
	
	@Autowired
	private IModifyProjectFormFactory projectFormFactory; 
	
	@Resource(name = "projectconstants")
	private Properties messages;
	
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
	 * @return model -  model object
	 * @author Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/addproject", method=RequestMethod.GET)
	public ModelAndView addProjectRequestForm()
	{
		logger.info("Loading add project form page");
		ModelAndView model = new ModelAndView("auth/workbench/addproject");
		model.getModelMap().put("project",projectFactory.createProjectObject());
		model.getModelMap().put("unixnameurl",messages.getProperty("project_unix_name.url"));
		model.getModelMap().put("success", 0);
		return model;
	}

	/**
	 * This method call the user manager to insert the record in
	 * the database on form submission
	 * @param  project - object containing the form details.
	 * @param  result - object containing the errors.
	 * @param  principal
	 * @return model - model object
	 * @throws QuadrigaStorageException 
	 * @author Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/addproject", method = RequestMethod.POST)
	public ModelAndView addProjectRequest(@Validated @ModelAttribute("project")Project project, 
			BindingResult result,Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model;
		model = new ModelAndView("auth/workbench/addproject");
        logger.info("Adding project details.");
		if(result.hasErrors())
		{
			logger.error("Adding project details",result);
			model.getModelMap().put("project",project);
			model.getModelMap().put("unixnameurl",messages.getProperty("project_unix_name.url"));
			model.getModelMap().put("success", 0);
		}
		else
		{
			IUser user = userManager.getUserDetails(principal.getName());
            project.setOwner(user);
			projectManager.addProjectRequest(project,principal.getName());
			model.getModelMap().put("success", 1);
		}
		return model;
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
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value="auth/workbench/modifyproject/{projectid}", method = RequestMethod.GET)
	public ModelAndView updateProjectRequestForm(@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		IProject project;
		logger.info("Updating project details");
		model = new ModelAndView("auth/workbench/modifyproject");
		project = retrieveProjectManager.getProjectDetails(projectid);
		model.getModelMap().put("project", project);
		model.getModelMap().put("unixnameurl",messages.getProperty("project_unix_name.url"));
		model.getModelMap().put("success", 0);
		return model;
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
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 3, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/modifyproject/{projectid}", method = RequestMethod.POST)
	public ModelAndView updateProjectRequest(@Validated @ModelAttribute("project")Project project,BindingResult result,
			@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		String userName = principal.getName();

		logger.info("Update project details");
		model = new ModelAndView("auth/workbench/modifyproject");
		if(result.hasErrors())
		{
			logger.error("Update project details error:",result);
			model.getModelMap().put("project", project);
			model.getModelMap().put("unixnameurl",messages.getProperty("project_unix_name.url"));
			model.getModelMap().put("success", 0);
		}
		else
		{
			projectManager.updateProjectRequest(project.getInternalid(),project.getName(),project.getDescription(),project.getProjectAccess().name(),project.getUnixName(),userName);
			model.getModelMap().put("success", 1);
		}
		return model;
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
		String userName =user.getUserName();
		projectManager.assignEditorToOwner(projectId, userName);
		IProject project = retrieveProjectManager.getProjectDetails(projectId);
		
		//retrieve all the workspaces associated with the project
		List <IWorkSpace> workspaceList = wsManager.listActiveWorkspace(projectId,userName);
		if(projectSecurity.checkProjectOwner(userName)){
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
		return "auth/workbench/project";
	}
	/**
	 * This controller function would assign editor roles to project owner
	 * @param projectId
	 * @param model
	 * @param principal
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@RequestMapping(value = "auth/workbench/deleteownereditor/{projectid}", method = RequestMethod.GET)
	public String deleteOwnerEditorRole(@PathVariable("projectid") String projectId, ModelMap model,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		IUser user = userManager.getUserDetails(principal.getName());
		String userName =user.getUserName();
		projectManager.deleteEditorToOwner(projectId, userName);
		IProject project = retrieveProjectManager.getProjectDetails(projectId);
		
		//retrieve all the workspaces associated with the project
		List <IWorkSpace> workspaceList = wsManager.listActiveWorkspace(projectId,userName);
		if(projectSecurity.checkProjectOwner(userName)){
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
		return "auth/workbench/project";
	}
}
