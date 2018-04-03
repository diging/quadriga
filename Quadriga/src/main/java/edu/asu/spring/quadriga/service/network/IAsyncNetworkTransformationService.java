package edu.asu.spring.quadriga.service.network;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

public interface IAsyncNetworkTransformationService {

    public abstract String submitNetworkTransformationRequest(String conceptId, IProject project);
    public abstract TransformationRequestStatus getTransformationRequestStatus(String token);
    public abstract ITransformedNetwork getTransformedNetwork(String token);

}
