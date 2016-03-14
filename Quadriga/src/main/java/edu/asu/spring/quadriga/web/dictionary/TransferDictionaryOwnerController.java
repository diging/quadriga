package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
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
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.validator.UserValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class TransferDictionaryOwnerController
{
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private IDictionaryManager dictionaryManager;
	
	@Autowired
	private IDictionaryCollaboratorManager dictCollabManager;
	
	@Autowired
	private IQuadrigaRoleManager roleManager;
	
	@Autowired
	private UserValidator validator;
	
	@InitBinder("user")
	protected void initBinder(WebDataBinder validateBinder){
		validateBinder.setValidator(validator);
	}
	
	/**
	 * This method retrieves the collaborators associated for given dictionary to transfer the
	 * ownership to one of the collaborators
	 * @param dictionaryid
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY,paramIndex = 1, userRole = {} )})
	@RequestMapping(value="auth/dictionaries/transfer/{dictionaryid}", method = RequestMethod.GET)
	public ModelAndView transferDictionaryOwner(@PathVariable("dictionaryid") String dictionaryid) throws QuadrigaStorageException, QuadrigaAccessException
	{
		
		ModelAndView model = new ModelAndView("auth/dictionaries/changedictionaryowner");
		
		IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryid);
		
		//create a model
	    model.getModelMap().put("user", userFactory.createUserObject());
		model.getModelMap().put("dictionaryname", dictionary.getDictionaryName());
		model.getModelMap().put("dictionaryowner", dictionary.getOwner().getUserName());
		model.getModelMap().put("dictionaryid", dictionaryid);
		
		//fetch the collaborators
		List<IDictionaryCollaborator> collaboratingUsers = dictionaryManager.showCollaboratingUsers(dictionaryid);
		List<IUser> userList = new ArrayList<IUser>();
        
		if (collaboratingUsers != null) {
    		for(IDictionaryCollaborator collabuser : collaboratingUsers) {
    			//userList.add(collabuser);
    			
    			userList.add(collabuser.getCollaborator().getUserObj());
    		}
		}
		
		model.getModelMap().put("collaboratinguser", userList);
		
		//create model attribute
		model.getModelMap().put("success", 0);
		
		return model;
	}
	
	/**
	 * This method transfer the ownership of the dictionary to the selected collaborator
	 * and assign the old owner as collaborator to the dictionary
	 * @param dictionaryid
	 * @param principal
	 * @param collaboratorUser
	 * @param result
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaException 
	 * @throws JSONException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY,paramIndex = 1, userRole = {} )})
	@RequestMapping(value="auth/dictionaries/changedictionaryowner/{dictionaryid}", method = RequestMethod.POST)
    public String transferDictionaryOwner(@PathVariable("dictionaryid") String dictionaryid,Principal principal, ModelMap model,
			@Validated @ModelAttribute("user")User collaboratorUser, BindingResult result) throws QuadrigaStorageException, QuadrigaException, JSONException, QuadrigaAccessException
	{
		
		
		List<IDictionaryCollaborator> collaboratingUser = new ArrayList<IDictionaryCollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		
		String userName = principal.getName();
		
		if(result.hasErrors()) {
			model.put("success", 0);
		} else {
			//fetch the new owner
		    String newOwner = collaboratorUser.getUserName();
        	
		    String collaboratorRole = roleManager.getQuadrigaRoleById(IQuadrigaRoleManager.DICT_ROLES, RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN).getDBid();
        	
			//call the method to transfer the ownership
        	dictCollabManager.transferOwnership(dictionaryid, userName, newOwner, collaboratorRole);
			
			model.put("success", 1);
			model.addAttribute("show_success_alert", true);
			model.addAttribute("success_alert_msg", "Ownership successfully transferred.");
		}
		
		IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryid);
        model.put("dictionaryid",dictionaryid);
        
        List<IDictionaryItems> dictionaryItemList = dictionaryManager
                .getDictionariesItems(dictionaryid,userName);
        
		model.addAttribute("dictionaryItemList", dictionaryItemList);
        model.addAttribute("dictionary", dictionary);
        
        String jsonTreeData = dictionaryManager.getProjectsTree(userName, dictionaryid);
        model.addAttribute("core", jsonTreeData);

		//fetch the collaborators
        collaboratingUser = dictionaryManager.showCollaboratingUsers(dictionaryid);
        
        for(IDictionaryCollaborator collabuser : collaboratingUser)
        {
            userList.add(collabuser.getCollaborator().getUserObj());
        }
        
        model.put("collaboratingUsers", collaboratingUser);
        model.addAttribute("owner", dictionary.getOwner().getUserName().equals(userName));
		
		return "auth/dictionary/dictionary";
	}
}
