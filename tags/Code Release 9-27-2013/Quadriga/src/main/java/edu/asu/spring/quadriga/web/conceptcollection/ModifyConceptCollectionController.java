package edu.asu.spring.quadriga.web.conceptcollection;

import java.security.Principal;

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

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.conceptcollection.IModifyConceptCollectionManager;
import edu.asu.spring.quadriga.validator.ConceptCollectionValidator;

@Controller
public class ModifyConceptCollectionController 
{
	@Autowired
	private IConceptCollectionFactory collectionFactory;
	
	@Autowired
	private IConceptCollectionManager conceptControllerManager;
	
	@Autowired
	private IModifyConceptCollectionManager collectionManager;
	
	@Autowired
	private ConceptCollectionValidator validator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {

		binder.setValidator(validator);
	}
	
	@RequestMapping(value="auth/conceptcollections/updatecollection/{collectionid}", method = RequestMethod.GET)
	public ModelAndView updateConceptCollectionDetials(@PathVariable("collectionid") String collectionid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		IConceptCollection collection;
		String userName;
		
		userName = principal.getName();
		
		model = new ModelAndView("auth/conceptcollections/updatecollectiondetails");
		//retrieve the collection details
		collection = collectionFactory.createConceptCollectionObject();
		collection.setId(collectionid);
		conceptControllerManager.getCollectionDetails(collection,userName);
		
		model.getModelMap().put("collection", collection);
		model.getModelMap().put("success", 0);
		
		return model;
	}
	
	@RequestMapping(value="auth/conceptcollections/updatecollection/{collectionid}", method = RequestMethod.POST)
	public ModelAndView updateConceptCollectionDetails(@Validated @ModelAttribute("collection")ConceptCollection collection,BindingResult result,
			@PathVariable("collectionid") String collectionid,Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model;
		String userName;
		
		userName = principal.getName();
		
		model = new ModelAndView("auth/conceptcollections/updatecollectiondetails");
		if(result.hasErrors())
		{
			model.getModelMap().put("collection", collection);
			model.getModelMap().put("success", 0);
		}
		else
		{
			collectionManager.updateCollectionDetails(collection, userName);
			model.getModelMap().put("success", 1);
		}
		return model;
	}

}
