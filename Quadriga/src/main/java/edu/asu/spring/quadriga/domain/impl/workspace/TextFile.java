package edu.asu.spring.quadriga.domain.impl.workspace;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;

/**
 * 
 * Domain object for handling Text File Operations. Text files have the following properties:
 * 
 * <ul>
 *  <li>fileName: a filename in the file system</li>
 *  <li>projectId: id of the project a file belongs to</li>
 *  <li>workspaceId: id of the workspace a file belongs to</li>
 *  <li>textId: id of the text in the database</li>
 *  <li>fileContent: content of file</li>
 *  <li>refId: URI of the text in an external repository</li>
 *  <li>accessibility: PUBLIC or PRIVATE. only public text can be seen from users that do not belong
 *  to the project</li>
 *  <li>textFileURIPrefix: prefix use to generate the interal text URI</li>
 *  <li>title: title of the text</li>
 *  <li>author: author of the text</li>
 *  <li>creationDate: date on which the text was created (note: this is not the creation date
 *  in the system, but the date a text was originally created/published.</li>
 *  <li>snippet: this variable is not used, but is required to make the class work with JSP pages</li>
 *  <li>snippetLength: length of generated snippet</li>
 *  <li>presentationUrl: URL for a webpresenation of the text outside of Quadriga (which is different
 *  than the refId handle</li> 
 *  
 * </ul>
 * 
 * @author Nischal Samji, Julia Damerow     
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
    private String textFileURIPrefix;
    private String title;
    private String author;
    private String creationDate;
    private String snippet;
    private int snippetLength = 20;
    private String presentationUrl;

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

    /**
     * This method returns the URI for a text file. It uses the prefix set
     * through setTextFileURIPrefix() and appends the id of the text.
     * 
     * @return A URI for a text.
     */
    public String getTextFileURI() {
        return textFileURIPrefix + this.getTextId();
    }

    /**
     * This is the setter method for the URI prefix used for text files.
     * 
     * @param uriPrefix
     *            The prefix that should be used to generate the URI for this
     *            text file.
     */
    public void setTextFileURIPrefix(String uriPrefix) {
        this.textFileURIPrefix = uriPrefix;
    }

    /**
     * Returns a snippet of the text content. The snippet will
     * start at the first word of the text and have either as many words as specified
     * in snippetLength or if the text is shorter than snippetLength, the whole text.
     * Set the snippet length with {@link #setSnippetLength(int) setSnippetLength}.
     * 
     * @param numberOfWords Number of words in the snippet.
     * @return The generated snippet.
     */
    @Override
    public String getSnippet() {
        if (this.fileContent == null) {
            return "";
        }
        String[] words = fileContent.split(" ");
        
        StringBuffer snippet = new StringBuffer();
        for (int i = 0; i<snippetLength && i < words.length; i++) {
            snippet.append(words[i] + " ");
        }
        return snippet.toString().trim();
    }
    
    @Override
    public int getSnippetLength() {
        return snippetLength;
    }

    @Override
    public void setSnippetLength(int snippetLength) {
        this.snippetLength = snippetLength;
    }

    @Override
    public String getPresentationUrl() {
        return presentationUrl;
    }

    @Override
    public void setPresentationUrl(String presentationUrl) {
        this.presentationUrl = presentationUrl;
    }

}
