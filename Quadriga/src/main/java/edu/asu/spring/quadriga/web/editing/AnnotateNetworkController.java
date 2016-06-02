package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

@Controller
public class AnnotateNetworkController {

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private ID3Creator d3Creator;

    
    /**
     * Get the network displayed on to JSP by passing JSON string and allow to add annotations 
     * @author Lohith Dwaraka
     * @param networkId
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws JAXBException
     * @throws JSONException 
     * @throws QStoreStorageException 
     */
    @RequestMapping(value = "auth/editing/editnetworks/{networkId}", method = RequestMethod.GET)
    public String visualizeAndEditNetworksByD3(@PathVariable("networkId") String networkId, ModelMap model, Principal principal) throws QuadrigaStorageException, JAXBException, JSONException, QStoreStorageException {
        INetwork network = networkManager.getNetwork(networkId);
        if(network==null){
            return "auth/404";
        }
        ITransformedNetwork transformedNetwork = transformationManager.getTransformedNetwork(networkId);

        String nwId = "\""+networkId+"\"";
        model.addAttribute("networkid",nwId);
        String json = null;
        if(transformedNetwork!=null){
            json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        }
        model.addAttribute("network", network);
        model.addAttribute("jsonstring",json);
        return "auth/editing/editnetworks";
    }   
    
}
