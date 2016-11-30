package edu.asu.spring.quadriga.utilities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.utilities.INetworkUtils;

@Service
public class NetworkUtils implements INetworkUtils {

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private IJsonCreator jsonCreator;

    private final Logger logger = LoggerFactory.getLogger(NetworkUtils.class);

    @Override
    public ResponseEntity<String> sendNetworkAsResponseEntity(String networkId) {

        ITransformedNetwork transformedNetwork = null;
        try {
            transformedNetwork = transformationManager.getTransformedNetwork(networkId);
        } catch (QuadrigaStorageException qse) {
            logger.error("Error while retrieving networks for display:", qse);
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String json = null;
        if (transformedNetwork != null) {
            json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        } else {
            return new ResponseEntity<String>(json, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(json, HttpStatus.OK);

    }

}
