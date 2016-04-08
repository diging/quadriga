package edu.asu.spring.quadriga.dao.impl.projectblog;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.projectblog.IProjectBlogEntryDAO;
import edu.asu.spring.quadriga.dto.ProjectBlogEntryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class acts as data access object for {@linkplain ProjectBlogEntryDTO} class.
 * 
 * @author PawanMahalle
 *
 */
@Service
public class ProjectBlogEntryDAO extends BaseDAO<ProjectBlogEntryDTO>implements IProjectBlogEntryDAO {

    @Resource(name = "projectconstants")
    private Properties messages;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdPrefix() {
        return messages.getProperty("projectblogentry_id.prefix");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectBlogEntryDTO getProjectBlogEntryDTO(String id) {
        return getDTO(ProjectBlogEntryDTO.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectBlogEntryDTO getDTO(String id) {
        return getProjectBlogEntryDTO(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<ProjectBlogEntryDTO> getProjectBlogEntryDTOList(String projectId) throws QuadrigaStorageException {
       
        if (projectId == null || projectId.equals(""))
            return null;

        // Create a query to get all projects
        Query query = sessionFactory.getCurrentSession().getNamedQuery(
                "ProjectBlogEntryDTO.findByProjectId");
        query.setParameter("projectId", projectId);

        @SuppressWarnings("unchecked")
        List<ProjectBlogEntryDTO> projectBlogEntryDTOList = query.list();

        return projectBlogEntryDTOList;
    }
    
}