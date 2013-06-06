package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryFactory;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * @Description : this class will handle all dictionaries components  for the project 
 * 
 * @author 		: Lohith Dwaraka
 * 
 */

@Controller
public class DictionaryController {
	@Autowired IDictionaryManager dictonaryManager;
	private static final Logger logger = LoggerFactory.getLogger(WorkbenchController.class);
	
	@Autowired 
	IUserManager usermanager;
	
	@Autowired 
	IDictionaryFactory dictionaryFactory;
	
	@Autowired 
	IDictionaryItemsFactory dictionaryItemsFactory;
	
	@RequestMapping(value="auth/dictionaries", method = RequestMethod.GET)
	public String listDictionary(ModelMap model){
		try{
			UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			String userId = user.getUsername();
			List<IDictionary> dictionaryList;
			dictionaryList = dictonaryManager.getDictionariesList(userId);
			model.addAttribute("dictinarylist", dictionaryList);
			model.addAttribute("userId", userId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "auth/dictionaries"; 
	}
	@RequestMapping(value="auth/dictionaries/{dictionaryid}", method = RequestMethod.GET)
	public String getDictionaryPage(@PathVariable("dictionaryid") String dictionaryid, ModelMap model) {

		List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionariesItems(dictionaryid);
		String dictionaryName=dictonaryManager.getDictionaryName(dictionaryid);
		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictID", dictionaryid);
		return "auth/dictionary/dictionary";
	}
	
	
	@RequestMapping(value="auth/dictionaries/addDictionary", method = RequestMethod.GET)
	public ModelAndView addDictionaryForm() {
		return new ModelAndView("auth/dictionaries/addDictionary", "command",dictionaryFactory.createDictionaryObject());
	}
	
	@RequestMapping(value="auth/dictionaries/addDictionary", method = RequestMethod.POST)
	public String addDictionaryHandle(@ModelAttribute("SpringWeb")Dictionary dictionary,ModelMap model, Principal principal){
		try{
			
			IUser user = usermanager.getUserDetails(principal.getName());
			dictionary.setOwner(user);
			
			String msg = dictonaryManager.addNewDictionary(dictionary);
			if(msg.equals(""))
			{
				model.addAttribute("success", 1);
			}else{
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "auth/dictionaries/addDictionaryStatus"; 
	}
	
	@RequestMapping(value="auth/dictionaries/addDictionaryItems", method = RequestMethod.GET)
	public ModelAndView addDictionaryItemForm() {
		return new ModelAndView("auth/dictionaries/addDictionaryItems", "command",dictionaryItemsFactory.createDictionaryItemsObject());
	}
	
	@RequestMapping(value="auth/dictionaries/addDictionaryItems", method = RequestMethod.POST)
	public String addDictionaryItemHandle(@ModelAttribute("SpringWeb")DictionaryItems dictionaryItems,ModelMap model, Principal principal){
		try{
			
			IUser user = usermanager.getUserDetails(principal.getName());
			
			
			String msg="";
			if(msg.equals(""))
			{
				model.addAttribute("success", 1);
			}else{
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "auth/dictionaries/addDictionaryStatus"; 
	}
}