package edu.asu.spring.quadriga.web.conceptcollection;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.ICCCollaboratorManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormDeleteValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class DeleteCCCollaboratorController {
	
	@Autowired
	private IModifyCollaboratorFormFactory collaboratorFormFactory;
	
	@Autowired
	private ModifyCollaboratorFormManager collaboratorFormManager;
	
	@Autowired
	private ICCCollaboratorManager collaboratorManager;
	
	@Autowired
	private CollaboratorFormDeleteValidator validator;
	
	@Autowired
	private IConceptCollectionFactory collectionFactory;
	
	@Autowired
	private IConceptCollectionManager conceptControllerManager;
	
	
	@InitBinder
	protected void initBinder (WebDataBinder validateBinder)
	{
		validateBinder.setValidator(validator);
	}
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION,paramIndex = 1, userRole = {RoleNames.ROLE_CC_COLLABORATOR_ADMIN} )})
	@RequestMapping(value="auth/conceptcollections/{collectionid}/deletecollaborators", method=RequestMethod.GET)
	public ModelAndView deleteCollaborators(@PathVariable("collectionid") String collectionid,Principal principal) 
			throws QuadrigaAccessException, QuadrigaStorageException
	{
	    ModelAndView model;
	    ModifyCollaboratorForm collaboratorForm;
		
	    String userName = principal.getName();
		model = new ModelAndView("auth/conceptcollection/deletecollaborators");
		
		//fetch the concept collection details
		IConceptCollection conceptCollection = collectionFactory.createConceptCollectionObject();
		conceptCollection.setConceptCollectionId(collectionid);
		conceptControllerManager.getCollectionDetails(conceptCollection, userName);
		
		collaboratorForm = collaboratorFormFactory.createCollaboratorFormObject();
		
		List<ModifyCollaborator> modifyCollaborator = collaboratorFormManager.modifyCCCollaboratorManager(collectionid);
		
		collaboratorForm.setCollaborators(modifyCollaborator);
		
		model.getModelMap().put("collectionid", collectionid);
		model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());
		model.getModelMap().put("collectiondesc", conceptCollection.getDescription());
		model.getModelMap().put("collaboratorForm", collaboratorForm);
		model.getModelMap().put("success", 0);
		return model;
	}
	
	/**
	 * @description deletes the collaborator from current conceptcollection
	 * @param collectionid   id of the conceptcollection
	 * @param model
	 * @param req	contains string array returned by jsp
	 * @return String having path for showcollaborators jsp page
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION,paramIndex = 1, userRole = {RoleNames.ROLE_CC_COLLABORATOR_ADMIN} )})
	@RequestMapping(value="auth/conceptcollections/{collectionid}/deleteCollaborator", method = RequestMethod.POST)
	public ModelAndView deleteCollaborators(@PathVariable("collectionid") String collectionid,
	@Validated @ModelAttribute("collaboratorForm") ModifyCollaboratorForm collaboratorForm,BindingResult result,Principal principal)
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		String user;
		StringBuilder collabUser;
		List<ModifyCollaborator> collaborators;
		
		collabUser = new StringBuilder();
		model = new ModelAndView("auth/conceptcollection/deletecollaborators");
		model.getModelMap().put("collectionid", collectionid);
		
		String userName = principal.getName();
		
		if(result.hasErrors())
		{
			//fetch the concept collection details
			//fetch the concept collection details
			IConceptCollection conceptCollection = collectionFactory.createConceptCollectionObject();
			conceptCollection.setConceptCollectionId(collectionid);
			conceptControllerManager.getCollectionDetails(conceptCollection, userName);

			model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());
			model.getModelMap().put("collectiondesc", conceptCollection.getDescription());
			
			collaborators = collaboratorFormManager.modifyCCCollaboratorManager(collectionid);
			collaboratorForm.setCollaborators(collaborators);
			model.getModelMap().put("success", 0);
			model.getModelMap().put("error", 1);
			model.getModelMap().put("collaboratorForm", collaboratorForm);
		}
		else
		{
			collaborators = collaboratorForm.getCollaborators();
			
			for(ModifyCollaborator collaborator:collaborators)
			{
				System.out.println("---------collaborator name "+collaborator.getUserName());
				user = collaborator.getUserName();
				if(user!=null)
				{
					collabUser.append(",");
					collabUser.append(user);
				}
			}
			collaboratorManager.deleteCollaborators(collabUser.toString().substring(1), collectionid);
			
			model.getModelMap().put("success", 1);
		}
		return model;
	}
	

}
