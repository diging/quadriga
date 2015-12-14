package edu.asu.spring.quadriga.service.impl.conceptcollection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.dao.workspace.IListWsDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptFactory;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.Concept;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollection;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.mapper.IConceptCollectionDeepMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

public class ConceptCollectionManagerTest {

    @Mock
    private RestTemplate mockedRestTemplate = Mockito.mock(RestTemplate.class);

    @Mock
    private IConceptCollectionDeepMapper mockedConceptCollectionDeepMapper = Mockito
            .mock(IConceptCollectionDeepMapper.class);

    @Mock
    private IProjectShallowMapper mockedProjectShallowMapper = Mockito.mock(IProjectShallowMapper.class);

    @Mock
    private IListWsDAO mockedwsListManger = Mockito.mock(IListWsDAO.class);

    @Mock
    private IListWSManager mockedwsManager = Mockito.mock(IListWSManager.class);

    @Mock
    private IConceptFactory mockedConceptFactory = Mockito.mock(IConceptFactory.class);

    @Mock
    private IConceptCollectionDAO mockedccDao = Mockito.mock(IConceptCollectionDAO.class);

    private String conceptURL = "conceptURL";

    private String searchURL = "searchURL";

    private String updateURL = "updateURL";

    @InjectMocks
    private ConceptCollectionManager conceptCollectionManagerUnderTest;

    private Concept concept;

    private Map<String, String> vars2;

    @Before
    public void setUp() throws QuadrigaStorageException {
        MockitoAnnotations.initMocks(this);

        List<ConceptpowerReply.ConceptEntry> conceptEntries = new ArrayList<ConceptpowerReply.ConceptEntry>();
        ConceptpowerReply.ConceptEntry entry = new ConceptpowerReply.ConceptEntry();
        entry.setLemma("test-lemma");
        conceptEntries.add(entry);
        ConceptpowerReply rep = new ConceptpowerReply();
        rep.setConceptEntry(conceptEntries);

        ReflectionTestUtils.setField(conceptCollectionManagerUnderTest, "conceptURL", conceptURL);
        ReflectionTestUtils.setField(conceptCollectionManagerUnderTest, "searchURL", searchURL);
        ReflectionTestUtils.setField(conceptCollectionManagerUnderTest, "updateURL", updateURL);

        Map<String, String> vars = new HashMap<String, String>();
        vars.put("name", "item");
        vars.put("pos", "pos");

        vars2 = new HashMap<String, String>();
        vars2.put("name", "id");

        Mockito.when(mockedRestTemplate.getForObject(Matchers.eq(conceptURL + searchURL + "{name}/{pos}"),
                Matchers.eq(ConceptpowerReply.class), Matchers.eq(vars))).thenReturn(rep);

        Mockito.when(mockedRestTemplate.getForObject(Matchers.eq(conceptURL + searchURL + "{name}"),
                Matchers.eq(ConceptpowerReply.class), Matchers.eq(vars2))).thenReturn(rep);

        Mockito.when(mockedRestTemplate.getForObject(Matchers.eq(conceptURL + updateURL + "{name}"),
                Matchers.eq(ConceptpowerReply.class), Matchers.eq(vars2))).thenReturn(rep);

        Project project = new Project();
        project.setCreatedBy("createdBy");
        project.setProjectName("test project");
        project.setProjectId("id");

        Project project2 = new Project();
        project2.setCreatedBy("createdBy2");
        project2.setProjectName("test project2");
        project2.setProjectId("id2");

        List<IProject> ccProjectsList = new ArrayList<IProject>();
        ccProjectsList.add(project);

        List<IProject> ccProjectsList2 = new ArrayList<IProject>();
        ccProjectsList2.add(project);
        ccProjectsList2.add(project2);

        Mockito.when(mockedProjectShallowMapper.getProjectList("username")).thenReturn(ccProjectsList2);
        Mockito.when(mockedProjectShallowMapper.getCollaboratorProjectListOfUser("ccId")).thenReturn(ccProjectsList);

        WorkSpace workspace = new WorkSpace();
        workspace.setWorkspaceId("w-id");
        workspace.setWorkspaceName("w-name");

        WorkSpace workspace2 = new WorkSpace();
        workspace2.setWorkspaceId("w-id2");
        workspace2.setWorkspaceName("w-name2");

        List<IWorkSpace> ccWorkspaceList = new ArrayList<IWorkSpace>();
        ccWorkspaceList.add(workspace);

        List<IWorkSpace> ccWorkspaceList2 = new ArrayList<IWorkSpace>();
        ccWorkspaceList2.add(workspace);
        ccWorkspaceList2.add(workspace2);
        Mockito.when(mockedwsListManger.getWorkspaceByConceptCollection("ccId")).thenReturn(ccWorkspaceList);
        Mockito.when(mockedwsManager.listActiveWorkspace("id", "username")).thenReturn(ccWorkspaceList2);

        concept = new Concept();

        Mockito.when(mockedConceptFactory.createConceptObject()).thenReturn(concept);
        Mockito.when(mockedccDao.updateItem(Matchers.any(IConcept.class), Matchers.eq("id"), Matchers.eq("username")))
                .thenReturn("dummy");
    }

