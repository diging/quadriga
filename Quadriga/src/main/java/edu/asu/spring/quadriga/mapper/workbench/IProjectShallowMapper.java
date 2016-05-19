package edu.asu.spring.quadriga.mapper.workbench;

import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface has methods to implement the mapping of DTO object to Domain
 * objects for the service layer for Project. These methods need to map
 * {@link ProjectDTO} to {@link ProjectProxy} object.
 * 
 * @author Lohith Dwaraka
 *
 */
public interface IProjectShallowMapper extends IProjectBaseMapper {

    /**
     * This class should get a {@link IProject} of domain class type
     * {@link ProjectProxy} for a {@link ProjectDTO}.
     * 
     * @param projectDTO
     *            {@link ProjectDTO} object
     * @return Returns the {@link IProject} object
     * @throws QuadrigaStorageException
     *             Throws the storage exception when the method has issues to
     *             access the database
     */
    public abstract IProject getProjectDetails(ProjectDTO projectDTO)
            throws QuadrigaStorageException;
}
