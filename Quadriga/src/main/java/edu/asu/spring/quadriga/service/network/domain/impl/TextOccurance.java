package edu.asu.spring.quadriga.service.network.domain.impl;

import java.util.List;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.workbench.IProject;

public class TextOccurance {

    private String textUri;
    private String author;
    private String title;
    private String creationDate;
    private String textId;
    private String contents;
    private IProject project;
    private ETextAccessibility access;
    
    private List<TextPhrase> textPhrases;
    
    public String getTextUri() {
        return textUri;
    }
    public void setTextUri(String textUri) {
        this.textUri = textUri;
    }
    
    public ETextAccessibility getAccess() {
        return access;
    }
    public void setAccess(ETextAccessibility access) {
        this.access = access;
    }
    public List<TextPhrase> getTextPhrases() {
        return textPhrases;
    }
    public void setTextPhrases(List<TextPhrase> textPhrases) {
        this.textPhrases = textPhrases;
    }
    public IProject getProject() {
        return project;
    }
    public void setProject(IProject project) {
        this.project = project;
    }
    public String getTextId() {
        return textId;
    }
    public void setTextId(String textId) {
        this.textId = textId;
    }
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
   
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((access == null) ? 0 : access.hashCode());
        result = prime * result + ((contents == null) ? 0 : contents.hashCode());
        if (project != null) {
            result = prime * result + ((project.getProjectId() == null) ? 0 : project.getProjectId().hashCode());
        }
        result = prime * result + ((textId == null) ? 0 : textId.hashCode());
        int phrasesSum = 0;
        if (textPhrases != null) {
            for (TextPhrase phrase : textPhrases) {
                phrasesSum += phrase == null ? 0 : phrase.hashCode();
            }
        }
        result = prime * result + phrasesSum;
        result = prime * result + ((textUri == null) ? 0 : textUri.hashCode());
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
        TextOccurance other = (TextOccurance) obj;
        if (access != other.access)
            return false;
        if (contents == null) {
            if (other.contents != null)
                return false;
        } else if (!contents.equals(other.contents))
            return false;
        if (project == null) {
            if (other.project != null)
                return false;
        } else if (project != null) {
            if (other.project == null)
                return false;
        } else if (!project.getProjectId().equals(other.project.getProjectId()))
            return false;
        if (textId == null) {
            if (other.textId != null)
                return false;
        } else if (!textId.equals(other.textId))
            return false;
        if (textPhrases == null) {
            if (other.textPhrases != null)
                return false;
        } else if (!textPhrases.containsAll(other.textPhrases) && other.textPhrases.containsAll(textPhrases))
            return false;
        if (textUri == null) {
            if (other.textUri != null)
                return false;
        } else if (!textUri.equals(other.textUri))
            return false;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (creationDate == null) {
            if (other.creationDate != null)
                return false;
        } else if (!creationDate.equals(other.creationDate))
            return false;
        return true;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
