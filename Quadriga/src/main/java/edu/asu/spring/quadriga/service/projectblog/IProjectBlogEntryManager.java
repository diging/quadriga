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
     * generates unique id and sets creation date for project blog
     * entry and uses DAO object to store the blog entry
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
     * @param count
     *            number of project blogs entries to fetch. If 'null' value is
     *            passed, no limit is imposed and all the entries are fetched.
     * @return list of project blog entries
     * @throws QuadrigaStorageException
     */
    List<IProjectBlogEntry> getProjectBlogEntryList(String projectId, Integer count) throws QuadrigaStorageException;
    
    /**
     * 
     * @param projectBlogEntryId
     *            blog entry id used to obtain the blog entry   
     * @return  project blog entry
     * @throws QuadrigaStorageException
     */
    IProjectBlogEntry getProjectBlogEntryDetails(String projectBlogEntryId) throws QuadrigaStorageException ;

}
