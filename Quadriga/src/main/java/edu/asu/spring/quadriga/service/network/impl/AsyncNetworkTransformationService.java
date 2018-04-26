package edu.asu.spring.quadriga.service.network.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.TransformationRequestStatus;
import edu.asu.spring.quadriga.service.network.AsyncTransformationResult;
import edu.asu.spring.quadriga.service.network.IAsyncNetworkTransformationService;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.web.network.INetworkStatus;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * This class is responsible for initiating and managing async transformation requests.
 * 
 * @author Chiraag Subramanian
 *
 */
@Service
public class AsyncNetworkTransformationService implements IAsyncNetworkTransformationService{
    
    
    @Autowired
    private INetworkTransformationManager transformationManager;
    
    @Autowired
    @Qualifier("ehcache")
    private CacheManager cacheManager;
    
    private Cache cache;
    
    private final Logger logger = LoggerFactory.getLogger(AsyncNetworkTransformationService.class);
    
    
    @PostConstruct
    public void init() {
        cache = cacheManager.getCache("asyncTransformedNetworks");
    }
    
    
    private String generateToken(String conceptURI){
        return conceptURI.substring(conceptURI.lastIndexOf('/') + 1);
    }
    
    /**
     * This method submits a request for network transformation which is handled asynchronously.
     * 
     * @param conceptId: the conceptId to be searched in the project's network
     * @param project : the project whose network will be searched and transformed
     * @return token : a token corresponding to the network transformation request or null to indicate error
     */
    @Override
    public String submitNetworkTransformationRequest(String conceptId, IProject project){
        String token = generateToken(conceptId);
        // Submit transformation request if (i) token is not in cache or (ii) token (key) is in cache, but the element (value) has expired
        if(!cache.isKeyInCache(token) || cache.get(token) == null || cache.isExpired(cache.get(token))){
            try {
                cache.put(new Element(token, transformationManager.getSearchTransformedNetwork(project.getProjectId(), conceptId, INetworkStatus.APPROVED)));
            } catch (IllegalArgumentException | IllegalStateException | CacheException | QuadrigaStorageException e) {
                logger.error("Error while submitting asynchronous network transformation request.",e);
                return null;
            }
       }
        return token;
    }
    
    /**
     * This method returns the result of the asynchronous network transformation request.
     * 
     * @param token : the token corresponding to the network transformation request
     * @return result : asynchronous network transformation result comprising of transformed network and transformation status.
     *         transformation status- i) invalid - when request token does not exist in cache or the cache entry for the token has expired
     *                                ii) failed - when processing fails due to server error
     *                                iii) running - when transformation request is in progress
     *                                iv) complete - when transformation result is available    
     */
    @Override
    public AsyncTransformationResult getTransformationResult(String token){
        AsyncTransformationResult result = new AsyncTransformationResult();
        TransformationRequestStatus transformationStatus = TransformationRequestStatus.INVALID;
        ITransformedNetwork transformedNetwork = null;
        if(token == null || !cache.isKeyInCache(token) || cache.get(token) == null || cache.isExpired(cache.get(token))){
            transformationStatus = TransformationRequestStatus.INVALID;
        }else{
            Future<ITransformedNetwork> futureResult = (Future<ITransformedNetwork>) cache.get(token).getObjectValue();
            if(futureResult == null){
                transformationStatus = TransformationRequestStatus.INVALID;
            }
            else if(!futureResult.isDone()){
                transformationStatus = TransformationRequestStatus.RUNNING;
            }
            else{
                try {
                    transformedNetwork = futureResult.get();
                    transformationStatus = TransformationRequestStatus.COMPLETE;
                }catch (InterruptedException | ExecutionException e) {
                    logger.error("Exception while retrieving the result", e);
                    transformationStatus = TransformationRequestStatus.FAILED;
                    cache.remove(token);
                }
            }
        }
        
        result.setNetwork(transformedNetwork);
        result.setStatus(transformationStatus);
        
        return result;
    }
}
