package edu.asu.spring.quadriga.service.passthroughproject;

import edu.asu.spring.quadriga.domain.impl.passthroughproject.XMLInfo;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;

public interface IXMLReader {

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
     * submitted and sets it to a {@link XMLInfo} Object.
     * 
     * @param xml
     *            The XML submitted to the Quadriga.
     * @return The {@link XMLInfo} Object.
     * @throws DocumentParserException
     */
    XMLInfo getXMLInfo(String xml) throws DocumentParserException;

    /**
     * Identifies if the given XML is contains passthrough project information.
     * 
     * @param xml
     */
    boolean isPassThroughXML(String xml);

}
