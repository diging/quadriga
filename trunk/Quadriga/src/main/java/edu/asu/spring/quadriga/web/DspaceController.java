/**
 * 
 */
package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
			return collection.getName();
		}
		else
		{
			return "Loading...";
		}		
	}
	
	
	@RequestMapping(value = "/auth/workbench/workspace/communities-collections", method = RequestMethod.GET)
	public @ResponseBody String getCommunitiesAndCollections() {
		String sUserName = (String)SecurityContextHolder.getContext().getAuthentication().getName();
		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		StringBuilder sResult = new StringBuilder();
		List<ICommunity> communities = dspaceManager.getAllCommunities(sUserName,sPassword);
		
		if(communities.size()>0)
		{
			sResult.append("<h2>Communities in HPS Repository</h2>");
			sResult.append("<span class=\"byline\">Select a community to browse its collections.</span>");
			sResult.append("<span style=\"font-weight: bold\">");
			for(ICommunity community: communities)
			{
				sResult.append("<a href='/quadriga/auth/workspace/community/"+community.getId()+"'>");
				sResult.append(community.getName());
				sResult.append("</a>");
				sResult.append("<br />");
			}
			sResult.append("</span>");
		}
		else
		{
			sResult.append("<h2>No Communities in HPS Repository</h2>");
		}
		
		return sResult.toString();
	}
	

	@RequestMapping(value = "/auth/workbench/workspace/community/{communityTitle}", method = RequestMethod.GET)
	public String workspaceCommunityRequest(@PathVariable("communityTitle") String communityTitle, ModelMap model, Principal principal) {

		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		List<IDspaceCollection> collections = dspaceManager.getAllCollections(principal.getName(),sPassword, communityTitle);

		model.addAttribute("communityTitle", communityTitle);
		model.addAttribute("collectionList", collections);

		return "auth/workbench/workspace/community/collection";
	}
	
//	@RequestMapping(value = "/auth/workbench/workspace/ajaxtest", method = RequestMethod.GET)
//	public @ResponseBody String getTime() {
//
//		Random rand = new Random();
//		float r = rand.nextFloat() * 100;
//		String result = "<br>The Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
//		System.out.println("Debug Message from Spring-Ajax-JQuery Controller.." + new Date().toString());
//		return result;
//	}
}
