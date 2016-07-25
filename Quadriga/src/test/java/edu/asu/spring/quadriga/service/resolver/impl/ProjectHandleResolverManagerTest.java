package edu.asu.spring.quadriga.service.resolver.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.resolver.IProjectHandleResolverDAO;
import edu.asu.spring.quadriga.dao.resolver.impl.ProjectHandleResolverDAO;
import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.impl.ProjectHandleResolver;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectHandleResolverDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.mapper.resolver.IProjectHandleResolverMapper;
import edu.asu.spring.quadriga.mapper.resolver.impl.ProjectHandleResolverMapper;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;

public class ProjectHandleResolverManagerTest {

    @Mock
    private IProjectHandleResolverDAO resolverDao = Mockito.mock(ProjectHandleResolverDAO.class);

    @Mock
    private IProjectHandleResolverMapper mapper = Mockito.mock(ProjectHandleResolverMapper.class);

    @InjectMocks
    private ProjectHandleResolverManager projectHandleResolverManagerUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * tests
     * {@link IProjectHandleResolverManager#deleteProjectHandleResolver(resolver)}
     * method responsible for deleting the resolver from the database.
     * 
     * @throws QuadrigaException
     */
    @Test
    public void deleteProjectHandleResolverTest() throws QuadrigaException {

        // Creating dummyId
        String id = "123";

        // creating dummy resolver
        IProjectHandleResolver resolver = new ProjectHandleResolver();
        resolver.setId(id);

        // creating dummy projectHandlerResolverDTO
        ProjectHandleResolverDTO projectHandlerResolverDTO = new ProjectHandleResolverDTO();
        projectHandlerResolverDTO.setDescription("sample");

        // creating dummyprojectDTOList
        List<ProjectDTO> projectDTOList = new ArrayList<ProjectDTO>();
        Mockito.when(resolverDao.getProjectsForResolverId(id)).thenReturn(projectDTOList);

        Mockito.when(mapper.mapProjectHandleResolver(resolver)).thenReturn(projectHandlerResolverDTO);

        // Calling the method to test
        boolean deleteStatus = projectHandleResolverManagerUnderTest.deleteProjectHandleResolver(resolver);
        assertEquals(deleteStatus, true);
    }

    /**
     * tests
     * {@link IProjectHandleResolverManager#deleteProjectHandleResolver(resolver)}
     * method responsible for deleting the resolver from the database.
     * 
     * when there is a project using the resolver
     * 
     * @throws QuadrigaException
     */

    @Test(expected = QuadrigaException.class)
    public void deleteProjectHandleResolverProjectUsingResolverTest() throws QuadrigaException {

        // Creating dummyId
        String id = "456";

        // creating dummy resolver
        IProjectHandleResolver resolver = new ProjectHandleResolver();
        resolver.setId(id);

        // calling dummy projectDTOList
        List<ProjectDTO> projectDTOList = new ArrayList<ProjectDTO>();
        projectDTOList.add(new ProjectDTO());

        // calling the method to test
        Mockito.when(resolverDao.getProjectsForResolverId(id)).thenReturn(projectDTOList);

        projectHandleResolverManagerUnderTest.deleteProjectHandleResolver(resolver);
    }

}
