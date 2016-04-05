package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlog;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlog;
import edu.asu.spring.quadriga.dto.ProjectBlogDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;

/**
 * This DTO mapper class provides the mapping from {@linkplain IProjectBlog}
 * instance to {@linkplain ProjectBlogDTO} instance.
 * 
 * @author PawanMahalle
 *
 */
@Service
public class ProjectBlogDTOMapper extends BaseMapper {

    /**
     * generates {@linkplain ProjectBlogDTO} when {@linkplain IProjectBlog} is
     * provided
     * 
     * @param projectBlog
     * @param userName
     * @return
     */
    public ProjectBlogDTO getProjectBlogDTO(IProjectBlog projectBlog, String userName) {

        ProjectBlogDTO projectBlogDTO = new ProjectBlogDTO();

        projectBlogDTO.setProjectBlogId(projectBlog.getProjectBlogId());
        projectBlogDTO.setTitle(projectBlog.getTitle());
        projectBlogDTO.setDescription(projectBlog.getDescription());
        projectBlogDTO.setCreatedDate(projectBlog.getCreatedDate());

        QuadrigaUserDTO author = getUserDTO(userName);
        projectBlogDTO.setProjectBlogAuthorDTO(author);
        projectBlogDTO.setAuthor(author.getUsername());

        projectBlogDTO.setProjectid(projectBlog.getProjectId());

        return projectBlogDTO;
    }

    /**
     * generates {@linkplain IProjectBlog} when {@linkplain ProjectBlogDTO} is
     * provided
     * 
     * @param projectBlogDTO
     * @return
     */
    public IProjectBlog getProjectBlog(ProjectBlogDTO projectBlogDTO) {
        IProjectBlog projectBlog = new ProjectBlog();

        projectBlog.setProjectBlogId(projectBlogDTO.getProjectBlogId());
        projectBlog.setTitle(projectBlogDTO.getTitle());
        projectBlog.setDescription(projectBlogDTO.getDescription());
        projectBlog.setCreatedDate(projectBlogDTO.getCreatedDate());
        projectBlog.setAuthor(projectBlogDTO.getAuthor());

        return projectBlog;
    }

    /**
     * generates list of {@linkplain IProjectBlog} instances when
     * {@linkplain ProjectBlogDTO} list is provided
     * 
     * @param projectBlogDTOList
     * @return
     */
    public List<IProjectBlog> getProjectBlogListFromDTOList(List<ProjectBlogDTO> projectBlogDTOList) {
        List<IProjectBlog> projectBlogList = new ArrayList<>();

        for (ProjectBlogDTO projectBlogDTO : projectBlogDTOList) {
            projectBlogList.add(getProjectBlog(projectBlogDTO));
        }

        return projectBlogList;
    }

}
