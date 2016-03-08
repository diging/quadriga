package edu.asu.spring.quadriga.web.publicwebsite;

/**
 * This controller has all the mappings required to view the external website of a project, view all the networks in that project
 * and visualize the networks 
 * 
 * @author Sayalee Mehendale
 *
 */


import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.ui.context.Theme;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import edu.asu.spring.quadriga.domain.impl.networks.Network;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.impl.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;

@PropertySource(value = "classpath:/user.properties")
@Controller
public class WebsiteProjectController {
	
	@Autowired 
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private INetworkManager networkmanager;
	
	@Autowired
    private INetworkTransformationManager transformationManager;
    
    @Autowired
    private ID3Creator d3Creator;
	
	@Autowired
    private Environment env;
	
	public IRetrieveProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(IRetrieveProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	
	private IProject getProjectDetails(String name) throws QuadrigaStorageException {
		return projectManager.getProjectDetailsByUnixName(name);
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
		
		String user = null;
		if(principal!=null){
			user = principal.getName();
		}
		
		IProject project = getProjectDetails(unixName);
		
		model.addAttribute("project_baseurl", env.getProperty("project.cite.baseurl"));
		
		if (project == null)
		    return "forbidden";
		
		if(user == null) {
			if(projectManager.getPublicProjectWebsiteAccessibility(unixName)){
				model.addAttribute("project", project);
				return "sites/website";
			}
			else {
				return "forbidden";
			}
		}
	
		
		if(projectManager.getPrivateProjectWebsiteAccessibility(unixName, user)
				|| projectManager.getPublicProjectWebsiteAccessibility(unixName)) {
			model.addAttribute("project", project);
			return "sites/website";
		}
		else {
			return "forbidden";	
		}
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
		IProject project = getProjectDetails(unixName);
		String projectid = project.getProjectId();
		List<INetwork> Networks = networkmanager.getNetworksInProject(projectid);
		
		//List<String> networkNames = null;
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
	@RequestMapping(value = "sites/{projectUnixName}/networks/{networkId}", method = RequestMethod.GET)
	public String visualizeNetworks(@PathVariable("projectUnixName") String unixName, @PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException {
		INetwork network = networkmanager.getNetwork(networkId);
		if(network==null){
			return "auth/accessissue";
		}
		IProject project = getProjectDetails(unixName);
		model.addAttribute("project", project);
		
		ITransformedNetwork transformedNetwork = transformationManager.getTransformedNetwork(networkId);

		// test the transformed networks

		
		String nwId = "\""+networkId+"\"";
		model.addAttribute("networkid",nwId);
		String json = null;
		if(transformedNetwork!=null){
			json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
		}
		model.addAttribute("jsonstring",json);
		return "sites/networks/visualize";
	}

	/**
	 * This method gives the visualization of all the networks in a project
	 * @param projectUnixName	The project unix name
	 * @param model				Model
	 * @return view
	 * @throws JAXBException
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "sites/{projectUnixName}/networks", method = RequestMethod.GET)
	public String visualizeAllNetworks(@PathVariable("projectUnixName") String projectUnixName,
									   Model model)
			throws JAXBException, QuadrigaStorageException {
		IProject project = getProjectDetails(projectUnixName);

		if (project == null) {
			return "auth/accessissue";
		}

		ITransformedNetwork transformedNetwork = transformationManager.getTransformedNetworkOfProject(project.getProjectId());

		String json = null;
		if (transformedNetwork != null) {
			json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
		}

		model.addAttribute("jsonstring", json);
		model.addAttribute("networkid", "\"\"");
		model.addAttribute("project", project);

		return "sites/networks/visualize";
	}
	
	public static JSONArray sortByValue(Map<String, Integer> map,
	        Map<String, String[]> map2) throws JSONException {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
		        map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			public int compare(Map.Entry<String, Integer> m1,
			        Map.Entry<String, Integer> m2) {
				return (m2.getValue()).compareTo(m1.getValue());
			}
		});

		JSONArray obj = new JSONArray();

		for (int k = 0; k < (list.size() > 10 ? 10 : list.size()); k++) {
			Map.Entry<String, Integer> entry = list.get(k);
			String[] temp = map2.get(entry.getKey());
			JSONObject jo = new JSONObject();
			jo.put("conceptId", entry.getKey());
			jo.put("description", temp[0]);
			jo.put("label", temp[1]);
			jo.put("frequency", entry.getValue());
			obj.put(jo);
		}
		return obj;
	}

	/**
	 * This method gives the visualization of  how often concepts appear in the networks
	 * @author Bharath Srikantan & Ajay Modi
	 * @param projectUnixName	The project unix name
	 * @param model				Model
	 * @return view
	 * @throws JAXBException
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "sites/{projectUnixName}/statistics", method = RequestMethod.GET)
	public String showProjectStatistics(
	        @PathVariable("projectUnixName") String projectUnixName, Model model)
	        throws JAXBException, QuadrigaStorageException {
		IProject project = getProjectDetails(projectUnixName);

		if (project == null) {
			return "auth/accessissue";
		}

		String projectid = project.getProjectId();
		List<INetwork> Networks = networkmanager
		        .getNetworksInProject(projectid);
		HashMap<String, Integer> top10Concepts = new HashMap<String, Integer>();
		HashMap<String, String[]> top10ConceptsDetails = new HashMap<String, String[]>();
		JSONArray jo = new JSONArray();
		StringBuffer errorMsg = new StringBuffer();
		StringBuffer successMsg = new StringBuffer();
		// List<String> networkNames = null;
		if (!Networks.isEmpty()) {

			for (int i = 0; i < Networks.size(); i++) {
				Network n = (Network) Networks.get(i);
				ITransformedNetwork transformedNetwork = transformationManager
				        .getTransformedNetwork(n.getNetworkId());
				String json = null;
				if (transformedNetwork != null) {
					List<Link> links = transformedNetwork.getLinks();
					for (int itr = 0; itr < links.size(); itr++) {
						Node s = links.get(itr).getSubject();
						String url = s.getConceptId();
						String des = s.getDescription();
						String lbl = s.getLabel();
						if (top10Concepts.containsKey(url)) {
							top10Concepts.put(url, top10Concepts.get(url) + 1);
						} else {
							top10Concepts.put(url, 1);
							top10ConceptsDetails.put(url, new String[] { des,
							        lbl });
						}
					}

				}

			}
			try {
				jo = sortByValue(top10Concepts, top10ConceptsDetails);
				model.addAttribute("jsonstring", jo);
				model.addAttribute("networkid", "\"\"");
				model.addAttribute("project", project);

			} catch (JSONException e) {
				model.addAttribute("show_error_alert", true);
				errorMsg.append(e.getMessage());
				errorMsg.append("\n");

			}
		}

		model.addAttribute("error_alert_msg", errorMsg.toString());

		return "sites/project/statistics";
	}
}