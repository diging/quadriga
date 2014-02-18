package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceFormFactory;
import edu.asu.spring.quadriga.profile.IServiceRegistry;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBeanForm;
import edu.asu.spring.quadriga.profile.impl.ServiceBackBean;
import edu.asu.spring.quadriga.profile.validator.ProfileAddValidator;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.IUserProfileManager;
import edu.asu.spring.quadriga.web.profile.impl.ProfileManager;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBeanFormManager;

@Controller
public class UserProfileAddController {
	
	@Autowired
	private IUserProfileManager profileManager;
	
	@Autowired
	private ProfileManager profilemanager;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private ProfileAddValidator profileValidator;
	
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

	/*@InitBinder
	protected void initBinder(WebDataBinder validateBinder) throws Exception {
		validateBinder.setValidator(profileValidator);
}*/

	/**
	 * this method is used to add searchresults returned from the service to the own profile
	 * 
	 * @param searchResultBackBeanForm
	 * @param result	error thrown through validation
	 * @param serviceid	id of the service selected by user	
	 * @param term		term entered by user
	 * @param model
	 * @param principal
	 * @return	path of the jsp page
	 * @throws QuadrigaStorageException
	 */
	
	@RequestMapping(value = "auth/profile/{serviceid}/{term}/add", method = RequestMethod.POST)
	public String addSearchResult(@Valid @ModelAttribute("SearchResultBackBeanForm") SearchResultBackBeanForm searchResultBackBeanForm, BindingResult result,
	@PathVariable("serviceid") String serviceid, @PathVariable("term") String term, Model model, Principal principal) throws QuadrigaStorageException
	{
		
		Map<String, String> serviceNameIdMap = serviceRegistry.getServiceNameIdMap();
		IService serviceObj = serviceRegistry.getServiceObject(serviceid);
						
		List<SearchResultBackBean> backBeanSearchResults = searchResultBackBeanForm.getSearchResultList();
				
		if(result.hasErrors())
		{
			searchResultBackBeanForm.setSearchResultList(backBeanSearchResults);
			model.addAttribute("searchResultBackBeanForm", searchResultBackBeanForm);
			return "auth/home/profile";
		}
		else
		{
			for(SearchResultBackBean resultBackBean: backBeanSearchResults)
			{
				if(resultBackBean.getIsChecked() == true)
				{
					userProfileManager.addUserProfile(principal.getName(), serviceid, resultBackBean);
				}
			}
			
			/*if(errmsg.equals(""))
			{	
				
				model.addAttribute("success",1);
				model.addAttribute("ServiceBackBean",new ServiceBackBean());
				model.addAttribute("serviceNameIdMap",serviceNameIdMap);
				model.addAttribute("searchResults", backBeanSearchResults);
			}
			
			else
			{
				model.addAttribute("errmsg", errmsg);
				model.addAttribute("ServiceBackBean",new ServiceBackBean());
				model.addAttribute("serviceNameIdMap",serviceNameIdMap);	
			}
			*/
			List<SearchResultBackBean> resultLists =  profileManager.showUserProfile(principal.getName());
			searchResultBackBeanForm.setSearchResultList(resultLists);
			model.addAttribute("searchResultBackBeanForm", searchResultBackBeanForm);
		}
		
		return "auth/home/showProfile";
	}
}
