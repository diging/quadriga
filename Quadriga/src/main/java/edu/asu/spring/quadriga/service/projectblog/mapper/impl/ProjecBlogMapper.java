package edu.asu.spring.quadriga.service.projectblog.mapper.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.projectblog.IProjectBlogDAO;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlog;
import edu.asu.spring.quadriga.dto.ProjectBlogDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectBlogDTOMapper;
import edu.asu.spring.quadriga.service.projectblog.mapper.IProjectBlogMapper;

/**
 * This interface provides method to communicate with DAO and fetch related objects.
 *  
 * @author PawanMahalle
 *
 */
@Service
public class ProjecBlogMapper implements IProjectBlogMapper {

    @Autowired
    IProjectBlogDAO projectBlogDAO;
    
    @Autowired
    ProjectBlogDTOMapper projectBlogDTOMapper;
    
    @Override
    public List<IProjectBlog> getProjectBlogListForProject(String projectid) throws QuadrigaStorageException {
        List<IProjectBlog> projectBlogList = null;
        List<ProjectBlogDTO> projectBlogDTOList = projectBlogDAO.getProjectBlogDTOList(projectid);
        
        projectBlogList = projectBlogDTOMapper.getProjectBlogListFromDTOList(projectBlogDTOList);
        
        return projectBlogList;
    }

}
