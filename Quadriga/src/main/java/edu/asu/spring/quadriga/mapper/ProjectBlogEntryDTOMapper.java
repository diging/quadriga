package edu.asu.spring.quadriga.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlogEntry;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;
import edu.asu.spring.quadriga.dto.ProjectBlogEntryDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This DTO mapper class provides the mapping from {@linkplain IProjectBlogEntry}
 * instance to {@linkplain ProjectBlogEntryDTO} instance.
 * 
 * @author PawanMahalle
 *
 */
@Service
public class ProjectBlogEntryDTOMapper extends BaseMapper {

    @Autowired 
    private IUserManager userManager;
    
    /**
     * generates {@linkplain ProjectBlogEntryDTO} when {@linkplain IProjectBlogEntry} is
     * provided
     * 
     * @param projectBlogEntry
     * @param userName
     * @return
     */
    public ProjectBlogEntryDTO getProjectBlogEntryDTO(IProjectBlogEntry projectBlogEntry) {

        ProjectBlogEntryDTO projectBlogEntryDTO = new ProjectBlogEntryDTO();

        projectBlogEntryDTO.setProjectBlogEntryId(projectBlogEntry.getProjectBlogEntryId());
        projectBlogEntryDTO.setTitle(projectBlogEntry.getTitle());
        projectBlogEntryDTO.setDescription(projectBlogEntry.getDescription());
        projectBlogEntryDTO.setCreatedDate(projectBlogEntry.getCreatedDate());

        String username = projectBlogEntry.getAuthor().getUserName();
        QuadrigaUserDTO author = getUserDTO(username);
        projectBlogEntryDTO.setProjectBlogEntryAuthorDTO(author);
        projectBlogEntryDTO.setAuthor(author.getUsername());

        projectBlogEntryDTO.setProjectid(projectBlogEntry.getProjectId());

        return projectBlogEntryDTO;
    }

    /**
     * generates {@linkplain IProjectBlogEntry} when {@linkplain ProjectBlogEntryDTO} is
     * provided
     * 
     * @param projectBlogEntryDTO
     * @return
     * @throws QuadrigaStorageException 
     */
    public IProjectBlogEntry getProjectBlogEntry(ProjectBlogEntryDTO projectBlogEntryDTO) throws QuadrigaStorageException {
        IProjectBlogEntry projectBlogEntry = new ProjectBlogEntry();

        projectBlogEntry.setProjectBlogEntryId(projectBlogEntryDTO.getProjectBlogEntryId());
        projectBlogEntry.setTitle(projectBlogEntryDTO.getTitle());
        projectBlogEntry.setDescription(projectBlogEntryDTO.getDescription());
        projectBlogEntry.setCreatedDate(projectBlogEntryDTO.getCreatedDate());
        projectBlogEntry.setProjectId(projectBlogEntryDTO.getProjectid());
        String author = projectBlogEntryDTO.getAuthor();
        projectBlogEntry.setAuthor(userManager.getUser(author));

        return projectBlogEntry;
    }
}
