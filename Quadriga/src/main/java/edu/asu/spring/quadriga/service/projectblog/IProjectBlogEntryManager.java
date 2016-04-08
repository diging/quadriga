package edu.asu.spring.quadriga.service.projectblog;

import java.util.List;

import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.impl.projectblog.ProjectBlogEntryManager;

/**
 * This interface provides template for project blog entry manager
 * implementations.
 * 
 * @author PawanMahalle
 *
 * @see ProjectBlogEntryManager
 */
public interface IProjectBlogEntryManager {

    /**
     * populated the details of a project blog entry and uses DAO to add the
     * enry into database table <code>tbl_projectblogentry</code>
     * 
     * @param projectBlogEntry
     *            instance of {@linkplain IProjectBlogEntry} interface.
     */
    void addNewProjectBlogEntry(IProjectBlogEntry projectBlogEntry);

    /**
     * fetches all project blog entries under the given project identified by
     * project id.
     * 
     * @param projectId
     *            project id used to obtain the blog entries
     * @return List of project blog entries i.e. list of
     *         {@linkplain IProjectBlogEntry} interface.
     * @throws QuadrigaStorageException
     */
    List<IProjectBlogEntry> getProjectBlogEntryList(String projectId) throws QuadrigaStorageException;
}
