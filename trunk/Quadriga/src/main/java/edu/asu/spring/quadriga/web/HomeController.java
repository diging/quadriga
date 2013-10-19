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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.implementation.Profile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceFormFactory;
import edu.asu.spring.quadriga.profile.IServiceRegistry;
import edu.asu.spring.quadriga.profile.impl.SearchResult;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBeanForm;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBeanFormManager;
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
	private SearchResult searchResult;
	
	@Autowired
	private IServiceFormFactory serviceFormFactory;
	
	@Autowired
	private IUserProfileManager userProfileManager;
	
	@Autowired
	private SearchResultBackBeanForm searchResultBackBeanForm;
	
	@Autowired
	private SearchResultBackBeanFormManager backBeanFormManager;
	
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
	
	
	@RequestMapping(value = "auth/profile/search", method = RequestMethod.GET)
	public String search(Model model, Principal principal, @ModelAttribute("ServiceBackBean") ServiceBackBean serviceBackBean) throws QuadrigaStorageException
	{
		String serviceId = serviceBackBean.getId();
		model.addAttribute("serviceid", serviceId);
		String term = serviceBackBean.getTerm();
		model.addAttribute("term", term);
		
		Map<String,String> serviceNameIdMap = serviceRegistry.getServiceNameIdMap();

		model.addAttribute("serviceNameIdMap",serviceNameIdMap);	
		
		List<SearchResultBackBean> searchResultList = backBeanFormManager.getsearchResultBackBeanList(serviceId, term);
		
		if(!searchResultList.isEmpty()){
			
			searchResultBackBeanForm.setSearchResultList(searchResultList);
			model.addAttribute("SearchResultBackBeanForm", searchResultBackBeanForm);
			model.addAttribute("method", 1);
			model.addAttribute("success",0);		
		}
		
		
		return "auth/home/profile";
	}
	
	@RequestMapping(value = "auth/profile/{serviceid}/{term}/add", method = RequestMethod.POST)
	public String addUri(@ModelAttribute("SearchResultBackBeanForm") SearchResultBackBeanForm searchResultBackBeanForm, @PathVariable("serviceid") String serviceid, @PathVariable("term") String term, Model model, Principal principal) throws QuadrigaStorageException
	{
		
		String profileid;
		String description ;
		StringBuilder profilebuilder ;
		String errmsg = null;
		
		IService serviceObj = serviceRegistry.getServiceObject(serviceid);
		
		List<ISearchResult> conceptsearchResults = serviceObj.search(term);
		
		List<SearchResultBackBean> searchResults = searchResultBackBeanForm.getSearchResultList();
		
		
		
		
		for( ISearchResult conceptSearchResult: conceptsearchResults)
		{
			for(SearchResultBackBean searchResultBackBean:searchResults  )
			{	
								
				profileid = searchResultBackBean.getId();
				if(profileid!=null)
				{
					/*System.out.println("--------------profileid "+profileid);
					idbuilder.append(",");
					idbuilder.append(profileid);
*/
					
					if( searchResultBackBean.getId().equals(conceptSearchResult.getId()) )
					{
						profilebuilder = new StringBuilder();
						profileid = searchResultBackBean.getId();
						profilebuilder.append(",");
						profilebuilder.append(profileid);
						searchResultBackBean.setDescription(conceptSearchResult.getDescription());	
						description = searchResultBackBean.getDescription();
						profilebuilder.append(",");
						profilebuilder.append(description);
						errmsg = userProfileManager.addUserProfile(principal.getName(), serviceid, profilebuilder.substring(1) );

					}
				}
			}
		}
		
		if(errmsg.equals("no errors"))
		{
			model.addAttribute("success",0);		
			model.addAttribute("success",1);
		}
		
	/*	System.out.println("----------idbuilder "+idbuilder.substring(1));

		System.out.println("----------descbuilder "+descbuilder);
		userProfileManager.addUserProfile(principal.getName(), serviceid, idbuilder.substring(1), descbuilder.substring(1));

*/		
		/*for(SearchResultBackBean searchResultBackBean:searchResults)
		{
			if(searchResultBackBean.getId().equals(sea)
		}*/
		
		
		/*IService serviceObj = serviceRegistry.getServiceObject(serviceid);
		
		List<ISearchResult> searchResults = serviceObj.search(term);
		
		for(ISearchResult searchrrrrrrresult:searchResults){
					
		if( searchResult.getId().equals(searchrrrrrrresult.getId()) ){
				description = searchrrrrrrresult.getDescription();
				userProfileManager.addUserProfile(principal.getName(), serviceid, profileid, description);
			}
		}
			*/
		
		return "auth/home/profile";
	}

}
