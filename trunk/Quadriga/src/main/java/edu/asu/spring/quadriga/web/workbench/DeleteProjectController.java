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

import edu.asu.spring.quadriga.domain.factories.IModifyProjectFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.ICheckProjectSecurity;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.validator.ProjectFormValidator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyProject;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyProjectForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyProjectFormManager;

@Controller
public class DeleteProjectController 
{
	
	@Autowired
	private IModifyProjectFormFactory projectFormFactory; 
	
	@Autowired
	IModifyProjectManager projectManager;
	
	@Autowired
	ICheckProjectSecurity projectSecurity;
	
	@Autowired
	ProjectFormValidator validator;
	
	@Autowired
	ModifyProjectFormManager projectFormManager;
	
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
		ModifyProjectForm projectForm;
		List<ModifyProject> deleteProjectList;
		
		//create a model
		model = new ModelAndView("auth/workbench/deleteproject");
		userName = principal.getName();
		
		//create a project form
		projectForm = projectFormFactory.createModifyProjectForm();
		
		deleteProjectList = projectFormManager.getUserProjectList(userName);
		
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
			BindingResult result,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		String projIdList = "";
		String userName;
		String projInternalId;
		List<ModifyProject> deleteProjectList;
		ModelAndView model;
		
		//create a model view
		model = new ModelAndView("auth/workbench/deleteproject");
		
        userName = principal.getName();
        
        deleteProjectList = new ArrayList<ModifyProject>();
		
		if(result.hasErrors())
		{
			deleteProjectList = projectFormManager.getUserProjectList(userName);
			
			projectForm.setProjectList(deleteProjectList);
			
			System.out.println("Error :" + result.getErrorCount());
			System.out.println("Field Error :" + result.getErrorCount());
			System.out.println("Field Error :" + result.getFieldError());
			
			//add a variable to display the entire page
			model.getModelMap().put("success", 0);
			
			//add a error variable to display the error message
			model.getModelMap().put("error", 1);
			
			//add the model object
			model.getModelMap().put("projectform",projectForm);
			
		}
		else
		{
			deleteProjectList = projectForm.getProjectList();
			
			for(ModifyProject delProject : deleteProjectList)
			{
				//fetch the internal id
				projInternalId = delProject.getInternalid();
				
				if(projInternalId !=null)
				{
				  projIdList = projIdList + "," + projInternalId;
				}
			}
			
			projIdList = projIdList.substring(1);
			
			projectManager.deleteProjectRequest(projIdList);
			
			model.getModelMap().put("success", 1);
		}
		return model;	
	}
}
