package edu.asu.spring.quadriga.domain.impl.projectblog;

import java.util.Date;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlog;

/**
 * This class is concrete implementation of {@linkplain IProjectBlog} interface
 * and holds the project blog related details.
 * 
 * @author Pawan Mahalle
 *
 */
public class ProjectBlog implements IProjectBlog {
    private String projectBlogId;
    private String title;
    private String description;
    private Date createdDate;
    private String projectId;
    private String author;

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#
     * getProjectBlogId()
     */
    @Override
    public String getProjectBlogId() {
        return projectBlogId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#
     * setProjectBlogId(java.lang.String)
     */
    @Override
    public void setProjectBlogId(String projectBlogId) {
        this.projectBlogId = projectBlogId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#getTitle()
     */
    @Override
    public String getTitle() {
        return title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#setTitle(
     * java.lang.String)
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#
     * getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#
     * setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#
     * getCreatedDate()
     */
    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#
     * setCreatedDate(java.util.Date)
     */
    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#getProjectId
     * ()
     */
    @Override
    public String getProjectId() {
        return projectId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.domain.impl.projectblog.IProjectBlog#setProjectId
     * (java.lang.String)
     */
    @Override
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjectBlog other = (ProjectBlog) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (projectId == null) {
            if (other.projectId != null)
                return false;
        } else if (!projectId.equals(other.projectId))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
}
