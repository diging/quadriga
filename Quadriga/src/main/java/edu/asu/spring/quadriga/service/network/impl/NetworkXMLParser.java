package edu.asu.spring.quadriga.service.network.impl;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;
import edu.asu.spring.quadriga.exceptions.NetworkXMLParseException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkXMLParser;
import edu.asu.spring.quadriga.service.textfile.impl.TextFileManager;

/**
 * 
 * @author skollur1
 *
 */
@Service
public class NetworkXMLParser implements INetworkXMLParser {

    @Autowired
    private TextFileManager txtFileManager;

    @Override
    public String storeText(String xml, String projectid, String workspaceid)
            throws NetworkXMLParseException, QuadrigaStorageException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;
        Document document = null;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(xml)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new NetworkXMLParseException(e);
        }

        NodeList textNodeList = document.getElementsByTagName("text");
        if (textNodeList.getLength() == 0) {
            return "Success";
        }
        String text = textNodeList.item(0).getTextContent();

        NodeList handle = document.getElementsByTagName("handle");
        NodeList fileName = document.getElementsByTagName("file_name");
        if (handle.getLength() == 0 && fileName.getLength() == 0) {
            throw new NetworkXMLParseException("The Handle and file name must be specified in the input XML ");
        } else if (handle.getLength() == 0) {
            throw new NetworkXMLParseException("Handle must be specified in the input XML ");
        } else if (fileName.getLength() == 0) {
            throw new NetworkXMLParseException("File name must be specified in the input XML ");
        }

        String handleID = handle.item(0).getTextContent();
        String filename = fileName.item(0).getTextContent();
        TextFile txtfile = new TextFile();
        txtfile.setFileContent(text);
        txtfile.setFileName(filename);
        txtfile.setProjectId(projectid);
        txtfile.setWorkspaceId(workspaceid);
        txtfile.setRefId(handleID);

        return txtFileManager.saveTextFile(txtfile) ? "Success" : null;

    }

}
