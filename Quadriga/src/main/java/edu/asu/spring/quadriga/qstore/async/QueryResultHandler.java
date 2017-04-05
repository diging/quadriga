package edu.asu.spring.quadriga.qstore.async;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 
 * Handler class to retrieve the query result from the QStore XML. Also converts
 * character data in the result to markup characters.
 *
 */
public class QueryResultHandler implements DomHandler<String, StreamResult> {

    private static final String PARAMETERS_START_TAG = "<result>";
    private static final String PARAMETERS_END_TAG = "</result>";
    private StringWriter xmlWriter = new StringWriter();

    @Override
    public StreamResult createUnmarshaller(ValidationEventHandler errorHandler) {
        return new StreamResult(xmlWriter);
    }

    @Override
    public String getElement(StreamResult res) {
        String xml = res.getWriter().toString();
        int beginIndex = xml.indexOf(PARAMETERS_START_TAG) + PARAMETERS_START_TAG.length();
        int endIndex = xml.indexOf(PARAMETERS_END_TAG);
        return xml.substring(beginIndex, endIndex);
    }

    @Override
    public Source marshal(String res, ValidationEventHandler errorHandler) {
        String xml = PARAMETERS_START_TAG + res.trim() + PARAMETERS_END_TAG;
        StringReader xmlReader = new StringReader(xml);
        return new StreamSource(xmlReader);
    }

}
