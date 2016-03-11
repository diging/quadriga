package edu.asu.spring.quadriga.service.network.impl;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.asu.spring.quadriga.exceptions.NetworkXMLParseException;
import edu.asu.spring.quadriga.service.network.INetworkXMLParser;

/**
 * 
 * @author skollur1
 *
 */
@Service
public class NetworkXMLParser implements INetworkXMLParser {

    @Override
    public String storeText(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            NodeList textNodeList = document.getElementsByTagName("text");
            if (textNodeList.getLength() == 0) {
                return "Success";
            }
            String text = textNodeList.item(0).getTextContent();

            NodeList handle = document.getElementsByTagName("handle");
            NodeList fileName = document.getElementsByTagName("file_name");
            if (handle.getLength() == 0 && fileName.getLength() == 0) {
                throw new NetworkXMLParseException("Please specify the Handle and file name in the input XML ");
            } else if (handle.getLength() == 0) {
                throw new NetworkXMLParseException("Please specify the Handle in the input XML ");
            } else if (fileName.getLength() == 0) {
                throw new NetworkXMLParseException("Please specify the file name in the input XML ");
            }

            String handleID = handle.item(0).getTextContent();
            String filename = fileName.item(0).getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
