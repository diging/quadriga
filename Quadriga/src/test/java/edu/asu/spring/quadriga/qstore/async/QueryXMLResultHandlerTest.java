package edu.asu.spring.quadriga.qstore.async;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class QueryXMLResultHandlerTest {
    private QStoreAsyncResult asyncRes = null;

    @Before
    public void setup() throws JAXBException {
        String res = "<message><queryStatus>RUNNING</queryStatus><pollurl>query/123</pollurl><result><test>test</test></result></message>";
        JAXBContext jaxbContext = JAXBContext.newInstance(QStoreAsyncResult.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(res);
        asyncRes = (QStoreAsyncResult) unmarshaller.unmarshal(reader);
    }

    @Test
    public void test_getResponseFromXML_asyncresult() {
        Assert.assertEquals("<test>test</test>", asyncRes.getResult());
    }

    @Test
    public void test_getResponseFromXML_pollURL() {
        Assert.assertEquals("query/123", asyncRes.getPollurl());
    }

    @Test
    public void test_getResponseFromXML_status() {
        Assert.assertEquals("RUNNING", asyncRes.getQueryStatus());
    }
}
