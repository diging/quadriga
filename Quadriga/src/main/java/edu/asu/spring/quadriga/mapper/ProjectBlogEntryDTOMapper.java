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
    IUserManager userManager;
    
    /**
     * generates {@linkplain ProjectBlogEntryDTO} when {@linkplain IProjectBlogEntry} is
     * provided
     * 
     * @param projectBlogEntry
     * @param userName
     * @return
     */
    public ProjectBlogEntryDTO getProjectBlogDTO(IProjectBlogEntry projectBlogEntry) {

        ProjectBlogEntryDTO projectBlogDTOEntry = new ProjectBlogEntryDTO();

        projectBlogDTOEntry.setProjectBlogEntryId(projectBlogEntry.getProjectBlogEntryId());
        projectBlogDTOEntry.setTitle(projectBlogEntry.getTitle());
        projectBlogDTOEntry.setDescription(projectBlogEntry.getDescription());
        projectBlogDTOEntry.setCreatedDate(projectBlogEntry.getCreatedDate());

        String username = projectBlogEntry.getAuthor().getName();
        QuadrigaUserDTO author = getUserDTO(username);
        projectBlogDTOEntry.setProjectBlogEntryAuthorDTO(author);
        projectBlogDTOEntry.setAuthor(author.getUsername());

        projectBlogDTOEntry.setProjectid(projectBlogEntry.getProjectId());

        return projectBlogDTOEntry;
    }

    /**
     * generates {@linkplain IProjectBlogEntry} when {@linkplain ProjectBlogEntryDTO} is
     * provided
     * 
     * @param projectBlogEntryDTO
     * @return
     * @throws QuadrigaStorageException 
     */
    public IProjectBlogEntry getProjectBlog(ProjectBlogEntryDTO projectBlogEntryDTO) throws QuadrigaStorageException {
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
