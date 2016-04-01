package edu.asu.spring.quadriga.dao.impl.projectblog;

import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dto.ProjectBlogDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class ProjectBlogDAO extends BaseDAO<ProjectBlogDTO>implements IProjectBlogDAO {

    @Autowired
    private IProjectDAO projectDAO;

    @Override
    public ProjectBlogDTO getDTO(String id) {
        return getDTO(ProjectBlogDTO.class, id);
    }
    
    /**
     * This method gets the project id for the workspace id.
     * 
     * @param workspaceId
     * @return Project Id - String object
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public String getCorrespondingProjectID(String projectBlogId)
            throws QuadrigaStorageException {
       
        return null;
    }

    @Resource(name = "projectconstants")
    private Properties messages;

    @Override
    public String getIdPrefix() {
        return messages.getProperty("projectblog_id.prefix");
    }


    @Override
    public void addProjectBlogDTO(ProjectBlogDTO projectBlogDTO) throws QuadrigaStorageException  {
        
        projectBlogDTO.setProjectDTO(projectDAO.getProjectDTO(projectBlogDTO.getProjectid()));
        projectBlogDTO.setProjectBlogId(generateUniqueID());
        
        saveNewDTO(projectBlogDTO);
    }
    
    
}