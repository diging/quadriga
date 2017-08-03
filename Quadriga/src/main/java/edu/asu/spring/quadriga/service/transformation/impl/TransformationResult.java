package edu.asu.spring.quadriga.service.transformation.impl;

public class TransformationResult {

    private String id;
    private String result;
    private TransformationStatus status;
    
    public TransformationResult() {}
    
    public TransformationResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TransformationStatus getStatus() {
        return status;
    }

    public void setStatus(TransformationStatus status) {
        this.status = status;
    }

    public enum TransformationStatus {
        SUCCESS, FAILED, IN_PROGRESS, EXPIRED, UNKNOWWN;
    }
}
