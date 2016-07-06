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
    private String title;
    private String author;
    private String creationDate;
    
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

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
