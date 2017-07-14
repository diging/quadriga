package edu.asu.spring.quadriga.domain.network.impl;

import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;

public class Transformation implements ITransformation {
    
    private String patternFilePath;
    private String transformationFilePath;
   
    @Override
    public void setPatternFilePath(String patternFilePath) {
        this.patternFilePath = patternFilePath;
    }

    @Override
    public void setTransformationFilePath(String transformationFilePath) {
        this.transformationFilePath = transformationFilePath;
    }

    @Override
    public String getPatternFilePath() {
        return patternFilePath;
    }

    @Override
    public String getTransformationFilePath() {
        return transformationFilePath;
    }

}
