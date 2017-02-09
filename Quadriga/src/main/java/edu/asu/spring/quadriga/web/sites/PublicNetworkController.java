package edu.asu.spring.quadriga.web.sites;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

@Controller
public class PublicNetworkController {

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
     * @throws QStoreStorageException
     * @throws JAXBException
     */
    @RequestMapping(value = "sites/network", method = RequestMethod.GET)
    public String showPublicNetworks(Model model)
            throws QuadrigaStorageException, QStoreStorageException, JAXBException {

        ITransformedNetwork transformedNetwork = transformationManager.getAllTransformedNetworks();

        String json = null;
        if (transformedNetwork != null && transformedNetwork.getNodes().size() != 0) {
            json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        } else {
            model.addAttribute("isNetworkEmpty", true);
        }

        model.addAttribute("jsonstring", json);

        return "sites/network";
    }
}
