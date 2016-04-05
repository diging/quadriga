package edu.asu.spring.quadriga.dao.projectblog;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ProjectBlogDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface provides template for DAOs of {@linkplain ProjectBlogDTO}
 * 
 * @author PawanMahalle
 *
 */
public interface IProjectBlogDAO extends IBaseDAO<ProjectBlogDTO> {

    /**
     * fetches {@linkplain ProjectBlogDTO} instance when project blog id is
     * provided
     * 
     * @param id
     * @return
     */
    ProjectBlogDTO getProjectBlogDTO(String id);

    /**
     * fetches the list of project blog entries for given project when project
     * id is provided
     * 
     * @param projectid
     * @return
     * @throws QuadrigaStorageException
     */
    List<ProjectBlogDTO> getProjectBlogDTOList(String projectid) throws QuadrigaStorageException;

}
