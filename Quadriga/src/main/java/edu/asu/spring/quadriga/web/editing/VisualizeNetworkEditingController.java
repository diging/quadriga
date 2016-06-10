package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class VisualizeNetworkEditingController {

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private ID3Creator d3Creator;

    /**
     * Get the network displayed on to JSP by passing JSON string on editing
     * page
     * 
     * @author Lohith Dwaraka
     * @param networkId
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws JAXBException
     */
    @AccessPolicies({
            @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 0, userRole = { RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR }),
            @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 0, userRole = {
                    RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR }),
            @ElementAccessPolicy(type = CheckedElementType.NETWORK, paramIndex = 1, userRole = {}) })
    @RequestMapping(value = "auth/editing/visualize/{networkId}", method = RequestMethod.GET)
    public String visualizeNetworks(@PathVariable("networkId") String networkId, ModelMap model, Principal principal)
            throws QuadrigaStorageException, JAXBException, QuadrigaAccessException {
        INetwork network = networkManager.getNetwork(networkId);
        if (network == null) {
            return "auth/accessissue";
        }
        ITransformedNetwork transformedNetwork = transformationManager.getTransformedNetwork(networkId);
        String nwId = "\"" + networkId + "\"";
        model.addAttribute("networkid", nwId);
        String json = "";
        if (transformedNetwork != null) {
            json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        }
        model.addAttribute("network", network);
        model.addAttribute("jsonstring", json);

        return "auth/editing/visualize";
    }
}
