package edu.asu.spring.quadriga.qstore;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import edu.asu.spring.quadriga.exceptions.AsyncExecutionException;
import edu.asu.spring.quadriga.qstore.impl.QStoreConnector;

@RunWith(PowerMockRunner.class)
@PrepareForTest(QStoreConnector.class)
public class QStoreConnectorTest {

    @InjectMocks
    private QStoreConnector connector;

    @Mock
    private Environment env;

    private RestTemplate template = mock(RestTemplate.class);

    private String res = "";

    private String qStoreURL = "http://localhost:8080/qstore";
    private String qStoreURLQuery = "/query";

    private String url = qStoreURL + qStoreURLQuery;

    @Before
    public void setup() throws Exception {

        PowerMockito.whenNew(RestTemplate.class).withNoArguments().thenReturn(template);

        res = "<message><queryStatus>COMPLETED</queryStatus><pollurl>/query/123</pollurl><result><test>test</test></result></message>";

        when(template.postForEntity(
                eq(UriComponentsBuilder.fromHttpUrl(url).queryParam("class", "relationevent").build().encode().toUri()),
                any(), eq(String.class))).thenReturn(new ResponseEntity<String>(res, HttpStatus.OK));

        String queryUrl = url + "/123";

        when(template.exchange(eq(queryUrl), eq(HttpMethod.GET), any(), eq(String.class),
                Matchers.<Object> anyVararg())).thenReturn(new ResponseEntity<String>(res, HttpStatus.OK));

        when(env.getProperty("allNetworks")).thenReturn("");
        when(env.getProperty("qstore.rest.delay")).thenReturn("500");

        ReflectionTestUtils.setField(connector, "qStoreURL", qStoreURL);
        ReflectionTestUtils.setField(connector, "qStoreURLQuery", qStoreURLQuery);
    }

    @Test
    public void test_loadNetworkWithPopularTerms_result()
            throws AsyncExecutionException, InterruptedException, ExecutionException {
        Future<String> futureres = connector.loadNetworkWithPopularTerms();
        Assert.assertEquals("<test>test</test>", futureres.get());

    }

    @Test(expected = AsyncExecutionException.class)
    public void test_loadNetworkWihPopularTerms_exception() throws AsyncExecutionException {
        res = "<message><badxml>";
        when(template.postForEntity(
                eq(UriComponentsBuilder.fromHttpUrl(url).queryParam("class", "relationevent").build().encode().toUri()),
                any(), eq(String.class))).thenReturn(new ResponseEntity<String>(res, HttpStatus.OK));
        connector.loadNetworkWithPopularTerms();
    }
}
