/**
 * 
 */
package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;

/**
 * Controller to handle all the dspace requests for Quadriga.
 * @author Ram Kumar Kumaresan
 *
 */
@Controller
//@Scope("request")
public class DspaceController {

	@Autowired
	private IDspaceManager dspaceManager;


	/**
	 * Simply selects the workspace communities view to render by returning its path.
	 */
	@RequestMapping(value = "/auth/workbench/workspace", method = RequestMethod.GET)
	public String workspaceRequest(ModelMap model, Principal principal) {
		return "redirect:/auth/workbench/workspace/communities";
	}

	@RequestMapping(value = "/auth/workbench/workspace/communities", method = RequestMethod.GET)
	public String workspaceCommunityListRequest(ModelMap model, Principal principal) {
		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		List<ICommunity> communities = dspaceManager.getAllCommunities(principal.getName(),sPassword);

		model.addAttribute("communityList", communities);

		return "auth/workbench/workspace/communities";
	}

	@RequestMapping(value = "/auth/workbench/workspace/collectionstatus/{collectionid}", method = RequestMethod.GET)
	public @ResponseBody String getCollectionStatus(@PathVariable("collectionid") String collectionid) {
		ICollection collection = dspaceManager.getCollection(collectionid);
		if(collection != null)
		{
			if(collection.getName() != null)
				return collection.getName();
		}
		return "Loading...";		
	}


	@RequestMapping(value = "/auth/workbench/workspace/community/{communityTitle}", method = RequestMethod.GET)
	public String workspaceCommunityRequest(@PathVariable("communityTitle") String communityTitle, ModelMap model, Principal principal) {

		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		List<IDspaceCollection> collections = dspaceManager.getAllCollections(principal.getName(),sPassword, communityTitle);

		model.addAttribute("communityTitle", communityTitle);
		model.addAttribute("collectionList", collections);

		return "auth/workbench/workspace/community/collection";
	}

}
