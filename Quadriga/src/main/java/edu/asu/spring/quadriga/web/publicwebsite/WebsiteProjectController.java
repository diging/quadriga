package edu.asu.spring.quadriga.web.publicwebsite;

/**
 * This controller has all the mappings required to view the external website of a project, view all the networks in that project
 * and visualize the networks 
 * 
 * @author Sayalee Mehendale
 *
 */


import java.security.Principal;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;


@Controller
public class WebsiteProjectController {
	
	//public static String projectid = "";
	
	@Autowired 
	IRetrieveProjectManager projectManager;
	
	@Autowired
	INetworkManager networkmanager;
	
	@Autowired
	@Qualifier("qStoreURL")
	private String qStoreURL;

	@Autowired
	@Qualifier("qStoreURL_Add")
	private String qStoreURL_Add;

	@Autowired
	@Qualifier("qStoreURL_Get")
	private String qStoreURL_Get;

	private static final Logger logger = LoggerFactory
			.getLogger(WebsiteProjectController.class);

	/*
	 * Prepare the QStore GET URL
	 */
	public String getQStoreGetURL() {
		return qStoreURL+""+qStoreURL_Get;
	}

	
	public IRetrieveProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(IRetrieveProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	
	private IProject getProjectDetails(String name) throws QuadrigaStorageException{
		IProject project = projectManager.getProjectDetailsByUnixName(name);
		return project;
	}

	
	/**
	 * This method displays the public or external Website for the particular project 
	 * 
	 * If the project has been set to 'accessible', then the public website page is displayed. If the project does not exist
	 * then an error page is shown.
	 * 
	 * @param unixName 								unix name that is given to the project at the time of its creation
	 * @param model									Model object to map values to view
	 * @return										returns a string to access the external website main page
	 * @throws QuadrigaStorageException				Database storage exception thrown
	 */
	@RequestMapping(value="sites/{ProjectUnixName}", method=RequestMethod.GET)
	public String showProject(@PathVariable("ProjectUnixName") String unixName,Model model, Principal principal) throws QuadrigaStorageException {
		
		String user = principal.getName();
		IProject project = getProjectDetails(unixName);
		//IProject project = projectManager.getProjectDetailsByUnixName(unixName);
		//String projectid = project.getInternalid();
		
		if(project!=null){
			if(user == null && projectManager.getPublicProjectWebsiteAccessibility(unixName)){
				model.addAttribute("project", project);
				return "sites/website";
			}
			if(user!=null && (projectManager.getPrivateProjectWebsiteAccessibility(unixName, user) 
								|| projectManager.getPublicProjectWebsiteAccessibility(unixName))){
				model.addAttribute("project", project);
				return "sites/website";
			}
		}

		return "forbidden";
		
		
	}
	
	/**
	 * This method retrieves all the networks associated with the project based on the project unix name
	 * 
	 * If the project contains networks, it displays all of the networks along with the names of the workspaces
	 * that contain the networks. If no networks have been created for that
	 * particular project, then an appropriate error page is displayed.
	 * 
	 * @param unixName 							unix name that is given to the project at the time of its creation
	 * @param model								Model object to map values to view
	 * @return 									returns a string to access the browse networks page of the project external website
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	@RequestMapping(value="sites/{ProjectUnixName}/browsenetworks", method=RequestMethod.GET)
	public String browseNetworks(@PathVariable("ProjectUnixName") String unixName,Model model, Principal principal) throws QuadrigaStorageException{
		System.out.println("browse");
		IProject project = getProjectDetails(unixName);
		String projectid = project.getInternalid();
		List<INetwork> Networks = networkmanager.getNetworksInProject(projectid);
		
		//List<String> networkNames = null;

		if(Networks != null)
		{
			//networkNames = new ArrayList<String>();
			for(INetwork network : Networks){
				System.out.println(network.getWorkspace().getName());
			}
				//networkNames.add(network.getName());
				 
		}
		
		if(!Networks.isEmpty()){
			model.addAttribute("networks", Networks);
			model.addAttribute("project", project);
			return "sites/browseNetworks";
		}
		
		return "NoNetworks";
	}
	
	/**
	 * This method gives the visualization of the network with the given network id 
	 * @param networkId 					network id of the network that has to be visualized
	 * @param model							ModelMap object to map values to view
	 * @param principal						current session user
	 * @return								returns a string to access the visualize network page of the project external website
	 * @throws QuadrigaStorageException		Database storage exception thrown
	 * @throws JAXBException				JAXB exception while getting the JSON
	 */
	@RequestMapping(value = "sites/networks/visualize/{networkId}", method = RequestMethod.GET)
	public String visualizeNetworks(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		INetwork network = networkmanager.getNetwork(networkId);
		if(network==null){
			return "auth/accessissue";
		}
		INetworkJSon networkJSon = networkmanager.getJsonForNetworks(networkId, INetworkManager.JITJQUERY);
		String nwId = "\""+networkId+"\"";
		model.addAttribute("networkid",nwId);
		String json = null;
		if(networkJSon!=null){
			json = networkJSon.getJson();
		}
		model.addAttribute("jsonstring",json);
		return "sites/networks/visualize";
	}
}