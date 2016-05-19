package edu.asu.spring.quadriga.mapper.workbench;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectBaseMapper {

    public abstract IProject getProject(ProjectDTO projectDTO)
            throws QuadrigaStorageException;

    public abstract ProjectDTO getProjectDTO(IProject project);

    public abstract ProjectEditorDTO getProjectEditor(ProjectDTO project,
            String userName) throws QuadrigaStorageException;

    /**
     * This method associated the dictionary with the specified project.
     * 
     * @param project
     * @param dictionary
     * @param userName
     * @return ProjectDictionaryDTO object
     */
    public abstract ProjectDictionaryDTO getProjectDictionary(
            ProjectDTO project, DictionaryDTO dictionary, String userName);

    /**
     * This method associates the concept collection to the given project.
     * 
     * @param project
     * @param conceptCollection
     * @param userName
     * @return ProjectConceptCollectionDTO object
     */
    public abstract ProjectConceptCollectionDTO getProjectConceptCollection(
            ProjectDTO project, ConceptCollectionDTO conceptCollection,
            String userName);

    /**
     * This method associates the workspace with the given project
     * 
     * @param project
     * @param workspace
     * @return ProjectWorkspaceDTO object
     */
    public abstract ProjectWorkspaceDTO getProjectWorkspace(ProjectDTO project,
            WorkspaceDTO workspace);

}