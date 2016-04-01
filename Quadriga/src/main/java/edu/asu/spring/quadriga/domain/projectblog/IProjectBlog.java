package edu.asu.spring.quadriga.domain.projectblog;

import java.util.Date;

/**
 * Interface for {@linkplain ProjectBlog} object
 * 
 * @author PawanMahalle
 *
 */
public interface IProjectBlog {

    String getProjectBlogId();

    void setProjectBlogId(String projectBlogId);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    String getAuthor();

    void setAuthor(String author);

    Date getCreatedDate();

    void setCreatedDate(Date createdDate);

    String getProjectId();

    void setProjectId(String projectId);

}