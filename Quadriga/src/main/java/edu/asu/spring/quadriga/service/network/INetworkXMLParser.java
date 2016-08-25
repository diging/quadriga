package edu.asu.spring.quadriga.service.network;

import java.io.IOException;

import edu.asu.spring.quadriga.domain.network.INetworkXML;
import edu.asu.spring.quadriga.exceptions.NetworkXMLParseException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.TextFileParseException;

public interface INetworkXMLParser {

    /**
     * Interface for XML Parsing Methods to be used in Rest services
     * 
     * @param xml
     *        XML content from rest interface to be parsed.
     * @param projectid
     *        Project id of the Network to be saved.
     * @param workspaceid
     *        Workspace id of the network to be saved.
     * @return
     *        Returns a NetworkXML Object that contains Textfile and Network XML.
     * @throws NetworkXMLParseException
     * @throws TextFileParseException 
     * @throws QuadrigaStorageException
     * @throws IOException
     */
    INetworkXML parseXML(String xml, String projectid, String workspaceid)
            throws NetworkXMLParseException, TextFileParseException;

}
