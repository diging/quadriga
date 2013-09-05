package edu.asu.spring.quadriga.web.conceptcollection;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.sevice.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormDeleteValidator;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class CCCollaboratorDeleteController {
	
	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private IConceptCollectionFactory collectionFactory;
	
	@Autowired
	IConceptCollectionManager conceptControllerManager;
	
	@Autowired
	CollaboratorValidator collaboratorValidator;
	
	private IConceptCollection concept;
	
	@Autowired
	private IUserManager usermanager;
	
	@Autowired
	private ModifyCollaboratorFormManager collaboratorFormManager;
	
	@Autowired
	private IConceptCollectionManager collectionManager;
	
	@Autowired
	private CollaboratorFormDeleteValidator validator;
	
	
	@InitBinder
	protected void initBinder (HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder){
		
		validateBinder.setValidator(validator);
		
	}
	
	@RequestMapping(value="auth/conceptcollections/{collection_id}/showDeleteCollaborators", method=RequestMethod.GET)
	public String displayCollaborator(@PathVariable("collection_id") String collectionid, ModelMap model, Principal principal) throws QuadrigaAccessException, QuadrigaStorageException
	{
		
		List<IUser> nonCollaboratorList = conceptControllerManager.showNonCollaboratingUsers(collectionid);	
		model.addAttribute("nonCollaboratorList", nonCollaboratorList);
		
		concept = collectionFactory.createConceptCollectionObject();
		concept.setId(collectionid);		
		conceptControllerManager.getCollectionDetails(concept,principal.getName());
		
		model.addAttribute("collectionid", collectionid);
		
		ModifyCollaboratorForm modifyCollaboratorForm = new ModifyCollaboratorForm();
		model.addAttribute("collaboratorForm", modifyCollaboratorForm);
		
		List<ICollaborator> collaboratorList =  conceptControllerManager.showCollaboratingUsers(collectionid);
		model.addAttribute("collaboratingUsers", collaboratorList);

		
		return "auth/conceptcollection/showDeleteCollaborators";
	}
	
	/**
	 * @description deletes the collaborator from current conceptcollection
	 * @param collectionid   id of the conceptcollection
	 * @param model
	 * @param req	contains string array returned by jsp
	 * @return String having path for showcollaborators jsp page
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value="auth/conceptcollections/{collection_id}/deleteCollaborator", method = RequestMethod.POST)
	public ModelAndView deleteCollaborators(@PathVariable("collection_id") String collectionid,
	@Validated @ModelAttribute("collaboratorForm") ModifyCollaboratorForm collaboratorForm,BindingResult result)throws QuadrigaStorageException
	{
		
		ModelAndView modelAndView = new ModelAndView("auth/conceptcollection/showDeleteCollaborators");
		String errmsg = null;
		
		List<ModifyCollaborator> collaborators = collaboratorForm.getCollaborators();
		
		if(result.hasErrors())
		{
			List<ModifyCollaborator> collaborators2 = collaboratorFormManager.modifyConceptCollectionCollaboratorManager(collectionid);
			collaboratorForm.setCollaborators(collaborators2);
			modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
			modelAndView.getModelMap().put("collectionid", collectionid);
		}
		else
		{
			for(ModifyCollaborator collaborator:collaborators)
			{
				errmsg = collectionManager.deleteCollaborators(collaborator.getUserName(), collectionid);
			}
			
			List<ModifyCollaborator> collaborators2 = collaboratorFormManager.modifyConceptCollectionCollaboratorManager(collectionid);
			collaboratorForm.setCollaborators(collaborators2);
			modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
			modelAndView.getModelMap().put("collectionid", collectionid);
			
			List<ICollaborator> collaboratorList =  conceptControllerManager.showCollaboratingUsers(collectionid);
			for(ICollaborator collaborator:collaboratorList)
			{
				System.out.println("----------collaborator "+ collaborator.getUserObj().getUserName());
			}
			modelAndView.getModelMap().put("collaboratingUsers", collaboratorList);
		}
			
		return modelAndView;
	}
	

}
