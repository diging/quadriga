package edu.asu.spring.quadriga.web.sites;

import static edu.asu.spring.quadriga.qstore.ExecutionStatus.COMPLETED;
import static edu.asu.spring.quadriga.qstore.ExecutionStatus.FAILED;
import static edu.asu.spring.quadriga.qstore.ExecutionStatus.RUNNING;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.qstore.async.IQStoreAsyncTaskManager;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

@Controller
public class PublicNetworkController {

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private IJsonCreator jsonCreator;

    @Autowired
    private IQStoreAsyncTaskManager qStoreAsyncTaskManager;

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

        String xml = qStoreAsyncTaskManager.getNetworkWithPopularTerms();
        ITransformedNetwork transformedNetwork = transformationManager.getAllTransformedNetworks(xml);

        String json = null;
        if (transformedNetwork != null && transformedNetwork.getNodes().size() != 0) {
            json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        } else {
            model.addAttribute("isNetworkEmpty", true);
        }

        model.addAttribute("jsonstring", json);

        return "sites/network";
    }

    /**
     * This method starts asynchronous query execution
     * 
     * @return
     */
    @RequestMapping(value = "rest/loadnetworks", method = RequestMethod.GET)
    public ResponseEntity<String> loadNetworks() {
        try {
            qStoreAsyncTaskManager.startLoadingPublicNetworks();
        } catch (QStoreStorageException e) {
            return new ResponseEntity<>(FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(RUNNING.name(), HttpStatus.OK);
    }

    /**
     * This method tells if the asynchronous query has loaded the public
     * networks or not
     * 
     * @return
     */
    @RequestMapping(value = "rest/publicnetworkloadstatus", method = RequestMethod.GET)
    public ResponseEntity<String> getPublicNetworkLoadStatus() {
        if (qStoreAsyncTaskManager.getPublicNetworkStatus()) {
            return new ResponseEntity<>(COMPLETED.name(), HttpStatus.OK);
        }
        return new ResponseEntity<>(RUNNING.name(), HttpStatus.OK);

    }
}
