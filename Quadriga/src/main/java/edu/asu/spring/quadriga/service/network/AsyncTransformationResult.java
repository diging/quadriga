package edu.asu.spring.quadriga.service.network;

import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

public class AsyncTransformationResult {
    
    private ITransformedNetwork network;
    private TransformationRequestStatus status;
    
    public ITransformedNetwork getNetwork() {
        return network;
    }
    public void setNetwork(ITransformedNetwork network) {
        this.network = network;
    }
    public TransformationRequestStatus getStatus() {
        return status;
    }
    public void setStatus(TransformationRequestStatus status) {
        this.status = status;
    }
    
    
}
