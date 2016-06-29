package edu.asu.spring.quadriga.service.impl.projectblog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.projectblog.IProjectBlogEntryDAO;
import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlogEntry;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;
import edu.asu.spring.quadriga.dto.ProjectBlogEntryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectBlogEntryDTOMapper;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogEntryManager;

/**
 * This class tests the following methods of class
 * {@linkplain ProjectBlogEntryManager}
 * <ol>
 * <li>1.
 * {@linkplain ProjectBlogEntryManager#addNewProjectBlogEntry(IProjectBlogEntry)}
 * </li>
 * <li>2.
 * {@linkplain ProjectBlogEntryManager#getProjectBlogEntryList(String, Integer)}
 * </li>
 * </ol>
 * 
 * @author PawanMahalle
 *
 */
public class ProjectBlogEntryManagerTest {

    private static final String DUMMY_PROJECT_ID = "PROJECT_ID";
    private static final Integer LIMIT = 2;

    private static final String UNIQUE_BLGE_ID = "UNIQUE_BLGE_ID";
    private static final String TITLE_NEW = "TITLE_NEW";
    private static final String DESCRIPTION_NEW = "DESCRIPTION_NEW";

    private static final String BLGE_ID_1 = "BLGE1";
    private static final String TITLE_1 = "TITLE_1";
    private static final String DESCRIPTION_1 = "TITLE_1";

    private static final String BLGE_ID_2 = "BLGE2";
    private static final String TITLE_2 = "TITLE_2";
    private static final String DESCRIPTION_2 = "TITLE_2";

    @Mock
    private IProjectBlogEntryDAO projectBlogEntryDAO = Mockito.mock(IProjectBlogEntryDAO.class);

    @Mock
    private ProjectBlogEntryDTOMapper projectBlogEntryDTOMapper = Mockito.mock(ProjectBlogEntryDTOMapper.class);

