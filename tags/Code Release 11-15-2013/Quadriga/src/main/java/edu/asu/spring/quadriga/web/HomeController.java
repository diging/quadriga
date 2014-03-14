package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

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
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceFormFactory;
import edu.asu.spring.quadriga.profile.IServiceRegistry;
import edu.asu.spring.quadriga.profile.impl.ProfileManager;
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
	private ProfileManager profilemanager;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private ProfileValidator profileValidator;
	
	@Autowired
	private IServiceRegistry serviceRegistry;
	
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
	

	@RequestMapping(value="auth/profile", method = RequestMethod.GET)
	public String showProfile(Model model, Principal principal) throws QuadrigaException, QuadrigaStorageException
	{
		List<ISearchResult> userProfile = profilemanager.getUserProfile();
		model.addAttribute("userProfile", userProfile);
		return "auth/home/showProfile";
	}
	
	@RequestMapping(value="auth/profile/addnew", method = RequestMethod.GET)
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
		}
		
		
		return "auth/home/profile";
	}
	
	@RequestMapping(value = "auth/profile/{serviceid}/{term}/add", method = RequestMethod.POST)
	public String addUri(@ModelAttribute("SearchResultBackBeanForm") SearchResultBackBeanForm searchResultBackBeanForm, @PathVariable("serviceid") String serviceid, @PathVariable("term") String term, Model model, Principal principal) throws QuadrigaStorageException
	{
		
		String profileid;
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
					
					if( searchResultBackBean.getId().equals(conceptSearchResult.getId()) )
					{
						profilebuilder = new StringBuilder();
						searchResultBackBean.getId();
						profilebuilder.append(",");
						profilebuilder.append(searchResultBackBean.getId());
						searchResultBackBean.setDescription(conceptSearchResult.getDescription());	
						searchResultBackBean.getDescription();
						profilebuilder.append(",");
						profilebuilder.append(searchResultBackBean.getDescription());
						searchResultBackBean.setProfileName(conceptSearchResult.getName());
						profilebuilder.append(",");
						profilebuilder.append(searchResultBackBean.getProfileName());
						errmsg = userProfileManager.addUserProfile(principal.getName(), serviceid, profilebuilder.substring(1) );

					}
				}
			}
		}
		
		if(errmsg.equals("no errors"))
		{	
			model.addAttribute("success",1);
			model.addAttribute("ServiceBackBean",new ServiceBackBean());
			model.addAttribute("serviceNameIdMap",serviceNameIdMap);
			model.addAttribute("searchResults", searchResults);
		}
		
		else
		{
			model.addAttribute("errmsg", errmsg);
			model.addAttribute("ServiceBackBean",new ServiceBackBean());
			model.addAttribute("serviceNameIdMap",serviceNameIdMap);	
		}
		
		return "auth/home/profile";
	}

}