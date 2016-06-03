package edu.asu.spring.quadriga.mapper.workbench;

import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface provides methods for mapping between Project DTOs and Service
 * layer as a Deep mapping. {@link Project} object should be filled completely
 * by classes which implement this interface.
 * 
 * @author Lohith Dwaraka
 *
 */
public interface IProjectDeepMapper extends IProjectBaseMapper {

    /**
     * This class should return a {@link IProject} object with domain type of
     * {@link Project} with complete details of the project based on
     * {@link IProject} ID.
     * 
     * @return Returns {@link IProject} object.
     * @throws QuadrigaStorageException
     *             Throws the storage exception when the method has issues to
     *             access the database
     */
    public abstract IProject getProject(ProjectDTO projectDto)
            throws QuadrigaStorageException;

}