    @InjectMocks
    private ProjectBlogEntryManager projectBlogEntryManagerUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * tests
     * {@link IProjectBlogEntryManager#getProjectBlogEntryList(String, Integer)}
     * method responsible for retrieving list of project blog entries for a
     * project
     * 
     * @throws QuadrigaStorageException
     */
    @Test
    public void getProjectBlogEntryListTest() throws QuadrigaStorageException {

        // Creating dummy list of blog entry DTOs to return
        List<ProjectBlogEntryDTO> projectBlogEntryDTOList = new ArrayList<>();
        ProjectBlogEntryDTO blgeDTO1 = createProjectBlogEntryDTO(BLGE_ID_1, TITLE_1, DESCRIPTION_1);
        projectBlogEntryDTOList.add(blgeDTO1);
        ProjectBlogEntryDTO blgeDTO2 = createProjectBlogEntryDTO(BLGE_ID_2, TITLE_2, DESCRIPTION_2);
        projectBlogEntryDTOList.add(blgeDTO2);

        // Return the dummy list of blog entries created above when method to
        // get project blog entries is called
        Mockito.when(projectBlogEntryDAO.getProjectBlogEntryDTOListByProjectId(DUMMY_PROJECT_ID, LIMIT))
                .thenReturn(projectBlogEntryDTOList);

        // Creating dummy project blog entry
        IProjectBlogEntry blge1 = new ProjectBlogEntry();
        blge1.setProjectBlogEntryId(BLGE_ID_1);
        blge1.setTitle(TITLE_1);
        blge1.setDescription(DESCRIPTION_1);

        Mockito.when(projectBlogEntryDTOMapper.getProjectBlogEntry(blgeDTO1)).thenReturn(blge1);

        // Creating dummy project blog entry
        IProjectBlogEntry blge2 = new ProjectBlogEntry();
        blge2.setProjectBlogEntryId(BLGE_ID_2);
        blge2.setTitle(TITLE_2);
        blge2.setDescription(DESCRIPTION_2);

        Mockito.when(projectBlogEntryDTOMapper.getProjectBlogEntry(blgeDTO2)).thenReturn(blge2);

        // Calling the method to test
        List<IProjectBlogEntry> projectBlogEntryList = projectBlogEntryManagerUnderTest
                .getProjectBlogEntryList(DUMMY_PROJECT_ID, LIMIT);

        // Asserting the details
        assertProjectBlogEntryList(projectBlogEntryList);
    }

    private void assertProjectBlogEntryList(List<IProjectBlogEntry> projectBlogEntryList) {

        // Assert that exactly 2 entries are fetched
        assertEquals(2, projectBlogEntryList.size());

        assertEquals(BLGE_ID_1, projectBlogEntryList.get(0).getProjectBlogEntryId());
        assertEquals(TITLE_1, projectBlogEntryList.get(0).getTitle());
        assertEquals(DESCRIPTION_1, projectBlogEntryList.get(0).getDescription());

        assertEquals(BLGE_ID_2, projectBlogEntryList.get(1).getProjectBlogEntryId());
        assertEquals(TITLE_2, projectBlogEntryList.get(1).getTitle());
        assertEquals(DESCRIPTION_2, projectBlogEntryList.get(1).getDescription());

    }

    private ProjectBlogEntryDTO createProjectBlogEntryDTO(String id, String title, String description) {
        ProjectBlogEntryDTO projectBlogEntryDTO = new ProjectBlogEntryDTO();
        projectBlogEntryDTO.setProjectBlogEntryId(id);
        projectBlogEntryDTO.setTitle(title);
        projectBlogEntryDTO.setDescription(description);
        return projectBlogEntryDTO;
    }

    /**
     * tests
     * {@link IProjectBlogEntryManager#addNewProjectBlogEntry(IProjectBlogEntry)}
     * method responsible for adding a project blog entry into database
     * 
     * @throws QuadrigaStorageException
     */
    @Test
    public void addNewProjectBlogEntryTest() throws QuadrigaStorageException {

        // Creating dummy project blog entry
        IProjectBlogEntry projectBlogEntry = new ProjectBlogEntry();
        projectBlogEntry.setTitle(TITLE_NEW);
        projectBlogEntry.setDescription(DESCRIPTION_NEW);

        Mockito.when(projectBlogEntryDAO.generateUniqueID()).thenReturn(UNIQUE_BLGE_ID);

        // Mocked ProjectBlogEntryDTO instance
        ProjectBlogEntryDTO projectBlogEntryDTO = createProjectBlogEntryDTO(UNIQUE_BLGE_ID, TITLE_NEW, DESCRIPTION_NEW);

        Mockito.when(projectBlogEntryDTOMapper.getProjectBlogEntryDTO(projectBlogEntry))
                .thenReturn(projectBlogEntryDTO);

        // Calling the method to test
        projectBlogEntryManagerUnderTest.addNewProjectBlogEntry(projectBlogEntry);

        // Assert that id is assigned to projectBlogEntry object by
        // addNewProjectBlogEntry method
        assertEquals(UNIQUE_BLGE_ID, projectBlogEntry.getProjectBlogEntryId());

        // Assert that projectBlogEntry has date assigned by
        // addNewProjectBlogEntry method
        assertNotNull(projectBlogEntry.getCreatedDate());

        // Verifying that saveNewDTO method is called
        verify(projectBlogEntryDAO).saveNewDTO(projectBlogEntryDTO);
    }

    /**
     * tests
     * {@link IProjectBlogEntryManager#getProjectBlogEntryDetails(projectBlogEntryId)}
     * method responsible for fetching the blog entry identified by project blog
     * entry id
     * 
     * @throws QuadrigaStorageException
     */
    @Test
    public void getProjectBlogEntryDetailsTest() throws QuadrigaStorageException {

        // Creating the dummy project Blog Entry
        IProjectBlogEntry projectBlogEntry = new ProjectBlogEntry();
        projectBlogEntry.setTitle(TITLE_NEW);
        projectBlogEntry.setDescription(DESCRIPTION_NEW);
        projectBlogEntry.setProjectBlogEntryId(BLGE_ID_1);

        // Mocked ProjectBlogEntryDTO instance
        ProjectBlogEntryDTO projectBlogEntryDTO = createProjectBlogEntryDTO(UNIQUE_BLGE_ID, TITLE_NEW, DESCRIPTION_NEW);

        // Returning dummy project blog entry DTO
        Mockito.when(projectBlogEntryDAO.getDTO(BLGE_ID_1)).thenReturn(projectBlogEntryDTO);

        // Returning the created projectBlogEntry
        Mockito.when(projectBlogEntryDTOMapper.getProjectBlogEntry(projectBlogEntryDTO)).thenReturn(projectBlogEntry);

        // Calling the method to test
        IProjectBlogEntry resultProjectBlogEntry = projectBlogEntryManagerUnderTest
                .getProjectBlogEntryDetails(BLGE_ID_1);

        // assert that the resultProjectBlogEntry has blog entry id as expected
        assertEquals(resultProjectBlogEntry.getProjectBlogEntryId(), BLGE_ID_1);
        // assert that the resultProjectBlogEntry has Title as expected
        assertEquals(resultProjectBlogEntry.getTitle(), TITLE_NEW);
        // assert that the resultProjectBlogEntry has description as expected
        assertEquals(resultProjectBlogEntry.getDescription(), DESCRIPTION_NEW);

    }

    /**
     * tests
     * {@link IProjectBlogEntryManager#getProjectBlogEntryDetails(projectBlogEntryId)}
     * method responsible for fetching the blog entry identified by project blog
     * entry id
     * 
     * when there is no entry with the blog entry id
     * 
     * @throws QuadrigaStorageException
     */
    @Test
    public void getProjectBlogEntryDetailsTestIdNotExist() throws QuadrigaStorageException {

        // Calling the method to test with blog entry id does not exist
        IProjectBlogEntry resultProjectBlogEntryWhenIdNotExist = projectBlogEntryManagerUnderTest
                .getProjectBlogEntryDetails(BLGE_ID_2);
        // assert that the resultProjectBlogEntry is null
        assertEquals(resultProjectBlogEntryWhenIdNotExist, null);
    }

}
