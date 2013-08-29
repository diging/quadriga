package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.factories.IModifyProjectFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.ICheckProjectSecurity;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.ProjectFormValidator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyProject;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyProjectForm;

@Controller
public class DeleteProjectController 
{
	
	@Autowired
	private IModifyProjectFormFactory projectFormFactory; 
	
	@Autowired
	IModifyProjectManager projectManager;
	
	@Autowired
	IRetrieveProjectManager retrieveProjectManager;
	
	@Autowired
	ICheckProjectSecurity projectSecurity;
	
	@Autowired
	ProjectFormValidator validator;
	
	@InitBinder
	protected void initBinder(WebDataBinder validateBinder) throws Exception 
	{
		
		validateBinder.setValidator(validator);
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
	public ModelAndView deleteProjectRequestForm(Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model;
		String userName;
		List<IProject> projectList;
		ModifyProjectForm projectForm;
		ModifyProject project;
		List<ModifyProject> deleteProjectList = new ArrayList<ModifyProject>();
		
		//create a model
		model = new ModelAndView("auth/workbench/deleteproject");
		userName = principal.getName();
		
		projectList = retrieveProjectManager.getProjectList(userName); 
		
		for(IProject iProject : projectList)
		{
			project = new ModifyProject();
			project.setInternalid(iProject.getInternalid());
			project.setName(iProject.getName());
			project.setDescription(iProject.getDescription());
			deleteProjectList.add(project);
		}
		
		//create a project form
		projectForm = projectFormFactory.createModifyProjectForm();
		projectForm.setProjectList(deleteProjectList);
		
		//adding the model
		model.getModelMap().put("projectform", projectForm);
		model.getModelMap().put("success", 0);
		return model;
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
	public ModelAndView deleteProjectRequest(@Validated @ModelAttribute("projectform") ModifyProjectForm projectForm,
			BindingResult result) throws QuadrigaStorageException, QuadrigaAccessException
	{
		String projIdList = "";
		List<ModifyProject> deleteProjectList;
		ModelAndView model;
		
		//create a model view
		model = new ModelAndView("auth/workbench/deleteproject");
		
		if(result.hasErrors())
		{
			//add a variable to display the entire page
			model.getModelMap().put("success", 0);
			
			//add the model object
			model.getModelMap().put("projectform",projectForm);
			
		}
		else
		{
			deleteProjectList = projectForm.getProjectList();
			
			for(ModifyProject project : deleteProjectList)
			{
				projIdList = projIdList + "," + project.getInternalid();
			}
			
			projIdList = projIdList.substring(1);
			
			projectManager.deleteProjectRequest(projIdList);
			
			model.getModelMap().put("success", 1);
		}
		return model;	
	}
}
