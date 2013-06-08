package edu.asu.spring.quadriga.web;


import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.service.IDspaceManager;

/**
 * The controller to manage the workspace requests from the user
 * and also to connect to dspace to fetch the repository data
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Controller
public class WorkspaceController {

	@Autowired
	private IDspaceManager dspaceManager;

	/**
	 * Simply selects the workspace communities view to render by returning its path.
	 */
	@RequestMapping(value = "/auth/workspace", method = RequestMethod.GET)
	public String workspaceRequest(ModelMap model, Principal principal) {
		dspaceManager.checkRestConnection();
		return "redirect:/auth/workspace/communities";
	}

	@RequestMapping(value = "/auth/workspace/communities", method = RequestMethod.GET)
	public String workspaceCommunityListRequest(ModelMap model, Principal principal) {
		
		List<ICommunity> communities = dspaceManager.getAllCommunities();

		model.addAttribute("communityList", communities);

		return "auth/workspace/communities";
	}
	
	@RequestMapping(value = "/auth/workspace/community/{communityTitle}", method = RequestMethod.GET)
	public String workspaceCommunityRequest(@PathVariable("communityTitle") String communityTitle, ModelMap model, Principal principal) {
		
		List<ICollection> collections = dspaceManager.getAllCollections(communityTitle);

		model.addAttribute("communityTitle", communityTitle);
		model.addAttribute("collectionList", collections);

		return "auth/workspace/community/collection";
	}
}
