package edu.asu.spring.quadriga.utilities;

import org.springframework.http.ResponseEntity;

/**
 * @author Nischal Samji Interface for Network Utilities used across multiple
 *         classes.
 *
 */
public interface INetworkUtils {

    /**
     * This method takes a network id and returns the network json as a Response
     * Entity
     * 
     * @param networkId
     *            Id of the network that needs to be returned.
     * @return
     */
    public ResponseEntity<String> sendNetworkAsResponseEntity(String networkId);
}
