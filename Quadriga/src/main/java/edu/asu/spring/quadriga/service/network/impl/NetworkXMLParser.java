package edu.asu.spring.quadriga.service.network.impl;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.asu.spring.quadriga.service.network.INetworkXMLParser;

@Service
public class NetworkXMLParser implements INetworkXMLParser {

	@Override
	public String storeText(String xml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xml)));
			Node textNode = document.getElementsByTagName("text").item(0);
			if (textNode == null) {
				return "";
			}
			System.out.println(textNode.getTextContent());
			NodeList list = document.getElementsByTagName("refId");
			for (int temp = 0; temp < list.getLength(); temp++) {
				Node curNode = list.item(temp);
				System.out.println(curNode.getTextContent());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
