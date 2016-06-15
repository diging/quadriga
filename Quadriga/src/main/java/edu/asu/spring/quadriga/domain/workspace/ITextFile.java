package edu.asu.spring.quadriga.domain.workspace;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;

/**
 * @author Nischal Samji Backing bean for Text File Operations.
 *
 */
public interface ITextFile {

    public abstract String getWorkspaceId();

    public abstract void setWorkspaceId(String workspaceId);

    public abstract String getProjectId();

    public abstract void setProjectId(String projectId);

    public abstract String getTextId();

    public abstract void setTextId(String textId);

    public abstract String getRefId();

    public abstract void setRefId(String refId);

    public abstract String getFileName();

    public abstract void setFileName(String fileName);

    public abstract String getFileContent();

    public abstract void setFileContent(String fileContent);
    
    public abstract ETextAccessibility getAccessibility();

    public abstract void setAccessibility(ETextAccessibility accessibility);

    public abstract String getTextFileURI();

    /**
     * This method sets the URI of the TextFile by appending the TextID of the
     * Textfile to the URI Prefix passed to this method.
     * 
     * @param uriPrefix
     *            Prefix for TextFile URI.
     * 
     */
    public abstract void setTextFileURI(String uriPrefix);

}
