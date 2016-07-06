package edu.asu.spring.quadriga.domain.projectblog;

import java.util.Date;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlogEntry;

/**
 * Interface for project blog entry objects.
 * 
 * @author PawanMahalle
 *
 * @see ProjectBlogEntry
 */
public interface IProjectBlogEntry {

    String getProjectBlogEntryId();

    void setProjectBlogEntryId(String projectBlogEntryId);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    Date getCreatedDate();

    void setCreatedDate(Date createdDate);

    String getProjectId();

    void setProjectId(String projectId);

    void setAuthor(IUser author);

    IUser getAuthor();

    /**
     * returns the first n words of the blog description where n is 
     * the word count
     * @param wordCount
                 the count of number of words from the blog description
     * @return returns a string which is the snippet of the blog
     */
    String getSnippet(int wordCount);
}