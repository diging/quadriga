package edu.asu.spring.quadriga.service.network;
import edu.asu.spring.quadriga.domain.workbench.IProject;
/**
 * Interface to implement AsynNetworkTransformationService
 * 
 * @author Chiraag Subramanian
 *
 */
public interface IAsyncNetworkTransformationService {

    public abstract String submitNetworkTransformationRequest(String conceptId, IProject project);
    public abstract AsyncTransformationResult getTransformationResult(String token);

}
