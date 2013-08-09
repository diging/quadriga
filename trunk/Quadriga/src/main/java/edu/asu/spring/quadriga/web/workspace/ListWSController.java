package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IDspaceKeysFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceKeys;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

/**
 * Controller to handle all the workspace requests for Quadriga.
 * 
 *  The (Dspace) design flow is to load the list of all communities accessible to the user
 *  and then load all collections within the community selected by the user. This 
 *  second call for collection loads all the collections and the items within them.
 *  The last call is to load the set of bitstreams within a selected item. Any deviation 
 *  from the above flow is handled by the concerned classes.
 *  
 *  
 * @author Kiran Kumar Batna
 * @author Ram Kumar Kumaresan
 */

@Controller
@Scope(value="session")
public class ListWSController 
{
	@Autowired
	private	IListWSManager wsManager;

	@Autowired
	private	IRetrieveWSCollabManager wsCollabManager;

	@Autowired
	private IDspaceManager dspaceManager;

	private String dspaceUsername;
	private String dspacePassword;

	@Autowired
	private IDspaceKeysFactory dspaceKeysFactory;
	
	public IDspaceManager getDspaceManager() {
		return dspaceManager;
	}


	public void setDspaceManager(IDspaceManager dspaceManager) {
		this.dspaceManager = dspaceManager;
	}


	public IRetrieveWSCollabManager getWsCollabManager() {
		return wsCollabManager;
	}


	public void setWsCollabManager(IRetrieveWSCollabManager wsCollabManager) {
		this.wsCollabManager = wsCollabManager;
	}


	public IListWSManager getWsManager() {
		return wsManager;
	}


	public void setWsManager(IListWSManager wsManager) {
		this.wsManager = wsManager;
	}


	

	@RequestMapping(value="/auth/workbench/keys", method=RequestMethod.GET)
	public ModelAndView addProjectRequestForm()
	{
		System.out.println("Inside controller.........");
		return new ModelAndView("/auth/workbench/keys","command",dspaceKeysFactory.createDspaceKeysObject());
	}

	@RequestMapping(value = "/auth/workbench/updatekeys", method = RequestMethod.POST)
	public String addStudent(@ModelAttribute("SpringWeb")DspaceKeys dspaceKeys, ModelMap model) {
		System.out.println("...........");
		System.out.println(dspaceKeys.getPublicKey());
		System.out.println(dspaceKeys.getPrivateKey());
		return "redirect:/auth/workbench";
	}	
	
	/**
	 * This will list the details of workspaces 
	 * @param  workspaceid
	 * @param  model
	 * @return String - url of the page listing all the workspaces of the project.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@RequestMapping(value="auth/workbench/workspace/workspacedetails/{workspaceid}", method = RequestMethod.GET)
	public String getWorkspaceDetails(@PathVariable("workspaceid") String workspaceid, Principal principal, ModelMap model) throws QuadrigaStorageException, QuadrigaAccessException
	{
		String userName;
		IWorkSpace workspace;
		List<ICollaborator> collaboratorList;

		userName = principal.getName();
		workspace = getWsManager().getWorkspaceDetails(workspaceid,userName);

		//retrieve the collaborators associated with the workspace
		collaboratorList = getWsCollabManager().getWorkspaceCollaborators(workspaceid);

		workspace.setCollaborators(collaboratorList);
		List<INetwork> networkList = wsManager.getWorkspaceNetworkList(workspaceid);
		model.addAttribute("networkList", networkList);
		model.addAttribute("workspacedetails", workspace);
		if(this.dspaceUsername != null && this.dspacePassword != null)
		{
			model.addAttribute("dspaceLogin", "true");
		}
		
		
		return "auth/workbench/workspace/workspacedetails";
	}


	/**
	 * Following methods are responsible for the Dspace part of the workspace
	 */


