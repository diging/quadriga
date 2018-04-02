package edu.asu.spring.quadriga.service.network;

public enum TransformationRequestStatus {
    COMPLETE("complete"), FAILED("failed"), RUNNING("running");
   
    private String statusCode;
    
    private TransformationRequestStatus(String statusCode){
        this.statusCode = statusCode;
    }
    
    public String getStatusCode(){
        return statusCode;
    }
}
