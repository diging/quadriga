package edu.asu.spring.quadriga.service.network.domain.impl;

import java.util.List;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.workbench.IProject;

public class TextOccurance {

    private String textUri;
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
   
    
    
}
