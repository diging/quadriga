package edu.asu.spring.quadriga.dao.impl.projectblog;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.projectblog.IProjectBlogDAO;
import edu.asu.spring.quadriga.dto.ProjectBlogDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class acts as data access object for {@linkplain ProjectBlogDTO} class.
 * 
 * @author PawanMahalle
 *
 */
@Service
public class ProjectBlogDAO extends BaseDAO<ProjectBlogDTO>implements IProjectBlogDAO {

    @Resource(name = "projectconstants")
    private Properties messages;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdPrefix() {
        return messages.getProperty("projectblog_id.prefix");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectBlogDTO getProjectBlogDTO(String id) {
        return getDTO(ProjectBlogDTO.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectBlogDTO getDTO(String id) {
        return getProjectBlogDTO(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProjectBlogDTO> getProjectBlogDTOList(String projectId) throws QuadrigaStorageException {
       
        if (projectId == null || projectId.equals(""))
            return null;

        // Create a query to get all projects
        Query query = sessionFactory.getCurrentSession().getNamedQuery(
                "ProjectBlogDTO.findByProjectBlogId");
        query.setParameter("projectId", projectId);

        @SuppressWarnings("unchecked")
        List<ProjectBlogDTO> projectBlogDTOList = query.list();

        return projectBlogDTOList;
    }
    
}