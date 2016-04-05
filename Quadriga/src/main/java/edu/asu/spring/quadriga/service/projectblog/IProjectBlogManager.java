package edu.asu.spring.quadriga.service.projectblog;

import java.util.List;

import edu.asu.spring.quadriga.domain.projectblog.IProjectBlog;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.impl.projectblog.ProjectBlogManager;

/**
 * This interface provides template for project blog manager implementations.
 * 
 * @author PawanMahalle
 *
 * @see ProjectBlogManager
 */
public interface IProjectBlogManager {

    /**
     * adds new project blog object to database table
     * <code>tbl_projectblog</code>
     * 
     * @param projectBlog
     *            instance of {@linkplain IProjectBlog} interface.
     * @param userName
     *            name of the user who is logged in
     */
    void addNewProjectBlog(IProjectBlog projectBlog, String userName);

    /**
     * fetches all the project blog entries under the given project
     * 
     * @param projectId
     *            project id used to obtain the blog entries
     * @return List of project blog entries i.e. list of
     *         {@linkplain IProjectBlog} interface.
     * @throws QuadrigaStorageException
     */
    List<IProjectBlog> getProjectBlogList(String projectId) throws QuadrigaStorageException;
}
