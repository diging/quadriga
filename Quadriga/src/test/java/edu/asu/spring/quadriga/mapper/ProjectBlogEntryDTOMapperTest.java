package edu.asu.spring.quadriga.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlogEntry;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;
import edu.asu.spring.quadriga.dto.ProjectBlogEntryDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.UserManager;

/**
 * This class tests the mapper functionality for {@linkplain IProjectBlogEntry}
 * and {@linkplain ProjectBlogEntryDTO}.
 * 
 * @author PawanMahalle
 *
 */
public class ProjectBlogEntryDTOMapperTest {

    // Mocked variable values
    public static final String ID = "BLGE_ID_DUMMY";
    public static final String TITLE = "TITLE_DUMMY";
    private static final String DESCRIPTION = "DESCRIPTION_DUMMY";
    private static final String AUTHOR_NAME = "AUTHOR_NAME";
    private static final Date CREATED_DATE = new Date();
    private static final String PROJECT_ID = "PROJ_ID_DUMMY";

    @Mock
    IUserManager userManager = Mockito.mock(UserManager.class);

    @InjectMocks
    ProjectBlogEntryDTOMapper projectBlogEntryDTOMapperUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * tests
     * {@linkplain ProjectBlogEntryDTOMapper#getProjectBlogEntry(ProjectBlogEntryDTO)}
     * method responsible for mapping {@linkplain ProjectBlogEntryDTO} to
     * {@linkplain IProjectBlogEntry} instance
     * 
     * @throws QuadrigaStorageException
     */
    @Test
    public void getProjectBlogEntry() throws QuadrigaStorageException {

        // Creating an instance of ProjectBlogEntryDTO which is passes as
        // argument
        // to the getProjectBlogEntry method
        ProjectBlogEntryDTO projectBlogEntryDTO = new ProjectBlogEntryDTO();
        projectBlogEntryDTO.setProjectBlogEntryId(ID);
        projectBlogEntryDTO.setTitle(TITLE);
        projectBlogEntryDTO.setDescription(DESCRIPTION);
        projectBlogEntryDTO.setAuthor(AUTHOR_NAME);
        projectBlogEntryDTO.setCreatedDate(CREATED_DATE);
        projectBlogEntryDTO.setProjectid(PROJECT_ID);

        // IUser instance which should be returned by mocked userManager
        // instance when getUser() method is called
        IUser author = new User();
        author.setName(AUTHOR_NAME);
        Mockito.when(userManager.getUser(AUTHOR_NAME)).thenReturn(author);

        // Calling getProjectBlogEntry method with dummy ProjectBlogEntryDTO
        // instance
        IProjectBlogEntry projectBlogEntry = projectBlogEntryDTOMapperUnderTest
                .getProjectBlogEntry(projectBlogEntryDTO);

        assertProjectBlogEntryMapping(projectBlogEntry);
    }

    private void assertProjectBlogEntryMapping(IProjectBlogEntry projectBlogEntry) {
        assertEquals(ID, projectBlogEntry.getProjectBlogEntryId());
        assertEquals(TITLE, projectBlogEntry.getTitle());
        assertEquals(DESCRIPTION, projectBlogEntry.getDescription());
        assertEquals(AUTHOR_NAME, projectBlogEntry.getAuthor().getName());
        assertEquals(CREATED_DATE, projectBlogEntry.getCreatedDate());
        assertEquals(PROJECT_ID, projectBlogEntry.getProjectId());
    }

    /**
     * creates an instance of {@linkplain ProjectBlogEntryDTOMapper} which
     * overrides the {@linkplain BaseMapper#getUserDTO(String)} method to return
     * mocked instance of {@linkplain QuadrigaUserDTO} with username as name of
     * author i.e <code>AUTHOR_NAME</code>.
     * 
     * Mockito does not support mocking base class methods.
     * 
     * Reference: 1. Mocking base class method i.e. getUserDTO is not possible
     * [http://stackoverflow.com/questions/3467801/mockito-how-to-mock-only-the-
     * call-of-a-method-of-the-superclass] 2. Solution to the problem. Used
     * first approach from page -
     * https://www.tildedave.com/2011/03/06/pattern-stubbing-legacy-superclasses
     * -with-mockito-spies.html.
     * 
     * @return instance of {@linkplain QuadrigaUserDTO}
     */
    public ProjectBlogEntryDTOMapper getProjectBlogEntryDTOMapper() {
        return new ProjectBlogEntryDTOMapper() {
            @Override
            public QuadrigaUserDTO getUserDTO(String userName) {
                // Creating an instance of QuadrigaUserDTO which should be
                // returned by mocked projectBlogEntryDTOMapper object when
                // getUserDTO is called.
                QuadrigaUserDTO quadrigaUserDTO = new QuadrigaUserDTO();
                quadrigaUserDTO.setUsername(AUTHOR_NAME);
                return quadrigaUserDTO;
            }
        };
    }

    /**
     * tests
     * {@linkplain ProjectBlogEntryDTOMapper#getProjectBlogEntryDTO(IProjectBlogEntry)}
     * method responsible for mapping {@linkplain IProjectBlogEntry} instance to
     * {@linkplain ProjectBlogEntryDTO} instance
     */
    @Test
    public void getProjectBlogEntryDTOTest() {

        // Creating an instance of IProjectBlogEntry which is passes as an
        // argument to the getProjectBlogEntryDTO method
        IProjectBlogEntry projectBlogEntry = new ProjectBlogEntry();

        projectBlogEntry.setProjectBlogEntryId(ID);
        projectBlogEntry.setTitle(TITLE);
        projectBlogEntry.setDescription(DESCRIPTION);
        projectBlogEntry.setCreatedDate(CREATED_DATE);
        projectBlogEntry.setProjectId(PROJECT_ID);

        IUser author = new User();
        author.setUserName(AUTHOR_NAME);
        projectBlogEntry.setAuthor(author);

        // Calling getProjectBlogEntryDTO method with dummy IProjectBlogEntry
        // instance
        ProjectBlogEntryDTOMapper projectBlogEntryDTOMapperUnderTest = getProjectBlogEntryDTOMapper();
        ProjectBlogEntryDTO projectBlogEntryDTO = projectBlogEntryDTOMapperUnderTest
                .getProjectBlogEntryDTO(projectBlogEntry);

        assertProjectBlogEntryDTOMapping(projectBlogEntryDTO);
    }

    private void assertProjectBlogEntryDTOMapping(ProjectBlogEntryDTO projectBlogEntryDTO) {
        assertEquals(ID, projectBlogEntryDTO.getProjectBlogEntryId());
        assertEquals(TITLE, projectBlogEntryDTO.getTitle());
        assertEquals(DESCRIPTION, projectBlogEntryDTO.getDescription());
        assertEquals(CREATED_DATE, projectBlogEntryDTO.getCreatedDate());
        assertEquals(PROJECT_ID, projectBlogEntryDTO.getProjectid());
        assertEquals(AUTHOR_NAME, projectBlogEntryDTO.getProjectBlogEntryAuthorDTO().getUsername());
    }

}
