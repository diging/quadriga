package edu.asu.spring.quadriga.service.impl.projectblog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.projectblog.IProjectBlogEntryDAO;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;
import edu.asu.spring.quadriga.dto.ProjectBlogEntryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectBlogEntryDTOMapper;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogEntryManager;

/**
 * This class provides method to perform operations on {@linkplain IProjectBlogEntry}
 * instance.
 * 
 * @author PawanMahalle
 *
 */
@Service
public class ProjectBlogEntryManager implements IProjectBlogEntryManager {

    @Autowired
    private IProjectBlogEntryDAO projectBlogEntryDAO;

    @Autowired
    private ProjectBlogEntryDTOMapper projectBlogDTOMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addNewProjectBlogEntry(IProjectBlogEntry projectBlogEntry) {

        projectBlogEntry.setProjectBlogEntryId(projectBlogEntryDAO.generateUniqueID());
        projectBlogEntry.setCreatedDate(new Date());

        ProjectBlogEntryDTO projectBlogDTO = projectBlogDTOMapper.getProjectBlogDTO(projectBlogEntry);

        projectBlogEntryDAO.saveNewDTO(projectBlogDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IProjectBlogEntry> getProjectBlogEntryList(String projectId) throws QuadrigaStorageException {

        List<ProjectBlogEntryDTO> projectBlogEntryDTOList = projectBlogEntryDAO.getProjectBlogEntryDTOList(projectId);
        
        List<IProjectBlogEntry> projectBlogEntryList = new ArrayList<>();
        for(ProjectBlogEntryDTO projectBlogEntryDTO: projectBlogEntryDTOList){
            projectBlogEntryList.add(projectBlogDTOMapper.getProjectBlog(projectBlogEntryDTO));
        }
        return projectBlogEntryList;
    }

}
