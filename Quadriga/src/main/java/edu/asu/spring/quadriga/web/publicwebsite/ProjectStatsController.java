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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.asu.spring.quadriga.domain.impl.networks.Network;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.impl.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;

@PropertySource(value = "classpath:/user.properties")
@Controller
public class ProjectStatsController {
	
	@Autowired 
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private INetworkManager networkmanager;
	
	@Autowired
    private INetworkTransformationManager transformationManager;
    	
	public IRetrieveProjectManager getProjectManager() {
		return projectManager;
	}
	
	private IProject getProjectDetails(String name) throws QuadrigaStorageException {
		return projectManager.getProjectDetailsByUnixName(name);
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
		// List<String> networkNames = null;
		if (!Networks.isEmpty()) {

			for (int i = 0; i < Networks.size(); i++) {
				Network n = (Network) Networks.get(i);
				ITransformedNetwork transformedNetwork = transformationManager
				        .getTransformedNetwork(n.getNetworkId());
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