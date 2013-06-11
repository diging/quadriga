package edu.asu.spring.quadriga.web;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.DictionaryEntry;
import edu.asu.spring.quadriga.domain.implementation.DictionaryEntryBackupXJC;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.DictionaryManager;

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
	DictionaryItemsFactory dictionaryItemsFactory;
	
	@RequestMapping(value="auth/dictionaries", method = RequestMethod.GET)
	public String listDictionary(ModelMap model){
		try{
			UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			logger.info("came to listDictionary");
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

		logger.info("came to getDictionaryPage");
		List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionariesItems(dictionaryid);
		String dictionaryName=dictonaryManager.getDictionaryName(dictionaryid);
		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictionaryid", dictionaryid);
		return "auth/dictionary/dictionary";
	}
	
	
	@RequestMapping(value="auth/dictionaries/addDictionary", method = RequestMethod.GET)
	public ModelAndView addDictionaryForm() {
		logger.info("came to addDictionaryForm get");
		return new ModelAndView("auth/dictionaries/addDictionary", "command",dictionaryFactory.createDictionaryObject());
	}
	
	@RequestMapping(value="auth/dictionaries/addDictionary", method = RequestMethod.POST)
	public String addDictionaryHandle(@ModelAttribute("SpringWeb")Dictionary dictionary,ModelMap model, Principal principal){
		try{
			logger.info("came to addDictionaryHandle post");
			IUser user = usermanager.getUserDetails(principal.getName());
			dictionary.setOwner(user);
			
			String msg = dictonaryManager.addNewDictionary(dictionary);
			if(msg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg","Dictionary created successfully.");
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
	public ModelAndView addDictionaryItemForm(@PathVariable("dictionaryid") String dictionaryid) {
		logger.info("came to addDictionaryItemForm get" );
		return new ModelAndView("auth/dictionaries/addDictionaryItems", "command",dictionaryItemsFactory.createDictionaryItemsObject());
	}
	
	@RequestMapping(value="auth/dictionaries/addDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
	public String addDictionaryItem(@PathVariable("dictionaryid") String dictionaryId,@ModelAttribute("SpringWeb")DictionaryItems dictionaryItems,ModelMap model, Principal principal) {
		logger.info("came to addDictionaryItemForm post");
		String owner = usermanager.getUserDetails(principal.getName()).getUserName();
		String msg= dictonaryManager.addNewDictionariesItems(dictionaryId,dictionaryItems.getItems(),dictionaryItems.getId(),
				dictionaryItems.getPos(),owner);
		if(msg.equals(""))
		{
			model.addAttribute("success", 1);
			model.addAttribute("successmsg", "Item : "+dictionaryItems.getItems()+ " added successfully");
		}else{
			if(msg.equals("ItemExists")){
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", "Item : "+dictionaryItems.getItems()+" already exist for dictionary id :" +dictionaryId);
			}else{
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", msg);
			}
		}
		List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionariesItems(dictionaryId);
		String dictionaryName=dictonaryManager.getDictionaryName(dictionaryId);
		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictID", dictionaryId);
		return "auth/dictionary/dictionary";
	}
	
	@RequestMapping(value="auth/dictionaries/deleteDictionaryItems/{dictionaryid}", method = RequestMethod.GET)
	public String deleteDictionaryItem(@PathVariable("dictionaryid") String dictionaryId,@RequestParam("item") String item,ModelMap model, Principal principal) {
		String msg= dictonaryManager.deleteDictionariesItems(dictionaryId,item);
		if(msg.equals(""))
		{
			model.addAttribute("delsuccess", 1);
			model.addAttribute("delsuccessmsg", "Item : "+item+ " deleted successfully");
		}else{
			if(msg.equals("Item doesnot exists in this dictionary")){
				model.addAttribute("delsuccess", 0);
				model.addAttribute("delerrormsg", "Item : "+item+" doesn't exist for dictionary id :" +dictionaryId);
			}else{
				model.addAttribute("delsuccess", 0);
				model.addAttribute("delerrormsg", msg);
			}
		}
		logger.info("Item Returned "+item);
		List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionariesItems(dictionaryId);
		String dictionaryName=dictonaryManager.getDictionaryName(dictionaryId);
		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictID", dictionaryId);
		return "auth/dictionary/dictionary";
	}
	
	@RequestMapping(value="auth/dictionaries/updateDictionaryItems/{dictionaryid}", method = RequestMethod.GET)
	public String updateDictionaryItem(@PathVariable("dictionaryid") String dictionaryId,@RequestParam("item") String item,@RequestParam("pos") String pos,ModelMap model, Principal principal) {
		DictionaryEntry dictionaryEntry=dictonaryManager.callRestUri("http://digitalhps-develop.asu.edu:8080/wordpower/rest/WordLookup/",item,pos);
		
		String msg= dictonaryManager.updateDictionariesItems(dictionaryId,item,dictionaryEntry.getId());
		if(msg.equals(""))
		{
			model.addAttribute("updatesuccess", 1);
			model.addAttribute("updatesuccess", "Item : "+item+ " updated successfully");
		}else{
			if(msg.equals("Item doesnot exists in this dictionary")){
				model.addAttribute("updatesuccess", 0);
				model.addAttribute("updateerrormsg", "Item : "+item+" doesn't exist for dictionary id :" +dictionaryId);
			}else{
				model.addAttribute("updatesuccess", 0);
				model.addAttribute("updateerrormsg", msg);
			}
		}
		logger.info("Item Returned "+item);
		List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionariesItems(dictionaryId);
		String dictionaryName=dictonaryManager.getDictionaryName(dictionaryId);
		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictID", dictionaryId);
		return "auth/dictionary/dictionary";
	}
	
	@RequestMapping(value="auth/dictionaries/dictionary/wordSearch/{dictionaryid}", method = RequestMethod.POST)
	public String searchDictionaryItemRestHandle(@PathVariable("dictionaryid") String dictionaryid,@RequestParam("itemName") String item,@RequestParam("posdropdown") String pos, ModelMap model){
		try{
			logger.info("came to searchDictionaryItemRestHandle post");
			DictionaryEntry dictionaryEntry=dictonaryManager.callRestUri("http://digitalhps-develop.asu.edu:8080/wordpower/rest/WordLookup/",item,pos);
			model.addAttribute("status", 1);
			model.addAttribute("dictionaryEntry", dictionaryEntry);
			List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionariesItems(dictionaryid);
			String dictionaryName=dictonaryManager.getDictionaryName(dictionaryid);
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictionaryid", dictionaryid);
			if(dictionaryEntry == null){
				model.addAttribute("errorstatus", 1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "auth/dictionaries/dictionary/wordSearch";
		return "auth/dictionary/dictionary";
	}
}