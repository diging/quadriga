package edu.asu.spring.quadriga.rest.open.model;

public class TransformationRequest {

    private String pattern;
    private String transformation;
    
    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    public String getTransformation() {
        return transformation;
    }
    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }
}
