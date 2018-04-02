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
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.TransformationRequestStatus;
import edu.asu.spring.quadriga.service.network.IAsyncNetworkTransformationService;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.web.network.INetworkStatus;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * This class is responsible for initiating and managing async transformation requests.
 * It uses LRU cache to maintain the results for an hour.
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
    
    
    private String generateToken(String conceptId){
        return conceptId.substring(conceptId.lastIndexOf('/') + 1);
    }
    
    
    @Override
    public String submitNetworkTransformationRequest(String conceptId, IProject project){
        String token = generateToken(conceptId);
        if(!cache.isKeyInCache(token)){
             cache.put(new Element(token, transformationManager.getTransformedNetwork(project.getProjectId(), conceptId, INetworkStatus.APPROVED)));
        }
        return token;
    }
    
    @Override
    public TransformationRequestStatus getTransformationRequestStatus(String token){
        if(!cache.isKeyInCache(token)){
             return TransformationRequestStatus.FAILED;
        }
        Future<ITransformedNetwork> futureResult = (Future<ITransformedNetwork>) cache.get(token).getObjectValue();
        
        if(futureResult != null && !futureResult.isDone()){
             return TransformationRequestStatus.RUNNING;
        }
        
        return TransformationRequestStatus.COMPLETE;
    }
    
    @Override
    public ITransformedNetwork getTransformedNetwork(String token){
        if(getTransformationRequestStatus(token).equals(TransformationRequestStatus.COMPLETE)){
            try {
                    Future<ITransformedNetwork> futureResult = (Future<ITransformedNetwork>) cache.get(token).getObjectValue();
                    return futureResult.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Exception while retrieving the result", e);
                cache.remove(token);
                return null;
            }
        }
        return null;
    }
}
