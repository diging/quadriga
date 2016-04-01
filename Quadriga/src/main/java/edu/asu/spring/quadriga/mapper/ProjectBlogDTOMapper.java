package edu.asu.spring.quadriga.mapper;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.projectblog.IProjectBlog;
import edu.asu.spring.quadriga.dto.ProjectBlogDTO;

@Service
public class ProjectBlogDTOMapper extends BaseMapper {
    
	public ProjectBlogDTO getProjectBlogDTO(IProjectBlog projectBlog) {
	    
	    ProjectBlogDTO projectBlogDTO = new ProjectBlogDTO();
		projectBlogDTO.setTitle(projectBlog.getTitle());
		projectBlogDTO.setDescription(projectBlog.getDescription());
		projectBlogDTO.setProjectBlogAuthorDTO(getUserDTO(projectBlog.getAuthor()));
		projectBlogDTO.setCreatedDate(projectBlog.getCreatedDate());
		projectBlogDTO.setAuthor(projectBlog.getAuthor());
		projectBlogDTO.setProjectid(projectBlog.getProjectId());
		
		return projectBlogDTO;
	}	
}
