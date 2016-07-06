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
 * This class provides method to add and retrieve {@linkplain IProjectBlogEntry}
 * instances from table <tbl_projectblogentry>
 * 
 * @author PawanMahalle
 *
 */
@Service
public class ProjectBlogEntryManager implements IProjectBlogEntryManager {

    @Autowired
    private IProjectBlogEntryDAO projectBlogEntryDAO;

    @Autowired
    private ProjectBlogEntryDTOMapper projectBlogEntryDTOMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addNewProjectBlogEntry(IProjectBlogEntry projectBlogEntry) {

        projectBlogEntry.setProjectBlogEntryId(projectBlogEntryDAO.generateUniqueID());
        projectBlogEntry.setCreatedDate(new Date());

        ProjectBlogEntryDTO projectBlogEntryDTO = projectBlogEntryDTOMapper.getProjectBlogEntryDTO(projectBlogEntry);

        projectBlogEntryDAO.saveNewDTO(projectBlogEntryDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IProjectBlogEntry> getProjectBlogEntryList(String projectId, Integer limit)
            throws QuadrigaStorageException {

        List<ProjectBlogEntryDTO> projectBlogEntryDTOList = projectBlogEntryDAO
                .getProjectBlogEntryDTOListByProjectId(projectId, limit);

        List<IProjectBlogEntry> projectBlogEntryList = new ArrayList<>();
        for (ProjectBlogEntryDTO projectBlogEntryDTO : projectBlogEntryDTOList) {
            projectBlogEntryList.add(projectBlogEntryDTOMapper.getProjectBlogEntry(projectBlogEntryDTO));
        }
        return projectBlogEntryList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IProjectBlogEntry getProjectBlogEntryDetails(String projectBlogEntryId) throws QuadrigaStorageException {

        ProjectBlogEntryDTO projectBlogEntryDTO = projectBlogEntryDAO.getDTO(projectBlogEntryId);
        IProjectBlogEntry projectBlogEntry = projectBlogEntryDTOMapper.getProjectBlogEntry(projectBlogEntryDTO);
        return projectBlogEntry;
    }

}
