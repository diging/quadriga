package edu.asu.spring.quadriga.dao.impl.workspace;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.NetworkWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTO;

@Repository
public class WorkspaceDAO extends BaseDAO<WorkspaceDTO>implements IWorkspaceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private IProjectDAO projectDAO;

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.dao.impl.workspace.IWorkspaceDAO#getWorkspaceDTO(
     * java.lang.String)
     */
    @Override
    public WorkspaceDTO getWorkspaceDTO(String workspaceId) {
        WorkspaceDTO workspaceDTO = null;
        try {
            workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
        } catch (HibernateException e) {
            logger.error("Retrieve workspace details method :", e);
            return null;
        }
        return workspaceDTO;
    }

    @Override
    @Transactional
    public boolean deleteWorkspace(String wsId) {
        WorkspaceDTO workspace = getWorkspaceDTO(wsId);
        if (workspace == null) {
            logger.error("Workspace does not exist.");
            return false;
        }

        deleteWorkspaceProjectMappings(workspace);
        deleteWorkspaceConceptCollectionMappings(workspace);
        //deleteWorkspaceDictionaryMappings(workspace);
        deleteWorkspaceDSpaceMappings(workspace);
        deleteWorkspaceEditorMappings(workspace);
        deleteWorkspaceNetworkMappings(workspace);

        // save the above changes
        //updateDTO(workspace);
        // then delete
        deleteDTO(workspace);
        return true;
    }

    /*
     * ================================================================= Private
     * Methods =================================================================
     */

    /**
     * Method to delete workspace-project mapping objects. First, the
     * {@link ProjectWorkspaceDTO} mapping object is deleted from the list of
     * workspaces of a project, then the object is deleted. Last, the object is
     * set to null on the respective workspace.
     * 
     * @param wsDTO
     *            The workspace DTO for which the mapping objects should be
     *            deleted.
     */
    private void deleteWorkspaceProjectMappings(WorkspaceDTO wsDTO) {
        ProjectWorkspaceDTO pwDTO = wsDTO.getProjectWorkspaceDTO();

        // delete mapping project-workspace from project
        ProjectDTO project = pwDTO.getProjectDTO();
        if (project.getProjectWorkspaceDTOList() != null) {
            project.getProjectWorkspaceDTOList().remove(pwDTO);
            updateObject(project);
        }

        deleteObject(pwDTO);

        // assigning the workspace project mapping to null for workspace object
        wsDTO.setProjectWorkspaceDTO(null);
    }

    /**
     * Method to delete workspace-concept collection mapping objects. First the
     * mapping objects ({@link WorkspaceConceptcollectionDTO}) are removed from
     * the respective concept collections, then the mapping objects are deleted.
     * Last, the variable in the workspace object is set to null.
     * 
     * @param wsDTO
     *            Workspace for which the mapping objects should be deleted.
     */
    private void deleteWorkspaceConceptCollectionMappings(WorkspaceDTO wsDTO) {
        // delete all mapping objects from workspace list of concept collections
        // and delete mapping objects
        List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionList = wsDTO
                .getWorkspaceConceptCollectionDTOList();
        for (WorkspaceConceptcollectionDTO workspaceConceptCollection : workspaceConceptCollectionList) {
            ConceptCollectionDTO conceptCollection = workspaceConceptCollection.getConceptCollectionDTO();
            if (conceptCollection.getWsConceptCollectionDTOList() != null) {
                conceptCollection.getWsConceptCollectionDTOList().remove(workspaceConceptCollection);
                updateObject(conceptCollection);
            }
            deleteObject(workspaceConceptCollection);
        }

        // set the workspace concept collection mapping to null for workspace
        // object
        wsDTO.setWorkspaceConceptCollectionDTOList(null);
    }

    /**
     * Method to delete workspace-dictionary mapping objects (
     * {@link WorkspaceDictionaryDTO}). First the mapping objects are removed
     * from the respective Dictionaries, then the mapping objects are deleted.
     * Last the reference to the mapping objects in the workspace object is set
     * to null.
     * 
     * @param wsDTO
     *            The workspace DTO ({@link WorkspaceDTO}) for which the mapping
     *            objects should be deleted.
     */
    private void deleteWorkspaceDictionaryMappings(WorkspaceDTO wsDTO) {
        List<WorkspaceDictionaryDTO> workspaceDictionaryList = wsDTO.getWorkspaceDictionaryDTOList();
        for (WorkspaceDictionaryDTO workspaceDictionary : workspaceDictionaryList) {
            DictionaryDTO dictionary = workspaceDictionary.getDictionaryDTO();
            if (dictionary.getWsDictionaryDTOList() != null) {
                dictionary.getWsDictionaryDTOList().remove(workspaceDictionary);
                updateObject(dictionary);
            }
            deleteObject(workspaceDictionary);
        }

        // assigning the dictionary workspace mapping for workspace object to
        // null
        wsDTO.setWorkspaceDictionaryDTOList(null);
    }

    /**
     * Method to delete workspace-DSpace mapping objects (
     * {@link WorkspaceDspaceDTO}). First the mapping objects are deleted. Then
     * the reference to the mapping objects in the workspace object is set to
     * null.
     * 
     * @param wsDTO
     *            The workspace DTO ({@link WorkspaceDTO}) for which the mapping
     *            objects should be deleted.
     */
    private void deleteWorkspaceDSpaceMappings(WorkspaceDTO wsDTO) {
        List<WorkspaceDspaceDTO> workspaceDspaceList = wsDTO.getWorkspaceDspaceDTOList();
        for (WorkspaceDspaceDTO workspaceDspace : workspaceDspaceList) {
            deleteObject(workspaceDspace);
        }
        // set the workspace dspace mapping null in workspace object
        wsDTO.setWorkspaceDspaceDTOList(null);
    }

    /**
     * Method to delete workspace-editor mapping objects (
     * {@link WorkspaceEditorDTO}). First the mapping objects are deleted. Then
     * the reference to the mapping objects in the workspace object is set to
     * null.
     * 
     * @param wsDTO
     *            The workspace DTO ({@link WorkspaceDTO}) for which the mapping
     *            objects should be deleted.
     */
    private void deleteWorkspaceEditorMappings(WorkspaceDTO wsDTO) {
        List<WorkspaceEditorDTO> workspaceEditorList = wsDTO.getWorkspaceEditorDTOList();
        for (WorkspaceEditorDTO workspaceEditor : workspaceEditorList) {
            deleteObject(workspaceEditor);
        }

        // set the editor workspace mapping to null in workspace object
        wsDTO.setWorkspaceEditorDTOList(null);
    }

    /**
     * Method to delete workspace-network mapping objects (
     * {@link NetworkWorkspaceDTO}). First the mapping objects are deleted. Then
     * the reference to the mapping objects in the workspace object is set to
     * null.
     * 
     * @param wsDTO
     *            The workspace DTO ({@link WorkspaceDTO}) for which the mapping
     *            objects should be deleted.
     */
    private void deleteWorkspaceNetworkMappings(WorkspaceDTO wsDTO) {
        List<NetworkWorkspaceDTO> workspaceNetworksList = wsDTO.getWorkspaceNetworkDTOList();
        for (NetworkWorkspaceDTO workspaceNetwork : workspaceNetworksList) {
            deleteObject(workspaceNetwork);
        }
        // set the workspace network mapping to null in workspace object
        wsDTO.setWorkspaceNetworkDTOList(null);
    }

    @Override
    public WorkspaceDTO getDTO(String id) {
        return getDTO(WorkspaceDTO.class, id);
    }
    
    @Override
    public String getIdPrefix() {
        return messages.getProperty("workspace_id.prefix");
    }
}