package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.implementation.Profile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceFormFactory;
import edu.asu.spring.quadriga.profile.IServiceRegistry;
import edu.asu.spring.quadriga.profile.impl.ServiceBackBean;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.IUserProfileManager;
import edu.asu.spring.quadriga.validator.ProfileValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
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
	private IService serviceA;
	
	@Autowired
	private ISearchResult searchResult;
	
	@Autowired
	private IServiceFormFactory serviceFormFactory;
	
	@Autowired
	private IUserProfileManager userProfileManager;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		
		if(binder.getTarget() instanceof Profile)
		{
			binder.setValidator(profileValidator);
		}
		
		//binder.setValidator(projectValidator);
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "auth/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Principal principal) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		// Get the LDAP-authenticated userid
		String sUserId = principal.getName();		
		model.addAttribute("username", sUserId);
		model.addAttribute("serverTime", formattedDate );

		return "auth/home";
	}
	
//	@RequestMapping(value="auth/profile", method = RequestMethod.GET)
//	public String loginProfile(Model model, Principal principal) throws QuadrigaStorageException
//	{
//		IProfile serviceUri = serviceUriFactory.createServiceUriObject();
//		model.addAttribute("serviceUri", serviceUri);
//		
//		
//		IUser user = userManager.getUserDetails(principal.getName());
//		model.addAttribute("user", user);
//		
//		List<IProfile> profileList = profileManager.showUserProfile(principal.getName());
//		model.addAttribute("profileList", profileList);
//		
//		
//
//		return "auth/home/profile";
//	}

	@RequestMapping(value="auth/profile", method = RequestMethod.GET)
	public String showSearchForm(Model model, Principal principal) throws QuadrigaStorageException
	{
		
		model.addAttribute("ServiceBackBean",new ServiceBackBean());
		
		Map<String,String> serviceNameIdMap = serviceRegistry.getServiceNameIdMap();
		
		model.addAttribute("serviceNameIdMap",serviceNameIdMap);	
		
		return "auth/home/profile";
	} 
	
	
	@RequestMapping(value = "auth/profile/search", method = RequestMethod.POST)
	public String search(Model model, Principal principal, @ModelAttribute("ServiceBackBean") ServiceBackBean serviceBackBean) throws QuadrigaStorageException
	{
		String serviceId = serviceBackBean.getId();
		String term = serviceBackBean.getTerm();
		
		IService serviceObj = serviceRegistry.getServiceObject(serviceId);
		
		List<ISearchResult> searchResults = serviceObj.search(term);
		
		model.addAttribute("searchResults", searchResults);
		
		//IProfile serviceUri = serviceUriFactory.createServiceUriObject();
		////model.addAttribute("serviceUri", serviceUri);
		
		
		return "auth/home/profile";
	}
	
	@RequestMapping(value = "auth/profile/additem", method = RequestMethod.POST)
	public String addUri(@ModelAttribute("ServiceBackBean") ServiceBackBean serviceBackBean, Model model, Principal principal) throws QuadrigaStorageException
	{
				
		/*String serviceId = serviceBackBean.getId();
		String term = serviceBackBean.getTerm();
		
		IService serviceObj = serviceRegistry.getServiceObject(serviceId);
		
		List<ISearchResult> searchresults  = serviceObj.search(term);
		
		for(ISearchResult searchResult: searchresults){
			
			String errmsg = userProfileManager.addUserProfile(principal.getName(), serviceId, searchResult.getId(), searchResult.getDescription());
			
		}*/
		
			
			//String errmsg = profileManager.addUserProfile(principal.getName(),service,uri);
			//model.addAttribute("success",0);
		
		
		return "auth/home/profile/adduri";
	}

}
