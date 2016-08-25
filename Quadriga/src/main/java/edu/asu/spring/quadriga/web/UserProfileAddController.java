package edu.asu.spring.quadriga.web;

import org.springframework.stereotype.Controller;

@Controller
public class UserProfileAddController {
	
	/*@Autowired
	private IUserProfileManager profileManager;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private ProfileAddValidator profileValidator;
	
	@Autowired
	private IServiceRegistry serviceRegistry;
	
	@Autowired
	private IServiceFormFactory serviceFormFactory;
	
	@Autowired
	private IUserProfileManager userProfileManager;
	
	@Autowired
	private SearchResultBackBeanForm searchResultBackBeanForm;
	
	@Autowired
	private SearchResultBackBeanFormManager backBeanFormManager;
	
	@Autowired
	private ServiceBackBean serviceBackBean;
*/
	/*@InitBinder
	protected void initBinder(WebDataBinder validateBinder) throws Exception {
		validateBinder.setValidator(profileValidator);
}*/
	

	/**
	 * this method is used to add search results returned from the service to the own profile
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
	
	/*@RequestMapping(value = "auth/profile/{serviceid}/{term}/add", method = RequestMethod.POST)
	public String addSearchResult( @ModelAttribute("SearchResultBackBeanForm")  SearchResultBackBeanForm searchResultBackBeanForm, BindingResult result,
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
				else
				{
					model.addAttribute("ServiceBackBean", serviceBackBean);
					//List<SearchResultBackBean> resultLists =  profileManager.showUserProfile(principal.getName());
					searchResultBackBeanForm.setSearchResultList(backBeanSearchResults);
					model.addAttribute("searchResultBackBeanForm", searchResultBackBeanForm);
					model.addAttribute("success",2);
					model.addAttribute("errmsg", "please select some record");
					return "auth/home/profile";
				}
			}
			
			List<SearchResultBackBean> resultLists =  profileManager.showUserProfile(principal.getName());
			searchResultBackBeanForm.setSearchResultList(resultLists);
			model.addAttribute("searchResultBackBeanForm", searchResultBackBeanForm);
			
		}
		
		return "auth/home/showProfile";
	}
}*/


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
}
