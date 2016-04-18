package edu.asu.spring.quadriga.service.network.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.factory.networks.INetworkXMLFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;
import edu.asu.spring.quadriga.domain.network.INetworkXML;
import edu.asu.spring.quadriga.exceptions.NetworkXMLParseException;
import edu.asu.spring.quadriga.service.network.INetworkXMLParser;

/**
 * 
 * @author skollur1
 *
 */
@Service
public class NetworkXMLParser implements INetworkXMLParser {

    @Autowired
    private INetworkXMLFactory nwXMLFactory;

    private INetworkXML networkXML;

    @Override
    @Transactional
    public INetworkXML parseXML(String xml, String projectid, String workspaceid) throws NetworkXMLParseException {
        
        networkXML = nwXMLFactory.createNetworkXMLObject();
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
            networkXML.setNetworkXMLString(xml);
            return networkXML;
        }
        String text = textNodeList.item(0).getTextContent();

        NodeList handle = document.getElementsByTagName("handle");
        NodeList fileName = document.getElementsByTagName("file_name");
        if (handle.getLength() == 0 && fileName.getLength() == 0) {
            throw new NetworkXMLParseException("Handle and file name must be specified in the input XML");
        } else if (handle.getLength() == 0) {
            throw new NetworkXMLParseException("Handle must be specified in the input XML");
        } else if (fileName.getLength() == 0) {
            throw new NetworkXMLParseException("File name must be specified in the input XML");
        }

        String handleID = handle.item(0).getTextContent();
        String filename = fileName.item(0).getTextContent();
        TextFile txtfile = new TextFile();
        txtfile.setFileContent(text);
        txtfile.setFileName(filename);
        txtfile.setProjectId(projectid);
        txtfile.setWorkspaceId(workspaceid);
        txtfile.setRefId(handleID);

        networkXML.setTextFile(txtfile);

        Element e = document.getDocumentElement();
        e.removeChild(textNodeList.item(0));
        e.removeChild(handle.item(0));
        e.removeChild(fileName.item(0));
        networkXML.setNetworkXMLString(documentToString(document));
        return networkXML;

    }

    private String documentToString(Document doc) throws NetworkXMLParseException {
        DOMSource domSource = new DOMSource(doc);
        Transformer transformer;
        StringWriter sw = new StringWriter();
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult sr = new StreamResult(sw);
            transformer.transform(domSource, sr);
        } catch (TransformerFactoryConfigurationError | TransformerException e) {
            throw new NetworkXMLParseException("Error Converting document to XML");
        }
        return sw.toString();
    }

}
