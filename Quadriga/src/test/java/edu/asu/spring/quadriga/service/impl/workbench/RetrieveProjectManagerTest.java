package edu.asu.spring.quadriga.service.impl.workbench;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectShallowMapper;

public class RetrieveProjectManagerTest {

	@InjectMocks
	private RetrieveProjectManager retrieveProjectManagerUnderTest;

	@Mock
	private IRetrieveProjectDAO mockedDBConnect = Mockito.mock(IRetrieveProjectDAO.class);

	@Mock
	private IProjectShallowMapper mockedProjectShallowMapper = Mockito.mock(IProjectShallowMapper.class);

	@Before
	public void setUp() throws QuadrigaStorageException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void checkOnlyPublicProjectsAreReturned() throws QuadrigaStorageException{
		Mockito.when(mockedDBConnect.getAllProjectsDTOByAccessibility("PUBLIC")).thenReturn(createPublicProjectDTOList());
		Mockito.when(mockedProjectShallowMapper.getProjectDetails(Matchers.any(ProjectDTO.class)))
		.thenReturn(createPublicIProject());
		List<IProject> projectList = retrieveProjectManagerUnderTest.getProjectListByAccessibility("PUBLIC");

		assertNotNull(projectList);
		assertEquals(1, projectList.size());
		assertEquals("test Project1", projectList.get(0).getProjectName());
	}
	
	@Test
	public void checkProjectListByAccessibilityReturnsEmptyListIfNoPublicProjects() throws QuadrigaStorageException{
		Mockito.when(mockedDBConnect.getAllProjectsDTOByAccessibility("PUBLIC")).thenReturn(new ArrayList<ProjectDTO>());
		List<IProject> projectList = retrieveProjectManagerUnderTest.getProjectListByAccessibility("PUBLIC");

		assertNotNull(projectList);
		assertEquals(0, projectList.size());
	}
	
	@Test
	public void checkProjectListByAccessibilityReturnsEmptyListIfNull() throws QuadrigaStorageException{
		Mockito.when(mockedDBConnect.getAllProjectsDTOByAccessibility(null)).thenReturn(new ArrayList<ProjectDTO>());
		List<IProject> projectList = retrieveProjectManagerUnderTest.getProjectListByAccessibility(null);

		assertNotNull(projectList);
		assertEquals(0, projectList.size());
	}
	
	private List<ProjectDTO> createPublicProjectDTOList(){
		ProjectDTO project1 = new ProjectDTO();
		List<ProjectDTO> projectDTOList = new ArrayList<ProjectDTO>();
		projectDTOList.add(project1);

		return projectDTOList;
	}

	private IProject createPublicIProject(){
		IProject projectProxy = new ProjectProxy(retrieveProjectManagerUnderTest);
		projectProxy.setCreatedBy("Created By");
		projectProxy.setProjectName("test Project1");
		
		return projectProxy;
	}
}
