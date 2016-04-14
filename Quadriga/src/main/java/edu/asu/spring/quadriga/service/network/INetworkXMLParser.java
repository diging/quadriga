package edu.asu.spring.quadriga.service.network;

import java.io.IOException;

import edu.asu.spring.quadriga.domain.network.INetworkXML;
import edu.asu.spring.quadriga.exceptions.NetworkXMLParseException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface INetworkXMLParser {

    /**
     * 
     * @param xml
     * @param projectid
     * @param workspaceid
     * @return
     * @throws NetworkXMLParseException
     * @throws QuadrigaStorageException
     * @throws IOException
     */
    INetworkXML parseXML(String xml, String projectid, String workspaceid)
            throws NetworkXMLParseException;

}
