package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.impl.dictionary.Dictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.validator.DictionaryValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ModifyDictionaryManagerController 
{
	@Autowired
	private IDictionaryManager dictManager;
	
	@Autowired
	private DictionaryValidator validator;
	

	private static final Logger logger = LoggerFactory
			.getLogger(ModifyDictionaryManagerController.class);
	
	@InitBinder
	protected void initBinder(WebDataBinder validateBinder){
		validateBinder.setValidator(validator);
	}
	
	/**
	 * This method retrieves the dictionary details for updation
	 * @param dictionaryid
	 * @param principal
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY,paramIndex = 1, userRole = {RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN} )})
	@RequestMapping(value="auth/dictionaries/updatedictionary/{dictionaryid}", method = RequestMethod.GET)
     public ModelAndView updateDictionaryDetails(@PathVariable("dictionaryid") String dictionaryid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
     {
    	 ModelAndView model;
    	 IDictionary dictionary;
    	 
    	 //create a model
    	 model = new ModelAndView("auth/dictionaries/updatedictionary");
    	 dictionary = dictManager.getDictionaryDetails(dictionaryid);
    	 model.getModelMap().put("dictionary",dictionary);
    	 model.getModelMap().put("dictionaryid", dictionaryid);
    	 model.getModelMap().put("success", 0);
    	 return model;
     }
	
	/**
	 * This method updates the dictionary details associated with the specified dictioanry id
	 * @param dictionary
	 * @param result
	 * @param dictionaryid
	 * @param principal
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY,paramIndex = 3, userRole = {RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN} )})
	@RequestMapping(value="auth/dictionaries/updatedictionary/{dictionaryid}", method = RequestMethod.POST)
	public ModelAndView updateDictionaryDetails(@Validated @ModelAttribute("dictionary")Dictionary dictionary,BindingResult result,
			@PathVariable("dictionaryid") String dictionaryid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		String userName = principal.getName();
		
		model = new ModelAndView("auth/dictionaries/updatedictionary");
		model.getModelMap().put("dictionaryid", dictionaryid);
		
		logger.info("came here");
		if(result.hasErrors())
		{
		 	 model.getModelMap().put("dictionary",dictionary);
	    	 model.getModelMap().put("success", 0);
		}
		else
		{
			 dictionary.setDictionaryId(dictionaryid);
			 dictManager.updateDictionaryDetailsRequest(dictionary, userName);
	    	 model.getModelMap().put("success", 1);
		}
		
		return model;
	}
}
