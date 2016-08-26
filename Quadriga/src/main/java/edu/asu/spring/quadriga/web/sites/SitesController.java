package edu.asu.spring.quadriga.web.sites;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This Controller is mapped to handle public site page
 * 
 * @author Charan Thej Aware
 * @author Madhu Meghana Talasila
 */

@Controller
public class SitesController {
    @Autowired
    private IRetrieveProjectManager retrieveProjectManager;

    @Autowired
    private Environment env;

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private IJsonCreator jsonCreator;

    /**
     * This method is used to access the public page -sites that enlists the
     * public pages of all the projects
     * 
     * @param locale
     * @param model
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "sites", method = RequestMethod.GET)
    public String showQuadrigaPublicPages(Model model) throws QuadrigaStorageException {
        List<IProject> projectList = retrieveProjectManager.getProjectListByAccessibility(EProjectAccessibility.PUBLIC);
        model.addAttribute("projectList", projectList);
        model.addAttribute("project_baseurl", env.getProperty("project.cite.baseurl"));
        return "sites";
    }

    /**
     * This method is used to access the public page -sites that enlists the
     * public pages of all the projects
     * 
     * @param locale
     * @param model
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "sites/searchTerm", method = RequestMethod.POST)
    public String showPublicProjectsWithSearchTerm(@RequestParam("searchTerm") String searchTerm, Model model)
            throws QuadrigaStorageException {
        List<IProject> projectList = retrieveProjectManager.getProjectListBySearchTermAndAccessiblity(searchTerm,
                EProjectAccessibility.PUBLIC.name());
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("projectList", projectList);
        model.addAttribute("project_baseurl", env.getProperty("project.cite.baseurl"));
        return "searchsites";
    }

    /**
     * This method loads all the public networks and navigates to the public
     * networks page to display the graph
     * 
     * @param model
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "sites/network", method = RequestMethod.GET)
    public String showPublicNetworks(Model model) throws QuadrigaStorageException {
        List<INetwork> networkList = networkManager.getApprovedNetworkList();
        ITransformedNetwork transformedNetwork = transformationManager.getTransformedApprovedNetworks(networkList);

        String json = null;
        if (transformedNetwork != null) {
            json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        }
        
        if (transformedNetwork == null || transformedNetwork.getNodes().size() == 0) {
            model.addAttribute("isNetworkEmpty", true);
        }
        
        model.addAttribute("jsonstring", json);

        return "sites/network";
    }
}