package edu.asu.spring.quadriga.web.sites;

import static edu.asu.spring.quadriga.qstore.ExecutionStatus.COMPLETED;
import static edu.asu.spring.quadriga.qstore.ExecutionStatus.FAILED;
import static edu.asu.spring.quadriga.qstore.ExecutionStatus.RUNNING;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.exceptions.AsyncExecutionException;
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
     * This method navigates to the public networks page to display the graph
     * 
     * @return
     */
    @RequestMapping(value = "sites/network", method = RequestMethod.GET)
    public String showPublicNetworks() {

        return "sites/network";
    }

    /**
     * This method returns a JSON String that represents all the public networks
     * 
     * @throws QStoreStorageException
     * @throws JAXBException
     * @throws QuadrigaStorageException
     * 
     */
    @RequestMapping(value = "public/networks", method = RequestMethod.GET)
    public ResponseEntity<String> getPublicNetwork()
            throws QStoreStorageException, QuadrigaStorageException, JAXBException {
        String xml = qStoreAsyncTaskManager.getNetworkWithPopularTerms();
        ITransformedNetwork transformedNetwork = transformationManager.getAllTransformedNetworks(xml);

        String json = null;
        if (transformedNetwork != null && transformedNetwork.getNodes().size() != 0) {
            json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * This method starts asynchronous query execution
     * 
     * @return
     */
    @RequestMapping(value = "public/loadnetworks", method = RequestMethod.GET)
    public ResponseEntity<String> loadNetworks() {
        try {
            qStoreAsyncTaskManager.startLoadingPublicNetworks();
        } catch (AsyncExecutionException e) {
            return new ResponseEntity<>(FAILED.name() + " with exception" + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(RUNNING.name(), HttpStatus.OK);
    }

    /**
     * This method tells if the asynchronous query has loaded the public
     * networks or not
     * 
     * @return
     */
    @RequestMapping(value = "public/networkloadstatus", method = RequestMethod.GET)
    public ResponseEntity<String> getPublicNetworkLoadStatus() {
        if (qStoreAsyncTaskManager.getPublicNetworkStatus()) {
            return new ResponseEntity<>(COMPLETED.name(), HttpStatus.OK);
        }
        return new ResponseEntity<>(RUNNING.name(), HttpStatus.OK);

    }
}
