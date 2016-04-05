package edu.asu.spring.quadriga.service.projectblog.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.projectblog.IProjectBlog;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface provides method to communicate with DAO and fetch related objects.
 * 
 * @author Pawan Mahalle
 *
 */
public interface IProjectBlogMapper {

	public List<IProjectBlog> getProjectBlogListForProject(String projectId)
			throws QuadrigaStorageException;

}
