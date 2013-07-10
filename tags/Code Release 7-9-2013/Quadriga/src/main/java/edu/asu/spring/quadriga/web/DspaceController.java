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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
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
	 * Handle the request for the list of communities to be fetched from Dspace.
	 * 
	 * @return Return to the dspace communities page of Quadriga
	 */
	@RequestMapping(value = "/auth/workbench/workspace/communities", method = RequestMethod.GET)
	public String workspaceCommunityListRequest(ModelMap model, Principal principal) {

		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		List<ICommunity> communities = dspaceManager.getAllCommunities(principal.getName(),sPassword);
		model.addAttribute("communityList", communities);

		return "auth/workbench/workspace/communities";
	}


	/**
	 * Handle the request for the list of collections associated with a community.
	 * 
	 * @param communityId	The community id of the community whose collections are to be fetched.
	 * @return				Return to the collections page of Quadriga
	 */
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


	/**
	 * Handle the request for the list of items associated with a collection.
	 * 
	 * @param collectionId		The collection id of the collection whose items are to be fetched.
	 * @return					Return to the items page of Quadriga.
	 */
	@RequestMapping(value = "/auth/workbench/workspace/community/collection/{collectionId}", method = RequestMethod.GET)
	public String workspaceItemListRequest(@PathVariable("collectionId") String collectionId, ModelMap model, Principal principal) {

		String communityId = dspaceManager.getCommunityId(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(communityId == null)
		{
			return "redirect:/auth/workbench/workspace/communities";
		}

		String communityName = dspaceManager.getCommunityName(communityId);
		//No such community has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(communityName == null)
		{
			return "redirect:/auth/workbench/workspace/communities";
		}

		String collectionName = dspaceManager.getCollectionName(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(collectionName == null)
		{
			return "redirect:/auth/workbench/workspace/communities";
		}

		List<IItem> items = dspaceManager.getAllItems(collectionId);
		model.addAttribute("communityId",communityId);
		model.addAttribute("communityName",communityName);
		model.addAttribute("collectionId",collectionId);
		model.addAttribute("collectionName", collectionName);		
		model.addAttribute("itemList", items);

		return "auth/workbench/workspace/community/collection";
	}

	/**
	 * Handle the request for the list of bitstreams associated with an item.
	 * 
	 * @param itemId		The id of the item whose bitstreams are to be listed.
	 * @param collectionId	The id of the collection to which the item and bitstream belongs to.
	 * 
	 * @return			Return to the bitstream page of quadriga.
	 */
	@RequestMapping(value = "/auth/workbench/workspace/community/collection/item", method = RequestMethod.GET)
	public String workspaceByteStreamListRequest(@RequestParam("itemId") String itemId,@RequestParam("collectionId") String collectionId, ModelMap model, Principal principal){
		String communityId = dspaceManager.getCommunityId(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(communityId == null)
		{
			return "redirect:/auth/workbench/workspace/communities";
		}

		String communityName = dspaceManager.getCommunityName(communityId);
		//No such community has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(communityName == null)
		{
			return "redirect:/auth/workbench/workspace/communities";
		}

		String collectionName = dspaceManager.getCollectionName(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(collectionName == null)
		{
			return "redirect:/auth/workbench/workspace/communities";
		}

		String itemName = dspaceManager.getItemName(collectionId, itemId);
		//No such item has been fetched. The user is trying to access the bitstream page directly
		//Redirect him to community list page
		if(itemName == null)
		{
			return "redirect:/auth/workbench/workspace/communities";
		}

		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		List<IBitStream> bitstreams = dspaceManager.getAllBitStreams(principal.getName(),sPassword,collectionId, itemId);
		model.addAttribute("communityId",communityId);
		model.addAttribute("communityName",communityName);
		model.addAttribute("collectionId",collectionId);
		model.addAttribute("collectionName", collectionName);
		model.addAttribute("itemId",itemId);
		model.addAttribute("itemName",itemName);
		model.addAttribute("bitList",bitstreams);	
		
		return "auth/workbench/workspace/community/collection/item";
	}

	
	/**
	 * Handle ajax requests to retrieve the collection name based on the collection id.
	 * 
	 * @param collectionid		The id of the collection whose name is to fetched.
	 * @return					Returns the collection name if it is loaded from Dspace. Else returns 'Loading...'
	 */
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

	/**
	 * Handle ajax requests to retrieve the bitstream name based on the bitstream id, item id and collection id
	 * 
	 * @param bitstreamId		The id of the bitstream whose name is to be retrieved.
	 * @param itemId			The id of the item to which the bitstream belongs to.
	 * @param collectionId		The id of the collection to which the item and the bitstream belongs to.
	 * @return					Returns the bitstream name if it is loaded. Else returns 'Loading...' 
	 */
	@RequestMapping(value = "/auth/workbench/workspace/bitstreamstatus", method = RequestMethod.GET)
	public @ResponseBody String getBitStreamStatus(@RequestParam("bitstreamId") String bitstreamId, @RequestParam("itemId") String itemId,@RequestParam("collectionId") String collectionId) {
		IBitStream bitstream = dspaceManager.getBitStreamName(collectionId, itemId, bitstreamId);
		if(bitstream != null)
		{
			if(bitstream.getName() != null)
				return bitstream.getName();
		}
		return "Loading...";		
	}
	
	
	@RequestMapping(value = "/auth/workbench/workspace/addbitstreams", method = RequestMethod.POST)
	public String addBitStreamsToWorkspace(@RequestParam(value="bitstreamids") String[] bitstreamids, ModelMap model, Principal principal){
		System.out.println("Controller received request......................."+bitstreamids.length);
		
		
		
		
		return "redirect:/auth/workbench/workspace/communities";
	}
}
