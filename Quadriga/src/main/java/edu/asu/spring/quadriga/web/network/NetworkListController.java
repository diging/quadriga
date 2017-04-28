package edu.asu.spring.quadriga.web.network;

import java.security.Principal;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class will handle list {@link INetwork} of the {@link IUser} and fetch the {@link INetwork} details from DB and QStore.
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class NetworkListController {

	@Autowired
	private INetworkManager networkManager;
	
	@Autowired
    private INetworkTransformationManager transformationManager;
    
    @Autowired
    private ID3Creator d3Creator;

	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private IProjectSecurityChecker projectSecurity;
	
	@Autowired
	private IWorkspaceManager workspaceManager;
	
	@Autowired
	private IRetrieveProjectManager projectManager;

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkListController.class);

	/**
	 * This method helps in listing of network belonging to the user in tree view. 
	 * @author Lohith Dwaraka
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JSONException 
	 */
	@RequestMapping(value = "auth/networks", method = RequestMethod.GET)
    public String listNetworks(ModelMap model, Principal principal) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUser(principal.getName());
		List<INetwork> networkList=networkManager.getNetworkList(user);
		model.addAttribute("userId", user.getUserName());
		model.addAttribute("networkList", networkList);
		return "auth/networks";
	}


	/**
	 * Get the network displayed on to JSP by passing JSON string
	 * @author Lohith Dwaraka, Chiraag Subramanian
	 * @param networkId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JAXBException
	 */
	@RequestMapping(value = "auth/networks/visualize/{networkId}", method = RequestMethod.GET)
	public String visualizeNetworks(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		
	    // Identify the User
	    String userId = principal.getName();
	
	    // Get network details using the networkId
		INetwork network = networkManager.getNetwork(networkId);
		
		// Get network-workspace mapping details using the network details
		IWorkspaceNetwork workspaceNetwork  =  network.getNetworkWorkspace();
		
		// Get details of workspace associated with the network
		IWorkSpace workspace = workspaceNetwork.getWorkspace();
		
		// Get projectId of the project to which the workspace belongs
		String projectId = workspaceManager.getProjectIdFromWorkspaceId(workspace.getWorkspaceId());
		
		// Get project details using projectId
		IProject project = projectManager.getProjectDetails(projectId);        
        
		// Identify the access type of the project : PUBLIC or PRIVATE
		String accessID = project.getProjectAccess().toString();
		
		// If the project is PRIVATE, check if the user is allowed to access the information associated with the project
		// If the project is PUBLIC, allow access to the information associated with the project
		if(accessID.equals("PRIVATE")){
		    boolean authorizedAccess = false;
	        
	        if (projectSecurity.isProjectOwner(userId, projectId)) 
	            authorizedAccess = true;
	        else{
	            List<String> collaboratorRoles = projectSecurity.getCollaboratorRoles(userId, projectId);
	            if (collaboratorRoles.contains(RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR) || collaboratorRoles.contains(RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN) || collaboratorRoles.contains(RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR)) {
	                authorizedAccess = true;
	            }
	            
	        }
	            
	        if(authorizedAccess){
	            network = networkManager.getNetwork(networkId);
	            if(network==null){
	                return "auth/404";
	            }
	            ITransformedNetwork transformedNetwork= transformationManager.getTransformedNetwork(networkId);
	            
	            String nwId = "\""+networkId+"\"";
	            model.addAttribute("networkid",nwId);
	            model.addAttribute("network", network);
	            String json = null;
	            if(transformedNetwork!=null){
	                json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
	            }
	            model.addAttribute("jsonstring",json);

	            return "auth/networks/visualize";
	        } 
	        return "public/forbidden";
		}
		else {
            network = networkManager.getNetwork(networkId);
            if(network==null){
                return "auth/404";
            }
            ITransformedNetwork transformedNetwork= transformationManager.getTransformedNetwork(networkId);
            
            String nwId = "\""+networkId+"\"";
            model.addAttribute("networkid",nwId);
            model.addAttribute("network", network);
            String json = null;
            if(transformedNetwork!=null){
                json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
            }
            model.addAttribute("jsonstring",json);

            return "auth/networks/visualize";
		    
		}
		
		
	

		
		
		
		
	}

}
