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
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;

/**
 * Controller to handle all the dspace requests for Quadriga.
 * @author Ram Kumar Kumaresan
 *
 */
@Controller
public class DspaceController {

	@Autowired
	private IDspaceManager dspaceManager;
	
	@RequestMapping(value = "/auth/workbench/workspace/communities", method = RequestMethod.GET)
	public String workspaceCommunityListRequest(ModelMap model, Principal principal) {
		
		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		List<ICommunity> communities = dspaceManager.getAllCommunities(principal.getName(),sPassword);
		model.addAttribute("communityList", communities);

		return "auth/workbench/workspace/communities";
	}
	
	
	@RequestMapping(value = "/auth/workbench/workspace/community/{communityId}", method = RequestMethod.GET)
	public String workspaceCommunityRequest(@PathVariable("communityId") String communityId, ModelMap model, Principal principal) {

		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		String communityName = dspaceManager.getCommunityName(communityId);
		
		//No community has been fetched. The user is trying to access the collection page directly
		//Redirect him to community list page
		if(communityName == null)
		{
			return "redirect:/auth/workbench/workspace/communities";
		}
		List<ICollection> collections = dspaceManager.getAllCollections(principal.getName(),sPassword, communityId);

		model.addAttribute("communityName", communityName);
		model.addAttribute("collectionList", collections);

		return "auth/workbench/workspace/community";
	}
	
	
	@RequestMapping(value = "/auth/workbench/workspace/community/collection/{collectionId}", method = RequestMethod.GET)
	public String workspaceItemRequest(@PathVariable("collectionId") String collectionId, ModelMap model, Principal principal) {
	
		String collectionName = dspaceManager.getCollectionName(collectionId);
		
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(collectionName == null)
		{
			return "redirect:/auth/workbench/workspace/communities";
		}
		
		List<IItem> items = dspaceManager.getAllItems(collectionId);
		model.addAttribute("collectionName", collectionName);
		model.addAttribute("itemList", items);
		
		return "auth/workbench/workspace/community/collection";
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

}
