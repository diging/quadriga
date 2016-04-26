package edu.asu.spring.quadriga.web.transformation;

/**
 * BackingBean class stores the data in the form to upload transformation files
 * 
 * @author JayaVenkat
 *
 */
public class TransformFilesBackingBean {

    private String title;
    private String description;
    private String patternTitle;
    private String patternDescription;
    private String mappingTitle;
    private String mappingDescription;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMappingTitle() {
        return mappingTitle;
    }

    public void setMappingTitle(String mappingTitle) {
        this.mappingTitle = mappingTitle;
    }

    public String getMappingDescription() {
        return mappingDescription;
    }

    public void setMappingDescription(String mappingDescription) {
        this.mappingDescription = mappingDescription;
    }

    public String getPatternTitle() {
        return patternTitle;
    }

    public void setPatternTitle(String patternTitle) {
        this.patternTitle = patternTitle;
    }

    public String getPatternDescription() {
        return patternDescription;
    }

    public void setPatternDescription(String patternDescription) {
        this.patternDescription = patternDescription;
    }

}
