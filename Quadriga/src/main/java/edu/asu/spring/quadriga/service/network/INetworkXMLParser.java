package edu.asu.spring.quadriga.service.network;

import java.io.IOException;

import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.NetworkXMLParseException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface INetworkXMLParser {

    String storeText(String xml, String projectid, String workspaceid)
            throws NetworkXMLParseException, QuadrigaStorageException, IOException, FileStorageException;

}
