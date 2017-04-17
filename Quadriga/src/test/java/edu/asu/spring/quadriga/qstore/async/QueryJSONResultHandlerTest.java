package edu.asu.spring.quadriga.qstore.async;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Assert;

public class QueryJSONResultHandlerTest {
    private QStoreAsyncResult asyncRes = null;

    @Before
    public void setup() throws JAXBException, JSONException, JsonParseException, JsonMappingException, IOException {
        String res = "{\"message\" : { \"queryStatus\" : \"RUNNING\", \"pollurl\" : \"query/123\", \"result\": { \"test\" : \"test\" } }}";
        JSONObject json = new JSONObject(res);
        ObjectMapper mapper = new ObjectMapper();
        asyncRes = mapper.readValue(json.getString("message"), QStoreAsyncResult.class);
    }

    @Test
    public void test_getResponseFromXML_asyncresult() {
        Assert.assertEquals("{\"test\":\"test\"}", asyncRes.getResult());
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
