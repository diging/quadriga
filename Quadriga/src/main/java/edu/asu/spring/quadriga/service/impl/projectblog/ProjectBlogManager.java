package edu.asu.spring.quadriga.service.impl.projectblog;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.projectblog.IProjectBlogDAO;
import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlog;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlog;
import edu.asu.spring.quadriga.dto.ProjectBlogDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectBlogDTOMapper;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogManager;
import edu.asu.spring.quadriga.service.projectblog.mapper.IProjectBlogMapper;

/**
 * This class provides method to perform operations on {@linkplain ProjectBlog}
 * instance.
 * 
 * @author PawanMahalle
 *
 */
@Service
public class ProjectBlogManager implements IProjectBlogManager {

    @Autowired
    private IProjectBlogDAO projectBlogDao;

    @Autowired
    private ProjectBlogDTOMapper projectBlogDTOMapper;

    @Autowired
    IProjectBlogMapper projectBlogMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addNewProjectBlog(IProjectBlog projectBlog, String userName) {

        projectBlog.setProjectBlogId(projectBlogDao.generateUniqueID());
        projectBlog.setCreatedDate(new Date());

        ProjectBlogDTO projectBlogDTO = projectBlogDTOMapper.getProjectBlogDTO(projectBlog, userName);

        projectBlogDao.saveNewDTO(projectBlogDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IProjectBlog> getProjectBlogList(String projectId) throws QuadrigaStorageException {

        List<IProjectBlog> projectBlogList = projectBlogMapper.getProjectBlogListForProject(projectId);

        return projectBlogList;
    }

}
