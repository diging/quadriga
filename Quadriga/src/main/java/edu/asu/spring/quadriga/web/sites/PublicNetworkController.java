package edu.asu.spring.quadriga.web.sites;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

@Controller
public class PublicNetworkController {

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private IJsonCreator jsonCreator;

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
