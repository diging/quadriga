package edu.asu.spring.quadriga.service.passthroughproject;

import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProjectInfo;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;

public interface IPassThroughProjectDocumentReader {

    /**
     * This method retrieves the network from the XML submitted to Quadriga.
     * 
     * @param xml
     *            The XML submitted to the Quadriga.
     * @return The network that is extracted from the XML.
     */
    String getNetwork(String xml);

    /**
     * This method retrieves all the necessary information from the XML
     * submitted and sets it to a {@link PassThroughProjectInfo} Object.
     * 
     * @param xml
     *            The XML submitted to the Quadriga.
     * @return The {@link PassThroughProjectInfo} Object.
     * @throws DocumentParserException
     */
    PassThroughProjectInfo getPassThroughProjectInfo(String xml) throws DocumentParserException;

}
