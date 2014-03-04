package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.asu.spring.quadriga.domain.implementation.Profile;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceFormFactory;
import edu.asu.spring.quadriga.profile.IServiceRegistry;
import edu.asu.spring.quadriga.profile.impl.ServiceBackBean;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.IUserProfileManager;
import edu.asu.spring.quadriga.validator.ProfileValidator;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBeanForm;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBeanFormManager;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes("ServiceBackBean")
public class HomeController {
	
	@Autowired
	private IUserProfileManager profileManager;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private ProfileValidator profileValidator;
	
	@Autowired
	private IServiceRegistry serviceRegistry;
	
	@Autowired
	private ISearchResultFactory searchResultFactory;
	
	@Autowired
	private IServiceFormFactory serviceFormFactory;
	
	@Autowired
	private IUserProfileManager userProfileManager;
	
	@Autowired
	private SearchResultBackBeanForm searchResultBackBeanForm;
		
	@Autowired
	private SearchResultBackBeanFormManager backBeanFormManager;
	
	private Map<String,String> serviceNameIdMap ;
	
	@Resource(name = "contentdescriptors")
	private Properties messages;
	
	private String serviceId;
	private String term;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		
		if(binder.getTarget() instanceof Profile)
		{
			binder.setValidator(profileValidator);
		}
		
	}
	
	@ModelAttribute("ServiceBackBean")
	public ServiceBackBean getServiceBackBean() {
	return new ServiceBackBean();

	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "auth/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Principal principal) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		System.out.println("Testing principal object :"+principal.toString());
		// Get the LDAP-authenticated userid
		String sUserId = principal.getName();		
		model.addAttribute("username", sUserId);
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("wbmsg",messages.getProperty("workbench_desc"));
		model.addAttribute("conceptmsg",messages.getProperty("concept_desc"));
		model.addAttribute("dictmsg",messages.getProperty("dictonary_desc"));
		model.addAttribute("networksmsg",messages.getProperty("network_desc"));

		return "auth/home";
	}
	
	@RequestMapping(value= "auth/about",method = RequestMethod.GET)
	public String aboutQuadriga(Locale locale, Model model, Principal principal)
	{
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		// Get the LDAP-authenticated userid
		String sUserId = principal.getName();		
		model.addAttribute("username", sUserId);
		model.addAttribute("serverTime", formattedDate );
		return "auth/about";
	}
	

	/**
	 * this method used to show searchresults saved in the user's profile
	 *
	 * @param model	
	 * @param principal
	 * @return	returns the path of jsp
	 * @throws QuadrigaException
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value="auth/profile", method = RequestMethod.GET)
	public String showProfile(Model model, Principal principal) throws QuadrigaException, QuadrigaStorageException
	{
		
		List<SearchResultBackBean> resultLists = profileManager.showUserProfile(principal.getName());
		searchResultBackBeanForm.setSearchResultList(resultLists);
		model.addAttribute("success", 1);
		model.addAttribute("SearchResultBackBeanForm", searchResultBackBeanForm);
				
		if(resultLists == null)
		{
			resultLists = new ArrayList<SearchResultBackBean>();
		}
			model.addAttribute("resultLists", resultLists);
				
		return "auth/home/showProfile";
	}
	
	/**
	 * this method is used to add new searchresults in the home page of user profile
	 * @param model
	 * @param principal
	 * @return path of the jsp
	 * @throws QuadrigaStorageException
	 */
	
	@RequestMapping(value="auth/profile/addnew", method = RequestMethod.GET)
	public String showSearchForm(Model model, Principal principal, @ModelAttribute("ServiceBackBean") ServiceBackBean serviceBackBean) throws QuadrigaStorageException
	{
		
		model.addAttribute("ServiceBackBean",new ServiceBackBean());
		Map<String,String> serviceNameIdMap = serviceRegistry.getServiceNameIdMap();
		model.addAttribute("serviceNameIdMap",serviceNameIdMap);	
		model.addAttribute("SearchResultBackBeanForm", new SearchResultBackBeanForm());
		
		return "auth/home/profile";
	} 
	
	/**
	 * this method is used to add call search method of service requested by user 
	 * and retrieves those search results
	 * 
	 * @param model
	 * @param principal
	 * @param serviceBackBean
	 * @return path of the jsp page
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "auth/profile/search", method = RequestMethod.GET)
	public String search(Model model, Principal principal, @ModelAttribute("ServiceBackBean") ServiceBackBean serviceBackBean) throws QuadrigaStorageException
	{
		
		serviceId = serviceBackBean.getId();
		model.addAttribute("serviceid", serviceId);
		String term = serviceBackBean.getTerm();
		
		model.addAttribute("term", term);
		
		serviceNameIdMap = serviceRegistry.getServiceNameIdMap();

		model.addAttribute("serviceNameIdMap",serviceNameIdMap);	
		
		List<SearchResultBackBean> searchResultList = backBeanFormManager.getsearchResultBackBeanList(serviceId, term);
		
		if(!searchResultList.isEmpty()){
			
			searchResultBackBeanForm.setSearchResultList(searchResultList);
			model.addAttribute("SearchResultBackBeanForm", searchResultBackBeanForm);
			model.addAttribute("searchResultList",searchResultList);
			model.addAttribute("success", 1);
		}
		
		List<SearchResultBackBean> resultLists = profileManager.showUserProfile(principal.getName());
		
		if(resultLists == null)
		{
			resultLists = new ArrayList<SearchResultBackBean>();
		}
		
			model.addAttribute("resultLists", resultLists);
		
		return "auth/home/profile";
	}
	
	
	/**
	 * this method is used to delete the profile from user's profile home page
	 * @param searchResultBackBeanForm
	 * @param principal
	 * @param model
	 * @return path of the jsp page
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value="/auth/profile/delete",method = RequestMethod.POST)
	public String deleteSearchResult(@ModelAttribute("SearchResultBackBeanForm") SearchResultBackBeanForm searchResultBackBeanForm,  
			Principal principal, Model model) throws QuadrigaStorageException
	{
		String errmsg = null;	
		Map<String, String> serviceidnamemap = serviceRegistry.getServiceNameIdMap();
			
		String profileid = null;
		String serviceid = null;
		boolean isAtLeastOneChecked = false;
		
		List<SearchResultBackBean> backBeanList = searchResultBackBeanForm.getSearchResultList();
		
		for(SearchResultBackBean searchResultBackBean : backBeanList)
		{
			profileid = searchResultBackBean.getId();
			serviceid = profileManager.retrieveServiceId(profileid);
			
			if(searchResultBackBean.getIsChecked() == true)
			{
				isAtLeastOneChecked = true;
			    profileManager.deleteUserProfile(principal.getName(), serviceid, searchResultBackBean.getId());
			}
			
		}
		
		if(!isAtLeastOneChecked)
		{
			model.addAttribute("success", 0);
			model.addAttribute("errmsg","please select at least one record to delete" );
		}
		else
		{
			model.addAttribute("success", 1);
			List<SearchResultBackBean> listAfterDeletion = profileManager.showUserProfile(principal.getName());
			searchResultBackBeanForm.setSearchResultList(listAfterDeletion);
			model.addAttribute("SearchResultBackBeanForm", searchResultBackBeanForm);
		}
		
		
		
		return "auth/home/showProfile";
	}
	
	@RequestMapping(value = "auth/profile/{serviceid}/{term}/add", method = RequestMethod.POST)
	public String addSearchResult( @ModelAttribute("ServiceBackBean") ServiceBackBean serviceBackBean, @ModelAttribute("SearchResultBackBeanForm")  SearchResultBackBeanForm searchResultBackBeanForm, BindingResult result,
	@PathVariable("serviceid") String serviceid, @PathVariable("term") String term, Model model, Principal principal) throws QuadrigaStorageException
	{
		
		Map<String, String> serviceNameIdMap = serviceRegistry.getServiceNameIdMap();
		IService serviceObj = serviceRegistry.getServiceObject(serviceid);
		
		boolean atLeastOneTrue = false;
		int count = 0;
						
		List<SearchResultBackBean> backBeanSearchResults = searchResultBackBeanForm.getSearchResultList();
				
			for(SearchResultBackBean resultBackBean: backBeanSearchResults)
			{
				if(resultBackBean.getIsChecked() == true)
				{
					userProfileManager.addUserProfile(principal.getName(), serviceid, resultBackBean);					
					atLeastOneTrue = true;
					//count++;
				}
				//else
					//atLeastOneTrue = false;
				
				/*if(atLeastOneTrue)
				{
					userProfileManager.addUserProfile(principal.getName(), serviceid, resultBackBean);					
				}*/
			}
			
			if(!atLeastOneTrue)
			{
				model.addAttribute("ServiceBackBean", serviceBackBean);
				model.addAttribute("serviceNameIdMap", serviceRegistry.getServiceNameIdMap());
				model.addAttribute("success",2);
				model.addAttribute("errmsg", "please select some record");
				return "auth/home/profile";
			}
		
			model.addAttribute("success", 1);
			List<SearchResultBackBean> backBeans = profileManager.showUserProfile(principal.getName());
			searchResultBackBeanForm.setSearchResultList(backBeans);
			model.addAttribute("searchResultBackBeanForm", searchResultBackBeanForm);
			
		return "auth/home/showProfile";
	}

	

}
