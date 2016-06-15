package edu.asu.spring.quadriga.domain.network.tranform;

public interface ITransformation {

    public String getPatternFilePath();
    public String getTransformationFilePath();
    public abstract void setTransformationFilePath(String transformationFilePath);
    public abstract void setPatternFilePath(String patternFilePath);
}
