 package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.CheckAccess;
import edu.asu.spring.quadriga.aspects.annotations.CheckPublicAccess;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectByName;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

@Controller
public class VisualizeNetworkController {

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private ID3Creator d3Creator;

    @Autowired
    private INetworkManager networkmanager;

    /**
     * This method gives the visualization of the network with the given network
     * id
     * 
     * @param networkId
     *            network id of the network that has to be visualized
     * @param model
     *            ModelMap object to map values to view
     * @param principal
     *            current session user
     * @return returns a string to access the visualize network page of the
     *         project external website
     * @throws QuadrigaStorageException
     *             Database storage exception thrown
     * @throws JAXBException
     *             JAXB exception while getting the JSON
     */
    @CheckPublicAccess
    @InjectProjectByName
    @RequestMapping(value = "sites/{projectUnixName}/networks/{networkId}", method = RequestMethod.GET)
    public String visualizeNetworks(@ProjectIdentifier @PathVariable("projectUnixName") String unixName,
            @PathVariable("networkId") String networkId, ModelMap model, Principal principal,
            @CheckAccess @InjectProject IProject project) throws QuadrigaStorageException, JAXBException {
        INetwork network = networkmanager.getNetwork(networkId);
        if (network == null) {
            return "public/404";
        }
        model.addAttribute("project", project);
        model.addAttribute("network", network);
        model.addAttribute("unixName", unixName);
        
        ITransformedNetwork transformedNetwork = transformationManager.getTransformedNetwork(networkId);

        // test the transformed networks

        String nwId = "\"" + networkId + "\"";
        model.addAttribute("networkid", nwId);
        String json = null;
        if (transformedNetwork != null) {
            json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        }
        model.addAttribute("jsonstring", json);
        return "sites/networks/visualize";
    }
}
