package edu.asu.spring.quadriga.utilities.impl;

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

import edu.asu.spring.quadriga.domain.factory.workspace.ITextFileFactory;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.TextFileParseException;
import edu.asu.spring.quadriga.utilities.ITextXMLParser;

/**
 * Implementation class for ITextXMLParser
 * @author Nischal Samji
 *
 */
@Service
public class TextXMLParser implements ITextXMLParser {

    @Autowired
    private ITextFileFactory txtFileFactory;

    @Override
    public ITextFile parseTextXML(String xml, String wsId, String projId) throws TextFileParseException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;
        Document document = null;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(xml)));
        } catch (ParserConfigurationException | IOException e) {
            throw new TextFileParseException(e);
        } catch (SAXException sae) {
            throw new TextFileParseException("This XML file is not well formed");
        }

        NodeList textNodeList = document.getElementsByTagName("text");
        NodeList handleNode = document.getElementsByTagName("handle");
        NodeList fileNameNode = document.getElementsByTagName("file_name");

        if (handleNode.getLength() == 0 && fileNameNode.getLength() == 0) {
            throw new TextFileParseException("Handle and file name must be specified in the input XML");
        } else if (handleNode.getLength() == 0) {
            throw new TextFileParseException("Handle must be specified in the input XML");
        } else if (fileNameNode.getLength() == 0) {
            throw new TextFileParseException("File name must be specified in the input XML");
        } else if (textNodeList.getLength() == 0) {
            throw new TextFileParseException("File content must be specified in the input XML");
        }

        String fileContent = textNodeList.item(0).getTextContent();
        String fileName = fileNameNode.item(0).getTextContent();
        String refId = handleNode.item(0).getTextContent();
        ITextFile txtFile = txtFileFactory.createTextFileObject();
        txtFile.setFileContent(fileContent);
        txtFile.setFileName(fileName);
        txtFile.setRefId(refId);
        txtFile.setWorkspaceId(wsId);
        txtFile.setProjectId(projId);

        return txtFile;
    }

}
