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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceRegistry;

@ContextConfiguration(locations={"file:src/test/resources/spring-security.xml", 
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceRegistryTest {
	
	@Autowired
	ApplicationContext ctx;
	
	private Map<String,IService> idServiceMap;
	
	private Map<String,String> serviceNameIdMap;
	
	@Autowired
	@Qualifier("conceptPowerService")
	private IService conceptPower;
	
	@Autowired
	@Qualifier("viafService")
	private IService viafService;
	
	@Autowired
	IServiceRegistry serviceRegistry;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
		//conceptPower = new ConceptPowerService();
		//conceptPower.setName("conceptPower");
		
		
	}
	
	

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInit() {
		
		IService serviceObject = null;
		idServiceMap = ctx.getBeansOfType(IService.class);
		
		IService service = idServiceMap.get("conceptPowerService");
		
		assertEquals(conceptPower,service);
	}

	@Test
	public void testGetServiceObject() {
		
		
		
		
	}

	@Test
	public void testGetServiceNameIdMap() {

		Map<String, String> dummyNameIdMap = new HashMap<String, String>();
		
		dummyNameIdMap.put(viafService.getServiceId(), viafService.getName());
		dummyNameIdMap.put(conceptPower.getServiceId(), conceptPower.getName());
		
		serviceNameIdMap = serviceRegistry.getServiceNameIdMap();
		
		assertEquals(dummyNameIdMap, serviceNameIdMap);
		
	}

	@Test
	public void testGetServiceIdNameMap() {

	
	
	}

	@Test
	public void testSetServiceIdNameMap() {

	
	
	}

}
