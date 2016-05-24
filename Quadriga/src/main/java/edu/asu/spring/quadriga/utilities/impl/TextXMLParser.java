package edu.asu.spring.quadriga.utilities.impl;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.factory.workspace.ITextFileFactory;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.TextFileParseException;
import edu.asu.spring.quadriga.utilities.ITextXMLParser;

/**
 * Implementation class for ITextXMLParser
 * 
 * @author Nischal Samji
 *
 */
@Service
public class TextXMLParser implements ITextXMLParser {

    @Autowired
    private ITextFileFactory txtFileFactory;

    private static final Logger logger = LoggerFactory.getLogger(TextXMLParser.class);

    @Override
    public ITextFile parseTextXML(String xml, String wsId, String projId) throws TextFileParseException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        try {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(xml)));
        } catch (ParserConfigurationException | IOException e) {
            logger.error("Error while parsing the XML");
            throw new TextFileParseException(e);
        } catch (SAXException sae) {
            logger.error("Error while parsing the XML");
            throw new TextFileParseException(sae);
        }

        NodeList textNodeList = document.getElementsByTagName("text");
        NodeList handleNode = document.getElementsByTagName("handle");
        NodeList fileNameNode = document.getElementsByTagName("file_name");
        NodeList accessibilityNode = document.getElementsByTagName("accessibility");

        if (handleNode.getLength() == 0 && fileNameNode.getLength() == 0) {
            throw new TextFileParseException("Handle and file name must be specified in the input XML.");
        } else if (handleNode.getLength() == 0) {
            throw new TextFileParseException("Handle must be specified in the input XML.");
        } else if (fileNameNode.getLength() == 0) {
            throw new TextFileParseException("File name must be specified in the input XML.");
        } else if (textNodeList.getLength() == 0) {
            throw new TextFileParseException("File content must be specified in the input XML.");
        } else if (accessibilityNode.getLength() == 0) {
            throw new TextFileParseException("Accessibility must be specified in the input XML.");
        }

        String fileContent = textNodeList.item(0).getTextContent();
        String fileName = fileNameNode.item(0).getTextContent();
        String refId = handleNode.item(0).getTextContent();
        String accessibility = accessibilityNode.item(0).getTextContent();

        if (accessibility.isEmpty()) {
            throw new TextFileParseException("Specify accessibility options in the XML.");
        } else if (!(accessibility.equalsIgnoreCase(ETextAccessibility.PUBLIC.name()) || accessibility.equalsIgnoreCase(ETextAccessibility.PRIVATE.name()))) {
            throw new TextFileParseException("Please set the proper accessibility option.");
        }

        if (fileContent.isEmpty()) {
            throw new TextFileParseException("Specify File Content in the XML.");
        } else if (fileName.isEmpty()) {
            throw new TextFileParseException("Filename cannot be empty.");
        } else if (refId.isEmpty()) {
            throw new TextFileParseException("Handle cannot be empty.");
        }

        ITextFile txtFile = txtFileFactory.createTextFileObject();
        txtFile.setFileContent(StringEscapeUtils.unescapeXml(fileContent));
        txtFile.setFileName(fileName);
        txtFile.setRefId(refId);
        txtFile.setWorkspaceId(wsId);
        txtFile.setProjectId(projId);
        txtFile.setAccessibility(ETextAccessibility.valueOf(accessibility.toUpperCase()));

        return txtFile;
    }

}