	/**
	 * This method is responsible for the handle of dspace Username and password.
	 * After the assignment of dspace variables, this redirects to the dspace communities controller.
	 * 
	 * @param workspaceId		The workspace id from which the add request is raised.
	 * @param dspaceUsername	The dspace username provided by the user.
	 * @param dspacePassword	The dspace password provided by the user.
	 * @return					Redirect to the dspace communities page.
	 * @author 					Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/adddspacelogin", method = RequestMethod.POST)
	public String addFilesDspaceAuthentication(@PathVariable("workspaceId") String workspaceId, HttpServletRequest req, ModelMap model, Principal principal) {		
		String dspaceUsername = req.getParameter("username");
		String dspacePassword = req.getParameter("password");
		String dspacePublicAccess = req.getParameter("dspacePublicAccess");
		if(dspacePublicAccess == null)
		{
			if(dspaceUsername == null || dspacePassword == null)
			{
				return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
			}
			else if(dspaceUsername.equals("") || dspacePassword.equals(""))
			{
				return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
			}
			this.dspaceUsername = dspaceUsername;
			this.dspacePassword = dspacePassword;
		}
		else
		{
			this.dspaceUsername = "";
			this.dspacePassword = "";
		}


		return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
	}


	/**
	 * This method is responsible for the handle of dspace Username and password.
	 * After the assignment of dspace variables, this redirects to the {@link #updateBitStreamsFromWorkspace(String, ModelMap, Principal)} controller
	 * for updating the bitstreams in this workspace
	 * 
	 * @param workspaceId		The workspace id from which the update request is raised.
	 * @param dspaceUsername	The dspace username provided by the user.
	 * @param dspacePassword	The dspace password provided by the user.
	 * @return					Redirect to the dspace update page.
	 * @author 					Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/syncdspacelogin", method = RequestMethod.POST)
	public String syncFilesDspaceAuthentication(@PathVariable("workspaceId") String workspaceId, HttpServletRequest req, ModelMap model, Principal principal) {
		String dspaceUsername = req.getParameter("username");
		String dspacePassword = req.getParameter("password");
		String dspacePublicAccess = req.getParameter("dspacePublicAccess");
		
		if(dspacePublicAccess == null)
		{
			if(dspaceUsername == null || dspacePassword == null)
			{
				return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
			}
			else if(dspaceUsername.equals("") || dspacePassword.equals(""))
			{
				return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
			}
			this.dspaceUsername = dspaceUsername;
			this.dspacePassword = dspacePassword;
		}
		else
		{
			this.dspaceUsername = "";
			this.dspacePassword = "";
		}

		return "redirect:/auth/workbench/workspace/"+workspaceId+"/updatebitstreams";
	}

	/**
	 * This method is responsible for the assignment/change of dspace Username and password.
	 * When the user changes the dspace authentication. All the cached dspace data is cleared.
	 * After the assignment of dspace variables, this redirects to the workspace page.
	 * 
	 * @param workspaceId		The workspace id from which the add request is raised.
	 * @param dspaceUsername	The dspace username provided by the user.
	 * @param dspacePassword	The dspace password provided by the user.
	 * @return					Redirect to the workspace page.
	 * @author 					Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/changedspacelogin", method = RequestMethod.POST)
	public String changeDspaceAuthentication(@PathVariable("workspaceId") String workspaceId, HttpServletRequest req, ModelMap model, Principal principal) {
		String dspaceUsername = req.getParameter("username");
		String dspacePassword = req.getParameter("password");
		String dspacePublicAccess = req.getParameter("dspacePublicAccess");
		if(dspacePublicAccess == null)
		{
			if(dspaceUsername == null || dspacePassword == null)
			{
				return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
			}
			else if(dspaceUsername.equals("") || dspacePassword.equals(""))
			{
				return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
			}
			this.dspaceUsername = dspaceUsername;
			this.dspacePassword = dspacePassword;
		}
		else
		{
			this.dspaceUsername = "";
			this.dspacePassword = "";
		}

		getDspaceManager().clearCompleteCache();
		
		return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
	}


	/**
	 * Handle the request for the list of communities to be fetched from Dspace.
	 * 
	 * @return Return to the dspace communities page of Quadriga
	 * @author Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/communities", method = RequestMethod.GET)
	public String workspaceCommunityListRequest(@PathVariable("workspaceId") String workspaceId, ModelMap model, Principal principal) {

		if(this.dspaceUsername == null || this.dspacePassword == null)
		{
			return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
		}

		List<ICommunity> communities = getDspaceManager().getAllCommunities(this.dspaceUsername,this.dspacePassword);

		model.addAttribute("communityList", communities);
		model.addAttribute("workspaceId",workspaceId);

		return "auth/workbench/workspace/communities";
	}


	/**
	 * Handle the request for the list of collections associated with a community.
	 * 
	 * @param communityId	The community id of the community whose collections are to be fetched.
	 * @return				Return to the collections page of Quadriga
	 * @author 				Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/community/{communityId}", method = RequestMethod.GET)
	public String workspaceCommunityRequest(@PathVariable("workspaceId") String workspaceId, @PathVariable("communityId") String communityId, ModelMap model, Principal principal) {

		if(this.dspaceUsername == null || this.dspacePassword == null)
		{
			return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
		}

		String communityName = getDspaceManager().getCommunityName(communityId);

		//No community has been fetched. The user is trying to access the collection page directly
		//Redirect him to community list page
		if(communityName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}
		List<ICollection> collections = getDspaceManager().getAllCollections(this.dspaceUsername,this.dspacePassword, communityId);

		model.addAttribute("communityName", communityName);
		model.addAttribute("collectionList", collections);
		model.addAttribute("workspaceId",workspaceId);

		return "auth/workbench/workspace/community";
	}


	/**
	 * Handle the request for the list of items associated with a collection.
	 * 
	 * @param collectionId		The collection id of the collection whose items are to be fetched.
	 * @return					Return to the items page of Quadriga.
	 * @author 					Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/community/collection/{collectionId}", method = RequestMethod.GET)
	public String workspaceItemListRequest(@PathVariable("workspaceId") String workspaceId, @PathVariable("collectionId") String collectionId, ModelMap model, Principal principal) {

		if(this.dspaceUsername == null || this.dspacePassword == null)
		{
			return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
		}

		String communityId = getDspaceManager().getCommunityId(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(communityId == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String communityName = getDspaceManager().getCommunityName(communityId);
		//No such community has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(communityName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String collectionName = getDspaceManager().getCollectionName(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(collectionName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		List<IItem> items = getDspaceManager().getAllItems(collectionId);
		model.addAttribute("communityId",communityId);
		model.addAttribute("communityName",communityName);
		model.addAttribute("collectionId",collectionId);
		model.addAttribute("collectionName", collectionName);		
		model.addAttribute("itemList", items);
		model.addAttribute("workspaceId",workspaceId);

		return "auth/workbench/workspace/community/collection";
	}

	/**
	 * Handle the request for the list of bitstreams associated with an item.
	 * 
	 * @param itemId		The id of the item whose bitstreams are to be listed.
	 * @param collectionId	The id of the collection to which the item and bitstream belongs to.
	 * 
	 * @return			Return to the bitstream page of quadriga.
	 * @author 			Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/community/collection/item", method = RequestMethod.GET)
	public String workspaceBitStreamListRequest(@PathVariable("workspaceId") String workspaceId,@RequestParam("itemId") String itemId,@RequestParam("collectionId") String collectionId, ModelMap model, Principal principal){

		if(this.dspaceUsername == null || this.dspacePassword == null)
		{
			return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
		}


		String communityId = getDspaceManager().getCommunityId(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(communityId == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String communityName = getDspaceManager().getCommunityName(communityId);
		//No such community has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(communityName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String collectionName = getDspaceManager().getCollectionName(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect him to community list page
		if(collectionName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String itemName = getDspaceManager().getItemName(collectionId, itemId);
		//No such item has been fetched. The user is trying to access the bitstream page directly
		//Redirect him to community list page
		if(itemName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}


		List<IBitStream> bitstreams = getDspaceManager().getAllBitStreams(this.dspaceUsername,this.dspacePassword,collectionId, itemId);
		model.addAttribute("communityId",communityId);
		model.addAttribute("communityName",communityName);
		model.addAttribute("collectionId",collectionId);
		model.addAttribute("collectionName", collectionName);
		model.addAttribute("itemId",itemId);
		model.addAttribute("itemName",itemName);
		model.addAttribute("bitList",bitstreams);	
		model.addAttribute("workspaceId",workspaceId);

		return "auth/workbench/workspace/community/collection/item";
	}


	/**
	 * Handle ajax requests to retrieve the collection name based on the collection id.
	 * 
	 * @param collectionid		The id of the collection whose name is to fetched.
	 * @return					Returns the collection name if it is loaded from Dspace. Else returns 'Loading...'
	 * @author 					Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/collectionstatus/{collectionid}", method = RequestMethod.GET)
	public @ResponseBody String getCollectionStatus(@PathVariable("collectionid") String collectionid) {
		ICollection collection = getDspaceManager().getCollection(collectionid);
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
	 * @author 					Ram Kumar Kumaresan 
	 */
	@RequestMapping(value = "/auth/workbench/workspace/bitstreamstatus", method = RequestMethod.GET)
	public @ResponseBody String getBitStreamStatus(@RequestParam("bitstreamId") String bitstreamId, @RequestParam("itemId") String itemId,@RequestParam("collectionId") String collectionId) {
		IBitStream bitstream = getDspaceManager().getBitStream(collectionId, itemId, bitstreamId);
		if(bitstream != null)
		{
			if(bitstream.getName() != null)
				return bitstream.getName();
		}
		return "Loading...";		
	}


