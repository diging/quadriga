package edu.asu.spring.quadriga.profile.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceRegistry;

public class ServiceRegistryTest {
	
	@Mock
	ApplicationContext ctx;
	
	@InjectMocks
    private ServiceRegistry serviceRegistry;
    
	private Map<String,String> serviceNameIdMap;
	
	private ViafService viafService;
	private ConceptPowerService conceptpowerService;
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	    ctx = Mockito.mock(ApplicationContext.class);
	    MockitoAnnotations.initMocks(this);
	    
	    viafService = new ViafService();
	    conceptpowerService = new ConceptPowerService();
	    Map<String, IService> beanMap = new HashMap<String, IService>();
	    beanMap.put("conceptPowerService", conceptpowerService);
	    beanMap.put("viafService", viafService);
	    
	    Mockito.when(ctx.getBeansOfType(IService.class)).thenReturn(beanMap);
		
	    serviceRegistry.init();
	}
	
	

	
	@Test
	public void testGetServiceNameIdMap() {
		
		Map<String, String> services = new HashMap<String, String>();
		services.put(viafService.getServiceId(), viafService.getName());
		services.put(conceptpowerService.getServiceId(), conceptpowerService.getName());
		
		assertEquals(serviceRegistry.getServiceIdNameMap(), services);
		
	}

	@Test
	public void testGetService() {
	    assertEquals(viafService, serviceRegistry.getServiceObject(viafService.getServiceId()));
	    assertEquals(conceptpowerService, serviceRegistry.getServiceObject(conceptpowerService.getServiceId()));
	}

}
