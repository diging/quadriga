package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
import edu.asu.spring.quadriga.exceptions.QuadrigaExceptionHandler;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;


/**
 * This class will handle all dictionaries components 
 * 				  controller for the dictionary tab 
 * 
 * @author 		: Lohith Dwaraka
 * 
 */

@Controller
public class DictionaryController {
	@Autowired 
	IDictionaryManager dictonaryManager;


	private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

	QuadrigaExceptionHandler quadExcepHandler = new QuadrigaExceptionHandler();

	@Autowired 
	IUserManager usermanager;

	public IUserManager getUsermanager() {
		return usermanager;
	}

	public void setUsermanager(IUserManager usermanager) {
		this.usermanager = usermanager;
	}
	
	@Autowired 
	IDictionaryFactory dictionaryFactory;

	@Autowired 
	DictionaryItemsFactory dictionaryItemsFactory;

	/**
	 * Admin can use this page to check the list of dictionary he is accessible to 
	 * 
	 * @return 	Return to the dictionary home page of the Quadriga
	 */

	@RequestMapping(value="auth/dictionaries", method = RequestMethod.GET)
	public String listDictionary(ModelMap model){
		try{
			UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			logger.info("came to listDictionary");
			String userId = user.getUsername();
			List<IDictionary> dictionaryList=null;
			try{
				dictionaryList = dictonaryManager.getDictionariesList(user.getUsername());
			}catch(QuadrigaStorageException e){
				throw new QuadrigaStorageException();
			}
			model.addAttribute("dictinarylist", dictionaryList);
			model.addAttribute("userId", userId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "auth/dictionaries"; 
	}

	/**
	 *  Admin can use this page to check the list of dictionary items in a dictionary 
	 *  and to search and add items from the word power  
	 * 
	 * @return 	Return to the list dictionary items page of the Quadriga
	 * @throws QuadrigaStorageException 
	 */

	@RequestMapping(value="auth/dictionaries/{dictionaryid}", method = RequestMethod.GET)
	public String getDictionaryPage(@PathVariable("dictionaryid") String dictionaryid, ModelMap model) throws QuadrigaStorageException {
		try{
			logger.info("came to getDictionaryPage");
			List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionariesItems(dictionaryid);
			if(dictionaryItemList == null){
				logger.info("Dictionary ITem list is null");
			}
			String dictionaryName="";
			try{
				dictionaryName=dictonaryManager.getDictionaryName(dictionaryid);
			}catch(QuadrigaStorageException e){
				throw new QuadrigaStorageException();
			}
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictionaryid", dictionaryid);
		}catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "auth/dictionary/dictionary";
	}

	/**
	 *  Handles the bean mapping from form tag 
	 *  
	 * 
	 * @return 	Used to handle the data from the form:form tag and map it to Dicitonary object
	 */

	//	@RequestMapping(value="auth/dictionaries/addDictionary", method = RequestMethod.GET)
	//	public ModelAndView addDictionaryForm() {
	//		logger.info("came to addDictionaryForm get");
	//		return new ModelAndView("auth/dictionaries/addDictionary", "command",dictionaryFactory.createDictionaryObject());
	//	}

	@RequestMapping(value="auth/dictionaries/addDictionary", method = RequestMethod.GET)
	public String addDictionaryForm(Model m) {
		logger.info("came to addDictionaryForm get");
		m.addAttribute("dictionary",dictionaryFactory.createDictionaryObject());
		return "auth/dictionaries/addDictionary";
		//return new ModelAndView("auth/dictionaries/addDictionary", "command",dictionaryFactory.createDictionaryObject());
	}

	/**
	 *  Admin can use this page to new dictionary to his list  
	 * 
	 * @return 	Return to the add dictionary status page
	 * @throws QuadrigaStorageException 
	 */

	@RequestMapping(value="auth/dictionaries/addDictionary", method = RequestMethod.POST)
	public String addDictionaryHandle(@ModelAttribute("SpringWeb")Dictionary dictionary,ModelMap model, Principal principal) throws QuadrigaStorageException{
		logger.info("came to addDictionaryHandle post");
		IUser user = usermanager.getUserDetails(principal.getName());
		dictionary.setOwner(user);

		String msg="";
		try {
			msg = dictonaryManager.addNewDictionary(dictionary);
		} catch (QuadrigaStorageException e1) {
			// TODO Auto-generated catch block
			msg="DB Error";
			e1.printStackTrace();

		}
		if(msg.equals(""))
		{
			model.addAttribute("adddicsuccess", 1);
			model.addAttribute("adddicsuccessMsg","Dictionary created successfully.");				
			List<IDictionary> dictionaryList=null;
			try{
				dictionaryList = dictonaryManager.getDictionariesList(user.getUserName());
			}catch(QuadrigaStorageException e){
				throw new QuadrigaStorageException();
			}
			model.addAttribute("dictinarylist", dictionaryList);
			model.addAttribute("userId", user.getUserName());
			return "auth/dictionaries"; 
		}else{
			model.addAttribute("dictionary", dictionary);
			model.addAttribute("success", 0);
			model.addAttribute("errormsg", msg);
			return "auth/dictionaries/addDictionary"; 
		} 
	}

	/**
	 *  Handles the add dictionary item page 
	 * 
	 * @return 	Return to the adddictionaryitems JSP
	 */
	@RequestMapping(value="auth/dictionaries/addDictionaryItems/{dictionaryid}", method = RequestMethod.GET)
	public String addDictionaryPage(@PathVariable("dictionaryid") String dictionaryid, ModelMap model) {

		logger.info("came to addDictionaryPage");

		model.addAttribute("dictionaryid", dictionaryid);
		return "auth/dictionaries/addDictionaryItems";
	}

	/**
	 *  Handles the form tag for add dictionary item to dictionary
	 * 
	 * @return 	Return to the POST part of add dictionary item in controller
	 */

	@RequestMapping(value="auth/dictionaries/addDictionaryItems", method = RequestMethod.GET)
	public ModelAndView addDictionaryItemForm(@PathVariable("dictionaryid") String dictionaryid) {
		logger.info("came to addDictionaryItemForm get" );
		return new ModelAndView("auth/dictionaries/addDictionaryItems", "command",dictionaryItemsFactory.createDictionaryItemsObject());
	}

	/**
	 *  Handles the form tag for add dictionary item to dictionary
	 * 
	 *  @return 	Return to list dictionary item page
	 */

	@RequestMapping(value="auth/dictionaries/addDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
	public String addDictionaryItem(@PathVariable("dictionaryid") String dictionaryId,@ModelAttribute("SpringWeb")DictionaryItems dictionaryItems,ModelMap model, Principal principal) throws QuadrigaStorageException{
		String msg="";
		logger.info("came to addDictionaryItemForm post");
		String owner = usermanager.getUserDetails(principal.getName()).getUserName();
		logger.info("items : "+dictionaryItems.getItems()+" , id: "+dictionaryItems.getId()+"pos"+
				dictionaryItems.getPos());
		try{
			msg= dictonaryManager.addNewDictionariesItems(dictionaryId,dictionaryItems.getItems(),dictionaryItems.getId(),
					dictionaryItems.getPos(),owner);
		}catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException("Oops the DB is an hard hangover, please try later");
		}
		try{
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
		}catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "auth/dictionary/dictionary";
	}

	/**
	 *  Admin can use this to delete a dictionary item to dictionary
	 * 
	 *  @return 	Return to list dictionary item page
	 * @throws QuadrigaStorageException 
	 */

	@RequestMapping(value="auth/dictionaries/deleteDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
	public String deleteDictionaryItem(HttpServletRequest req,@PathVariable("dictionaryid") String dictionaryId,ModelMap model, Principal principal) throws QuadrigaStorageException {
		try{
			String[] values= req.getParameterValues("selected");
			String msg ="";
			String errormsg="";
			int flag=0;
			if(values!=null){
				for(int i=0;i<values.length;i++){
					logger.info("Deleting item for dictionary id: "+ dictionaryId+" and term id : "+i+" : "+values[i]);
					msg= dictonaryManager.deleteDictionariesItems(dictionaryId,values[i]);
					if(msg.equals("")){

					}else{
						flag=1;
						errormsg=msg;
					}

				}
			}else{
				flag=2;
			}

			if(flag==0)
			{
				model.addAttribute("delsuccess", 1);
				model.addAttribute("delsuccessmsg", "Items  deleted successfully");
			}else if(flag==1){
				if(errormsg.equals("Item doesnot exists in this dictionary")){
					model.addAttribute("delsuccess", 0);
					model.addAttribute("delerrormsg", "Items doesn't exist for dictionary id :" +dictionaryId);
				}else{
					model.addAttribute("delsuccess", 0);
					model.addAttribute("delerrormsg", errormsg);
				}
			}else{

			}
			logger.info("Item Returned ");
			List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionariesItems(dictionaryId);
			String dictionaryName=dictonaryManager.getDictionaryName(dictionaryId);
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictID", dictionaryId);
		}catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "auth/dictionary/dictionary";
	}

	/**
	 *  Admin can use this to update a dictionary item's item to dictionary
	 * 
	 *  @return 	Return to list dictionary item page
	 */

	@RequestMapping(value="auth/dictionaries/updateDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
	public String updateDictionaryItem(HttpServletRequest req,@PathVariable("dictionaryid") String dictionaryId,ModelMap model, Principal principal) throws QuadrigaStorageException {
		//DictionaryEntry dictionaryEntry=dictonaryManager.callRestUri("http://digitalhps-develop.asu.edu:8080/wordpower/rest/Word/",item,pos);
		try{
			//String msg= dictonaryManager.updateDictionariesItems(dictionaryId,item,dictionaryEntry.getId());
			String[] values= req.getParameterValues("selected");
			String msg="";
			String errormsg="";
			int flag=0;

			if(values!=null){
				for(int i=0;i<values.length;i++){
					logger.info("Value "+i+" : "+values[i]);
					DictionaryEntry dictionaryEntry=dictonaryManager.getUpdateFromWordPower(dictionaryId,values[i]);
					msg= dictonaryManager.updateDictionariesItems(dictionaryId,values[i],dictionaryEntry.getLemma(),dictionaryEntry.getPos());
					if(msg.equals("")){

					}else{
						flag=1;
						errormsg=msg;
					}
				}
			}else{
				flag=2;
			}

			if(flag==0)
			{
				logger.info("Successfully updated");
				model.addAttribute("updatesuccess", 1);
				model.addAttribute("updatesuccessmsg", "Items updated successfully");
			}else if(flag==1){
				logger.info("Please check :  errormsg");
				if(errormsg.equals("Item doesnot exists in this dictionary")){
					model.addAttribute("updatesuccess", 0);
					model.addAttribute("updateerrormsg", "Items doesn't exist for dictionary id :" +dictionaryId);
				}else{
					model.addAttribute("updatesuccess", 0);
					model.addAttribute("updateerrormsg", errormsg);
				}
			}else{

			}
			logger.info("Item Returned ");
			List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionariesItems(dictionaryId);
			String dictionaryName=dictonaryManager.getDictionaryName(dictionaryId);
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictID", dictionaryId);
		}catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "auth/dictionary/dictionary";
	}
	/**
	 *  Admin can use this to search from term and pos from word power
	 * 
	 *  @return 	Return to list dictionary item page
	 * @throws QuadrigaStorageException 
	 */

	@RequestMapping(value="auth/dictionaries/dictionary/wordSearch/{dictionaryid}", method = RequestMethod.POST)
	public String searchDictionaryItemRestHandle(@PathVariable("dictionaryid") String dictionaryid,@RequestParam("itemName") String item,@RequestParam("posdropdown") String pos, ModelMap model) throws QuadrigaStorageException{
		try{
			logger.info("came to searchDictionaryItemRestHandle post");
			DictionaryEntry dictionaryEntry=null;
			if(!item.equals("")){
				logger.info("Query for Item :" +item+" and pos :"+ pos);
				dictionaryEntry=dictonaryManager.searchWordPower(item,pos);
			}
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

		}catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}catch(Exception e){
			e.printStackTrace();
		}
		//return "auth/dictionaries/dictionary/wordSearch";
		//return "auth/dictionary/dictionary";		
		return "auth/dictionaries/addDictionaryItems";
	}

}