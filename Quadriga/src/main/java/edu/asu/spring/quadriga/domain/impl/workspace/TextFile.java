package edu.asu.spring.quadriga.domain.impl.workspace;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;

/**
 * @author Nischal Samji
 * 
 *         Domain object for handling Text File Operations.
 *
 */
public class TextFile implements ITextFile {

    private String fileName;
    private String projectId;
    private String workspaceId;
    private String textId;
    private String fileContent;
    private String refId;
    private ETextAccessibility accessibility;
    private String textFileURI;
    
    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getProjectId() {
        return projectId;
    }

    @Override
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String getWorkspaceId() {
        return workspaceId;
    }

    @Override
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Override
    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public ETextAccessibility getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(ETextAccessibility accessibility) {
        this.accessibility = accessibility;
    }

    public String getTextFileURI() {
        return textFileURI;
    }

    public void setTextFileURI(String uriPrefix) {
        this.textFileURI = uriPrefix + this.getTextId();
    }

}
