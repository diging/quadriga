package edu.asu.spring.quadriga.domain.workspace;

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
    
    public abstract String getTextFileURI();

    /**
     * @param uriPrefix
     *      Prefix of the URI specified in the properties file.
     *      eg: http://quadriga.io/text/textid
     * 
     * Sets the URI corresponding to the TextFile. 
     */
    public abstract void setTextFileURI(String uriPrefix);

}
