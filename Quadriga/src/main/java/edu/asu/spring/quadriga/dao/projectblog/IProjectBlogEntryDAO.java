package edu.asu.spring.quadriga.dao.projectblog;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ProjectBlogEntryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface provides template for DAOs of {@linkplain ProjectBlogEntryDTO}
 * 
 * @author PawanMahalle
 *
 */
public interface IProjectBlogEntryDAO extends IBaseDAO<ProjectBlogEntryDTO> {

    /**
     * fetches {@linkplain ProjectBlogEntryDTO} instance when project blog id is
     * provided
     * 
     * @param id
     * @return
     */
    ProjectBlogEntryDTO getProjectBlogEntryDTO(String id);

    /**
     * fetches the list of project blog entries for given project when project
     * id is provided
     * 
     * @param projectid
     * @return
     * @throws QuadrigaStorageException
     */
    List<ProjectBlogEntryDTO> getProjectBlogEntryDTOList(String projectid) throws QuadrigaStorageException;

}
