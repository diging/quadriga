package edu.asu.spring.quadriga.web.workspace;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.accesschecks.IWSSecurityChecker;
import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.domain.dspace.ICollection;
import edu.asu.spring.quadriga.domain.dspace.ICommunity;
import edu.asu.spring.quadriga.domain.dspace.IItem;
import edu.asu.spring.quadriga.domain.factories.IDspaceKeysFactory;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceBitStream;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceKeys;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCollaboratorManager;
import edu.asu.spring.quadriga.validator.DspaceKeysValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

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
	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	private	IListWSManager wsManager;

	@Autowired
	private IWSSecurityChecker workspaceSecurity;
	
	@Autowired
	private IWorkspaceCollaboratorManager wsCollabManager;

	@Autowired
	private IDspaceManager dspaceManager;

	private String dspaceUsername;
	private String dspacePassword;
	private IDspaceKeys dspaceKeys;

	@Autowired
	private IDspaceKeysFactory dspaceKeysFactory;

	@Resource(name = "uiMessages")
	private Properties dspaceMessages;

	@Autowired
	private	DspaceKeysValidator keysValidator;
	
	@Autowired
	private INetworkManager networkManager;
	
	private static final Logger logger = LoggerFactory
			.getLogger(ListWSController.class);

	/**
	 * Attach the custom validator to the Spring context
	 */
	@InitBinder("command")
	protected void initBinder(WebDataBinder binder) {

		binder.setValidator(keysValidator);
	}

	public DspaceKeysValidator getKeysValidator() {
		return keysValidator;
	}

	public void setKeysValidator(DspaceKeysValidator keysValidator) {
		this.keysValidator = keysValidator;
	}

	public Properties getDspaceMessages() {
		return dspaceMessages;
	}


	public void setDspaceMessages(Properties dspaceMessages) {
		this.dspaceMessages = dspaceMessages;
	}


	public IDspaceKeys getDspaceKeys() {
		return dspaceKeys;
	}


	public void setDspaceKeys(IDspaceKeys dspaceKeys) {
		this.dspaceKeys = dspaceKeys;
	}


	public String getDspaceUsername() {
		return dspaceUsername;
	}


	public void setDspaceUsername(String dspaceUsername) {
		this.dspaceUsername = dspaceUsername;
	}


	public String getDspacePassword() {
		return dspacePassword;
	}


	public void setDspacePassword(String dspacePassword) {
		this.dspacePassword = dspacePassword;
	}


	public IDspaceKeysFactory getDspaceKeysFactory() {
		return dspaceKeysFactory;
	}


	public void setDspaceKeysFactory(IDspaceKeysFactory dspaceKeysFactory) {
		this.dspaceKeysFactory = dspaceKeysFactory;
	}


	public IDspaceManager getDspaceManager() {
		return dspaceManager;
	}


	public void setDspaceManager(IDspaceManager dspaceManager) {
		this.dspaceManager = dspaceManager;
	}


	public IWorkspaceCollaboratorManager getWsCollabManager() {
		return wsCollabManager;
	}


	public void setWsCollabManager(IWorkspaceCollaboratorManager wsCollabManager) {
		this.wsCollabManager = wsCollabManager;
	}


	public IListWSManager getWsManager() {
		return wsManager;
	}


	public void setWsManager(IListWSManager wsManager) {
		this.wsManager = wsManager;
	}

	
	/**
	 * Handle the request to view masked dspace keys and also allow an user to update the keys.
	 * 
	 * @return The model object containing the dspacekeys class for generating the form in the UI.
	 * @throws QuadrigaStorageException	Thrown when database encountered any problem during the operation.
	 */
	@RequestMapping(value="/auth/workbench/keys", method=RequestMethod.GET)
	public ModelAndView addDspaceKeysRequestForm(Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model = new ModelAndView("/auth/workbench/keys","command",getDspaceKeysFactory().createDspaceKeysObject());
		model.addObject("dspaceKeys", dspaceManager.getMaskedDspaceKeys(principal.getName()));

		return model;
	}

	/**
	 * Handle request to insert or update the dspace keys used by the user.
	 * @param dspaceKeys				The public and private key provided by the user.
	 * 
	 * @return							Redirect to the dspace manage pages.
	 * @throws QuadrigaStorageException	Thrown when database encountered any problems during the operation.
	 * @throws QuadrigaAccessException
	 */
	@RequestMapping(value = "/auth/workbench/updatekeys", method = RequestMethod.POST)
	public ModelAndView addDspaceKeys(@Validated @ModelAttribute("command")DspaceKeys dspaceKeys, BindingResult result, Principal principal) throws QuadrigaStorageException, QuadrigaAccessException 
	{
		ModelAndView model = null;
		if(result.hasErrors())
		{			
			model = new ModelAndView("/auth/workbench/keys","command",dspaceKeys);
		}
		else
		{
			//Successfull validation so update the database and set the keys
			model = new ModelAndView("/auth/workbench/keys","command",getDspaceKeysFactory().createDspaceKeysObject());
			dspaceManager.addDspaceKeys(dspaceKeys,principal.getName());
			this.setDspaceKeys(dspaceKeys);
		}
		model.addObject("dspaceKeys", dspaceManager.getMaskedDspaceKeys(principal.getName()));

		return model;
	}

	/**
	 * Handle request to delete the dspace keys from Quadriga. Then redirect to dspace keys page
	 * 
	 * @return	Redirect to the dspace keys page.
	 * @throws 	QuadrigaStorageException	Thrown when database encountered any problems during the operation
	 */
	@RequestMapping(value = "/auth/workbench/deletekeys", method = RequestMethod.GET)
	public String deleteDspaceKeys(Principal principal, ModelMap model) throws QuadrigaStorageException 
	{
		if(dspaceManager.deleteDspaceKeys(principal.getName())==SUCCESS)
		{
			this.setDspaceKeys(null);
			dspaceManager.clearCompleteCache();
		}
		return "redirect:/auth/workbench/keys";
	}

	/**
	 * This will list the details of workspaces 
	 * @param  workspaceid
	 * @param  model
	 * @return String - url of the page listing all the workspaces of the project.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 * @throws QuadrigaException 
	 * @author Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/workspace/workspacedetails/{workspaceid}", method = RequestMethod.GET)
	public String getWorkspaceDetails(@PathVariable("workspaceid") String workspaceid, Principal principal, ModelMap model) throws QuadrigaStorageException, QuadrigaAccessException, QuadrigaException
	{
		String userName = principal.getName();
		IWorkSpace workspace = getWsManager().getWorkspaceDetails(workspaceid,userName);

		
		//Check bitstream access in dspace. 
		this.setDspaceKeys(dspaceManager.getDspaceKeys(principal.getName()));
		if(this.getDspaceKeys() != null) {
			model.addAttribute("dspaceKeys", "true");
		}

		//Check if the dspace authentication is correct.
		List<IWorkspaceBitStream> workspaceBitStreams = null;
		if(dspaceManager.validateDspaceCredentials(this.dspaceUsername, this.dspacePassword, this.dspaceKeys))
		{
			workspaceBitStreams = dspaceManager.checkDspaceBitstreamAccess(workspace.getWorkspaceBitStreams(), this.getDspaceKeys(), this.getDspaceUsername(), this.getDspacePassword());
		}
		else
		{	
			//Set a flag to indicate the error in dspace login credentials.
			model.addAttribute("wrongDspaceLogin", "true");
			workspaceBitStreams = dspaceManager.checkDspaceBitstreamAccess(workspace.getWorkspaceBitStreams(), null, null, null);
		}
		
		workspace.setWorkspaceBitStreams(workspaceBitStreams);
		

		//retrieve the collaborators associated with the workspace
		List<IWorkspaceCollaborator> workspaceCollaboratorList = workspace.getWorkspaceCollaborators();


		workspace.setWorkspaceCollaborators(workspaceCollaboratorList);
		List<IWorkspaceNetwork> workspaceNetworkList = wsManager.getWorkspaceNetworkList(workspaceid);
		model.addAttribute("networkList", workspaceNetworkList);
		model.addAttribute("workspacedetails", workspace);

		if(workspaceSecurity.checkWorkspaceOwner(userName, workspaceid)){
			model.addAttribute("owner", 1);
		}else{
			model.addAttribute("owner", 0);
		}
		if(workspaceSecurity.checkWorkspaceOwnerEditorAccess(userName, workspaceid)){
			model.addAttribute("editoraccess", 1);
		}else{
			model.addAttribute("editoraccess", 0);
		}
		if (workspaceSecurity.checkWorkspaceProjectInheritOwnerEditorAccess(userName, workspaceid)){
			model.addAttribute("projectinherit", 1);
		}else{
			model.addAttribute("projectinherit", 0);
		}
		
		String projectid = wsManager.getProjectIdFromWorkspaceId(workspaceid);
		model.addAttribute("myprojectid", projectid);
		
		//Including a condition to check if the workspace is not deactive. If the workspace is deactive adding attribute to make delete button disabled
		model.addAttribute("isDeactivated", wsManager.getDeactiveStatus(workspaceid));
		//Including a condition to check if the workspace is not archived. 
		model.addAttribute("isArchived", wsManager.getArchiveStatus(workspaceid));
		
		return "auth/workbench/workspace/workspacedetails";
	}


	/**
	 * Following methods are responsible for the Dspace part of the workspace
	 */


	/**
	 * This method is responsible for the assignment/change of dspace Username and password.
	 * When the user changes the dspace authentication. All the cached dspace data is cleared.
	 * After the assignment of dspace variables, this redirects to the workspace page.
	 * 
	 * @param workspaceId		The id of the workspace from which the request originates.
	 * @param req				The request object containing the username and password (or) public access option.
	 * @return					Redirect to the workspace page.
	 * @author 					Ram Kumar Kumaresan
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
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

			this.setDspaceUsername(dspaceUsername);
			this.setDspacePassword(dspacePassword);
		}
		else
		{
			this.setDspaceUsername(null);
			this.setDspacePassword(null);
		}

		dspaceManager.clearCompleteCache();

		return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
	}


	/**
	 * Handle the request for the list of communities to be fetched from Dspace.
	 * 
	 * @return Return to the dspace communities page of Quadriga
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/communities", method = RequestMethod.GET)
	public String workspaceCommunityListRequest(@PathVariable("workspaceId") String workspaceId, ModelMap model, Principal principal) throws QuadrigaException, QuadrigaAccessException {

		List<ICommunity> communities = dspaceManager.getAllCommunities(this.getDspaceKeys(), this.getDspaceUsername(),this.getDspacePassword());

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
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/community/{communityId}", method = RequestMethod.GET)
	public String workspaceCommunityRequest(@PathVariable("workspaceId") String workspaceId, @PathVariable("communityId") String communityId, ModelMap model, Principal principal) {

		String communityName = dspaceManager.getCommunityName(communityId);

		//No community has been fetched. The user is trying to access the collection page directly
		//Redirect user to community list page
		if(communityName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}
		List<ICollection> collections = dspaceManager.getAllCollections(getDspaceKeys(),this.getDspaceUsername(),this.getDspacePassword(), communityId);

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
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/community/collection/{collectionId}", method = RequestMethod.GET)
	public String workspaceItemListRequest(@PathVariable("workspaceId") String workspaceId, @PathVariable("collectionId") String collectionId, ModelMap model, Principal principal) {

		String communityId = dspaceManager.getCommunityId(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect user to community list page
		if(communityId == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String communityName = dspaceManager.getCommunityName(communityId);
		//No such community has been fetched. The user is trying to access the item page directly
		//Redirect user to community list page
		if(communityName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String collectionName = dspaceManager.getCollectionName(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect user to community list page
		if(collectionName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		List<IItem> items = dspaceManager.getAllItems(collectionId);
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
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/community/collection/item", method = RequestMethod.GET)
	public String workspaceBitStreamListRequest(@PathVariable("workspaceId") String workspaceId,@RequestParam("itemId") String itemId,@RequestParam("collectionId") String collectionId, ModelMap model, Principal principal){

		String communityId = dspaceManager.getCommunityId(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect user to community list page
		if(communityId == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String communityName = dspaceManager.getCommunityName(communityId);
		//No such community has been fetched. The user is trying to access the item page directly
		//Redirect user to community list page
		if(communityName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String collectionName = dspaceManager.getCollectionName(collectionId);
		//No such collection has been fetched. The user is trying to access the item page directly
		//Redirect user to community list page
		if(collectionName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}

		String itemName = dspaceManager.getItemName(collectionId, itemId);
		//No such item has been fetched. The user is trying to access the bitstream page directly
		//Redirect user to community list page
		if(itemName == null)
		{
			return "redirect:/auth/workbench/workspace/"+workspaceId+"/communities";
		}


		List<IBitStream> bitstreams = dspaceManager.getAllBitStreams(getDspaceKeys(),this.getDspaceUsername(),this.getDspacePassword(),collectionId, itemId);
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
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 */
	@RequestMapping(value = "/auth/workbench/workspace/collectionstatus/{collectionid}", method = RequestMethod.GET)
	public @ResponseBody String getCollectionStatus(@PathVariable("collectionid") String collectionid) throws QuadrigaException, QuadrigaAccessException {

		//Can't find collection in any of the communities
		String communityid = dspaceManager.getCommunityId(collectionid);
		if(communityid == null)
			return getDspaceMessages().getProperty("dspace.restricted_bitstream");

		ICollection collection = dspaceManager.getCollection(collectionid,communityid);
		if(collection != null)
		{
			if(collection.getLoadStatus() == true)
			{
				if(collection.getName() != null)
					return collection.getName();
				else
					return getDspaceMessages().getProperty("dspace.restricted_collection");
			}
		}
		return getDspaceMessages().getProperty("dspace.loading");		
	}

	@RequestMapping(value = "/auth/workbench/workspace/itemstatus", method = RequestMethod.GET)
	public @ResponseBody String getItemStatus(@RequestParam("bitstreamid") String bitstreamid) throws QuadrigaException, QuadrigaAccessException {
		IBitStream bitstream = null;
		try {
			bitstream = dspaceManager.getWorkspaceItems(bitstreamid, dspaceKeys, dspaceUsername, dspacePassword);
		} catch (QuadrigaStorageException e) {
			return getDspaceMessages().getProperty("dspace.restricted_item");
		}
		if(bitstream != null)
		{
			if(bitstream.getItemName() != null)
			{
				return bitstream.getItemName();
			}
		}
		return getDspaceMessages().getProperty("dspace.loading");
	}

	
	@RequestMapping(value = "/auth/workbench/workspace/bitstreamaccessstatus", method = RequestMethod.GET)
	public @ResponseBody String getBitStreamAccessStatus(@RequestParam("bitstreamid") String bitstreamid) throws QuadrigaException, QuadrigaAccessException {
		IBitStream bitstream = null;
		try {
			bitstream = dspaceManager.getWorkspaceItems(bitstreamid, dspaceKeys, dspaceUsername, dspacePassword);
		} catch (QuadrigaStorageException e) {
			return getDspaceMessages().getProperty("dspace.restricted_bitstream");
		}
		if(bitstream != null)
		{
			if(bitstream.getItemName() != null)
				return bitstream.getItemName();
		}
		return getDspaceMessages().getProperty("dspace.loading");
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
		/*	
		 * Not handling/returning 'Invalid Bitstream' because we are not displaying the metadata bitstreams to the user.
		 */
		IBitStream bitstream = dspaceManager.getBitStream(collectionId, itemId, bitstreamId);
		if(bitstream != null)
		{
			if(bitstream.getItemName() != null)
				return bitstream.getItemName();
		}
		return getDspaceMessages().getProperty("dspace.loading");		
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
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/addbitstreams", method = RequestMethod.POST)
	public String addBitStreamsToWorkspace(@PathVariable("workspaceId") String workspaceId, @RequestParam(value="communityid") String communityId,@RequestParam(value="collectionid") String collectionId,@RequestParam(value="itemid") String itemId,@RequestParam(value="bitstreamids") String[] bitstreamids, ModelMap model, Principal principal) throws QuadrigaStorageException, QuadrigaAccessException, QuadrigaException, QuadrigaAccessException{

		dspaceManager.addBitStreamsToWorkspace(workspaceId, communityId, collectionId, itemId, bitstreamids, principal.getName());
		return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
	}

	/**
	 * Handle the request to delete bitstream(s) from a workspace. Gets the list of authroized bistreams from Dspace for the user and deletes based on that authorization.
	 * 
	 * @param workspaceId					The id of the workspace from which the bitstream(s) are to deleted. 
	 * @param bitstreamids					The id(s) of the bitstream(s) which are to deleted from the workspace.
	 * @return								Return to the workspace page.
	 * @throws QuadrigaStorageException		Thrown when any unexpected error occurs in the database.
	 * @throws QuadrigaAccessException		Thrown when a user tries to modify a workspace to which he/she does not have access. Also thrown when a user tries to access this method with made-up request paramaters.
	 * @author 								Ram Kumar Kumaresan
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/deletebitstreams", method = RequestMethod.POST)
	public String deleteBitStreamsFromWorkspace(@PathVariable("workspaceId") String workspaceId, @RequestParam(value="bitstreamids") String[] bitstreamids, ModelMap model, Principal principal) throws QuadrigaStorageException, QuadrigaAccessException, QuadrigaException, QuadrigaAccessException{

		IWorkSpace workspace = getWsManager().getWorkspaceDetails(workspaceId,principal.getName());
		//Check bitstream access in dspace. 
		this.setDspaceKeys(dspaceManager.getDspaceKeys(principal.getName()));
		List<IWorkspaceBitStream> workspaceBitStreams = dspaceManager.checkDspaceBitstreamAccess(workspace.getWorkspaceBitStreams(), this.getDspaceKeys(), this.getDspaceUsername(), this.getDspacePassword());

		dspaceManager.deleteBitstreamFromWorkspace(workspaceId, bitstreamids, workspaceBitStreams, principal.getName());
		return "redirect:/auth/workbench/workspace/workspacedetails/"+workspaceId;
	}
	
	@RequestMapping(value = "/auth/editing/getitemmetadata/{networkId}", method = RequestMethod.GET)
	public @ResponseBody String viewDspaceMetaData(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("networkId") String networkId, 
			ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException, QStoreStorageException, NoSuchAlgorithmException, JSONException {
		
		INetwork network = networkManager.getNetwork(networkId);
		if(network==null){
			return "auth/accessissue";
		}
		String fileid = networkManager.getSourceReferenceURL(networkId,networkManager.getLatestVersionOfNetwork(networkId));
		logger.info("Source reference ID " + fileid);
		
		String metaData = wsManager.getItemMetadataAsJson(fileid, dspaceUsername, dspacePassword, dspaceKeys);
		
		if(metaData!=null){
			return metaData;
		}
		
		return null;
	}
}
