package edu.asu.spring.quadriga.service.network;
/**
 * This enum defines status for asynchronous network transformation processing. 
 * @author Chiraag Subramanian
 *
 */
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
