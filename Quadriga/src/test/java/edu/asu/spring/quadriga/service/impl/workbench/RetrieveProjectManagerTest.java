package edu.asu.spring.quadriga.service.impl.workbench;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectShallowMapper;

public class RetrieveProjectManagerTest {

    @Mock
    private IRetrieveProjectDAO mockedDBConnect = Mockito.mock(IRetrieveProjectDAO.class);

    @Mock
    private IProjectShallowMapper mockedProjectShallowMapper = Mockito.mock(IProjectShallowMapper.class);

    @InjectMocks
    private RetrieveProjectManager retrieveProjectManagerUnderTest;

    @Before
    public void setUp() throws QuadrigaStorageException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkOnlyPublicProjectsAreReturned() throws QuadrigaStorageException {
        Mockito.when(mockedDBConnect.getAllProjectsDTOByAccessibility("PUBLIC")).thenReturn(createPublicProjectDTOList());
        Mockito.when(mockedProjectShallowMapper.getProjectDetails(Matchers.any(ProjectDTO.class))).thenReturn(createPublicIProject());
        List<IProject> projectList = retrieveProjectManagerUnderTest.getProjectListByAccessibility(EProjectAccessibility.PUBLIC);

        assertNotNull(projectList);
        assertEquals(1, projectList.size());
        assertEquals("test Project1", projectList.get(0).getProjectName());
        assertEquals(EProjectAccessibility.PUBLIC, projectList.get(0).getProjectAccess());

        Mockito.verify(mockedDBConnect, times(1)).getAllProjectsDTOByAccessibility("PUBLIC");
        Mockito.verify(mockedProjectShallowMapper, times(1)).getProjectDetails(Matchers.any(ProjectDTO.class));
    }

    @Test
    public void checkProjectListByAccessibilityReturnsEmptyListIfNoPublicProjects() throws QuadrigaStorageException {
        Mockito.when(mockedDBConnect.getAllProjectsDTOByAccessibility("PUBLIC")).thenReturn(new ArrayList<ProjectDTO>());
        List<IProject> projectList = retrieveProjectManagerUnderTest.getProjectListByAccessibility(EProjectAccessibility.PUBLIC);

        assertNotNull(projectList);
        assertEquals(0, projectList.size());

        Mockito.verify(mockedDBConnect, times(1)).getAllProjectsDTOByAccessibility("PUBLIC");
        Mockito.verify(mockedProjectShallowMapper, times(0)).getProjectDetails(Matchers.any(ProjectDTO.class));
    }

    private List<ProjectDTO> createPublicProjectDTOList() {
        ProjectDTO project1 = new ProjectDTO();
        List<ProjectDTO> projectDTOList = new ArrayList<ProjectDTO>();
        projectDTOList.add(project1);

        return projectDTOList;
    }

    private IProject createPublicIProject() {
        IProject projectProxy = new ProjectProxy(retrieveProjectManagerUnderTest);
        projectProxy.setCreatedBy("Created By");
        projectProxy.setProjectName("test Project1");
        projectProxy.setProjectAccess(EProjectAccessibility.PUBLIC);

        return projectProxy;
    }
}