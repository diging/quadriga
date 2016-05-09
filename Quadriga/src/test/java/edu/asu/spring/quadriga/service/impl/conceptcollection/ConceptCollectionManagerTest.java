package edu.asu.spring.quadriga.service.impl.conceptcollection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
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
    private IConceptCollectionDeepMapper mockedConceptCollectionDeepMapper = Mockito
            .mock(IConceptCollectionDeepMapper.class);

    @Mock
    private IConceptpowerConnector cpConnector;

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

    @InjectMocks
    private ConceptCollectionManager conceptCollectionManagerUnderTest;

    private Concept concept;

    @Before
    public void setUp() throws QuadrigaStorageException {
        MockitoAnnotations.initMocks(this);

        List<ConceptpowerReply.ConceptEntry> conceptEntries = new ArrayList<ConceptpowerReply.ConceptEntry>();
        ConceptpowerReply.ConceptEntry entry = new ConceptpowerReply.ConceptEntry();
        entry.setLemma("test-lemma");
        conceptEntries.add(entry);
        ConceptpowerReply rep = new ConceptpowerReply();
        rep.setConceptEntry(conceptEntries);

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

        Mockito.when(cpConnector.search("item", "pos")).thenReturn(rep);
        Mockito.when(cpConnector.getById("id")).thenReturn(rep);

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
        Mockito.doNothing().when(mockedccDao).updateItem(Matchers.any(IConcept.class), Matchers.eq("id"),
                Matchers.eq("username"));
    }

    @Test
    public void testSearch() {

        ConceptpowerReply reply = conceptCollectionManagerUnderTest.search("item", "pos");
        assertNotNull(reply);
        assertEquals(1, reply.getConceptEntry().size());
        assertEquals("test-lemma", reply.getConceptEntry().get(0).getLemma());
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
        Mockito.when(cpConnector.getById("id2")).thenReturn(repWithNoEntries);
        String result = conceptCollectionManagerUnderTest.getConceptLemmaFromConceptId("id2");
        assertEquals("id2", result);
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
    public void updateTest() throws QuadrigaStorageException {

        ConceptCollection conceptCollection = new ConceptCollection();
        conceptCollection.setConceptCollectionId("id");
        conceptCollectionManagerUnderTest.update(new String[] { "id" }, conceptCollection, "username");
        assertEquals("test-lemma", concept.getLemma());
    }

}