    @Test
    public void testSearch() {

        ConceptpowerReply reply = conceptCollectionManagerUnderTest.search("item", "pos");
        assertNotNull(reply);
    }

    @Test
    public void testSearchNegative() {

        ConceptpowerReply reply = conceptCollectionManagerUnderTest.search(null, null);
        assertNull(reply);
    }

    @Test
    public void getConceptLemmaFromConceptIdTest() {
        String result = conceptCollectionManagerUnderTest.getConceptLemmaFromConceptId("id");
        assertEquals("test-lemma", result);
    }

    @Test
    public void getConceptLemmaFromConceptIdTestWithNoEntries() {
        List<ConceptpowerReply.ConceptEntry> conceptEntries2 = new ArrayList<ConceptpowerReply.ConceptEntry>();
        ConceptpowerReply repWithNoEntries = new ConceptpowerReply();
        repWithNoEntries.setConceptEntry(conceptEntries2);
        Mockito.when(mockedRestTemplate.getForObject(Matchers.eq(conceptURL + updateURL + "{name}"),
                Matchers.eq(ConceptpowerReply.class), Matchers.eq(vars2))).thenReturn(repWithNoEntries);
        String result = conceptCollectionManagerUnderTest.getConceptLemmaFromConceptId("id");
        assertEquals("id", result);
    }

    private void mockConceptCollectionDeepMapper(IConceptCollection returnType) throws QuadrigaStorageException {
        Mockito.when(mockedConceptCollectionDeepMapper.getConceptCollectionDetails("id")).thenReturn(returnType);
    }

    @Test
    public void showCollaboratingUsersTest() throws QuadrigaStorageException {
        ConceptCollection conceptCollection = new ConceptCollection();
        List<IConceptCollectionCollaborator> collabList = new ArrayList<IConceptCollectionCollaborator>();

        ConceptCollectionCollaborator col = new ConceptCollectionCollaborator();
        col.setUpdatedBy("test updater");
        collabList.add(col);

        conceptCollection.setConceptCollectionCollaborators(collabList);
        mockConceptCollectionDeepMapper(conceptCollection);

        List<IConceptCollectionCollaborator> resultList = conceptCollectionManagerUnderTest
                .showCollaboratingUsers("id");
        assertEquals("test updater", resultList.get(0).getUpdatedBy());
    }

    @Test
    public void showCollaboratingUsersNegativeTest() throws QuadrigaStorageException {
        mockConceptCollectionDeepMapper(null);
        List<IConceptCollectionCollaborator> resultList = conceptCollectionManagerUnderTest
                .showCollaboratingUsers("id");
        assertNull(resultList);
    }

    @Test
    public void getProjectsTreeTest() throws QuadrigaStorageException, JSONException {

        String result = "{\"core\": {\"data\": [\n {\n  \"id\": \"id\",\n  \"parent\": \"#\",\n  \"text\": \"test project\"\n },\n {\n  \"id\": \"w-id\",\n  \"parent\": \"id\",\n  \"text\": \"w-name\"\n },\n {\n  \"id\": \"w-id2\",\n  \"parent\": \"id\",\n  \"text\": \"<a href=\'#\' id=\'w-id2\' name=\'w-name2\' onclick=\'javascript:addCCtoWorkspace(this.id,this.name);\' >w-name2<\\/a>\"\n },\n {\n  \"id\": \"id2\",\n  \"parent\": \"#\",\n  \"text\": \"<a href=\'#\' id=\'id2\' name=\'test project2\' onclick=\'javascript:addCCtoProjects(this.id,this.name);\' > test project2<\\/a>\"\n }\n]}}";

        String actualResult = conceptCollectionManagerUnderTest.getProjectsTree("username", "ccId");
        assertEquals(result, actualResult);
    }

    @Test
    public void updateTest() throws QuadrigaStorageException {

        ConceptCollection conceptCollection = new ConceptCollection();
        conceptCollection.setConceptCollectionId("id");
        conceptCollectionManagerUnderTest.update(new String[] { "id" }, conceptCollection, "username");
        assertEquals("test-lemma", concept.getLemma());
    }

}
