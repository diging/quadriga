package edu.asu.spring.quadriga.domain.workspace;

public interface ITextFile {

    
    
    public abstract String getWorkspaceId();

    public abstract void setWorkspaceId(String workspaceId);

    public abstract String getProjectId();

    public abstract void setProjectId(String projectId);

    public abstract String getRefId();

    public abstract void setRefId(String refId);

    public abstract String getFileName();

    public abstract void setFileName(String fileName);
    
    public abstract String getFileContent();
    
    public abstract void setFileContent(String fileContent);

    
}
