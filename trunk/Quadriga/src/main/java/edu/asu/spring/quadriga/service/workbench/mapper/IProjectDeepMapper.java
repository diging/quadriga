package edu.asu.spring.quadriga.service.workbench.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface provides methods for mapping between Project DTOs and Service layer as a Deep mapping. 
 * {@link Project} object should be filled completely by classes which implement this interface.
 *  
 * @author Lohith Dwaraka
 *
 */
public interface IProjectDeepMapper {

	public abstract IProject getProjectDetails(String projectId)
			throws QuadrigaStorageException;


}