	/**
	 * Handle the request to add one or more bitstreams to a workspace. The user must have access to the project that the workspace belongs to.
	 * If not, this method will throw an QuadrigaACcessException.
	 * 
	 * @param workspaceId					The id of the workspace to which the bitstream(s) are to be added.
	 * @param communityId					The id of the community to which the bitstream(s) belong to.
	 * @param collectionId					The id of the colletion to which the bitstream(s) belong to.
	 * @param itemId						The id of the item to which the bitstream(s) belong to.
	 * @param bitstreamids					The id(s) of the bitstream(s) which are to be added to the workspace.
	 * @return								Return to the workspace page
	 * @throws QuadrigaStorageException		Thrown when any unexpected error occurs in the database.
	 * @throws QuadrigaAccessException		Thrown when a user tries to modify a workspace to which he/she does not have access. Also thrown when a user tries to access this method with made-up request paramaters.
	 * @author 								Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/addbitstreams", method = RequestMethod.POST)
	public String addBitStreamsToWorkspace(@PathVariable("workspaceId") String workspaceId, @RequestParam(value="communityid") String communityId,@RequestParam(value="collectionid") String collectionId,@RequestParam(value="itemid") String itemId,@RequestParam(value="bitstreamids") String[] bitstreamids, ModelMap model, Principal principal) throws QuadrigaStorageException, QuadrigaAccessException{
		if(this.dspaceUsername == null || this.dspacePassword == null)
		{
			return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
		}

		getDspaceManager().addBitStreamsToWorkspace(workspaceId, communityId, collectionId, itemId, bitstreamids, principal.getName());
		return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
	}

	/**
	 * Handle the request to delete bitstream(s) from a workspace.
	 * 
	 * @param workspaceId					The id of the workspace from which the bitstream(s) are to deleted. 
	 * @param bitstreamids					The id(s) of the bitstream(s) which are to deleted from the workspace.
	 * @return								Return to the workspace page.
	 * @throws QuadrigaStorageException		Thrown when any unexpected error occurs in the database.
	 * @throws QuadrigaAccessException		Thrown when a user tries to modify a workspace to which he/she does not have access. Also thrown when a user tries to access this method with made-up request paramaters.
	 * @author 								Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/deletebitstreams", method = RequestMethod.POST)
	public String deleteBitStreamsFromWorkspace(@PathVariable("workspaceId") String workspaceId, @RequestParam(value="bitstreamids") String[] bitstreamids, ModelMap model, Principal principal) throws QuadrigaStorageException, QuadrigaAccessException{

		getDspaceManager().deleteBitstreamFromWorkspace(workspaceId, bitstreamids, principal.getName());
		return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
	}

	/**
	 * Handle the request to update the bitstreams of a workspace. The bitstreams and its related metadata are updated by sending a request to dspace.
	 * The user must have access to the project that the workspace belongs to. If not, this method will throw a QuadrigaAccessException.
	 * 
	 * @param workspaceId					The id of the workspace whose bitstream(s) are to be updated.
	 * @return								Return to the workspace page.
	 * @throws QuadrigaStorageException		Thrown when any unexpected error occurs in the database.
	 * @throws QuadrigaAccessException		Thrown when a user tries to modify a workspace to which he/she does not have access. Also thrown when a user tries to access this method with made-up request paramaters.
	 * @author 								Ram Kumar Kumaresan
	 */
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/updatebitstreams", method = RequestMethod.GET)
	public String updateBitStreamsFromWorkspace(@PathVariable("workspaceId") String workspaceId, ModelMap model, Principal principal) throws QuadrigaStorageException, QuadrigaAccessException{
		if(this.dspaceUsername == null || this.dspacePassword == null)
		{
			return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
		}
		getDspaceManager().updateDspaceMetadata(workspaceId, principal.getName(), this.dspaceUsername, this.dspacePassword);
		return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
	}
}
