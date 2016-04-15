package edu.asu.spring.quadriga.dao.impl.projectblog;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.projectblog.IProjectBlogEntryDAO;
import edu.asu.spring.quadriga.dto.ProjectBlogEntryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class acts as data access object for {@linkplain ProjectBlogEntryDTO}
 * class.
 * 
 * @author PawanMahalle
 *
 */
@Service
public class ProjectBlogEntryDAO extends BaseDAO<ProjectBlogEntryDTO> implements IProjectBlogEntryDAO {

    private static final int DEFAULT_LIST_COUNT = 0;

    @Resource(name = "projectconstants")
    private Properties messages;

    @Autowired
    private Environment env;

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
        Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectBlogEntryDTO.findByProjectId");

        // Check if property to set limit on number of project blog entries to
        // be fetched is specified in as environment variable.
        if (env.getProperty("projectblogentry.list.count") != null) {
            try {
                query.setMaxResults(Integer.parseInt(env.getProperty("projectblogentry.list.count")));
            } catch (NumberFormatException ex) {
                // If invalid numeric value is set in the environment property,
                // use default value
                query.setMaxResults(DEFAULT_LIST_COUNT);
            }
        } else {
            // If the environment property is not present, use default value
            query.setMaxResults(DEFAULT_LIST_COUNT);
        }

        query.setParameter("projectId", projectId);

        @SuppressWarnings("unchecked")
        List<ProjectBlogEntryDTO> projectBlogEntryDTOList = query.list();

        return projectBlogEntryDTOList;
    }

}