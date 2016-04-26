package edu.asu.spring.quadriga.domain.impl.projectblog;

import java.util.Date;

import org.jsoup.Jsoup;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;

/**
 * This class holds details of project blog entry and implements
 * {@linkplain IProjectBlogEntry} interface.
 * 
 * @author Pawan Mahalle
 *
 */
public class ProjectBlogEntry implements IProjectBlogEntry {

    private String projectBlogEntryId;
    private String title;
    private String description;
    private Date createdDate;
    private String projectId;
    private IUser author;

    /**
     * @return the projectBlogEntryId
     */
    public String getProjectBlogEntryId() {
        return projectBlogEntryId;
    }

    /**
     * @param projectBlogEntryId
     *            the projectBlogEntryId to set
     */
    public void setProjectBlogEntryId(String projectBlogEntryId) {
        this.projectBlogEntryId = projectBlogEntryId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the projectId
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * @param projectId
     *            the projectId to set
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * @return the author
     */
    @Override
    public IUser getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    @Override
    public void setAuthor(IUser author) {
        this.author = author;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStripped(String input, int wordCount) {
        for (int i = 0; i < input.length(); i++) {
            // When a space is encountered, reduce words remaining by 1.
            if (input.charAt(i) == ' ') {
                wordCount--;
            }
            // If no more words remaining, return a substring.
            if (wordCount == 0) {
                return input.substring(0, i);
            }
        }
        // Error case.
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSnippet(int wordCount) {
        org.jsoup.nodes.Document dom = Jsoup.parse(this.description);
        String strippedtext = dom.text();
        return getStripped(strippedtext, wordCount);
    }

}
