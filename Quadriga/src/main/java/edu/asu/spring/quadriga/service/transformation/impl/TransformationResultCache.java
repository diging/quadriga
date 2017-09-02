package edu.asu.spring.quadriga.service.transformation.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.transformation.ITransformationResultCache;
import edu.asu.spring.quadriga.service.transformation.exception.ResultExpiredException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class TransformationResultCache implements ITransformationResultCache {
    
    @Autowired
    @Qualifier("ehcache")
    private CacheManager cacheManager;
    
    private Cache cache;
    
    @PostConstruct
    public void init() {
        cache = cacheManager.getCache("transformedNetworks");
    }
    
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.transformation.impl.ITransformationResultManager#add(java.lang.String)
     */
    @Override
    public String add(String id, String result) {
        cache.put(new Element(id, result));
        return id;
    }
    
    @Override
    public void remove(String id) {
        cache.remove(id);
    }
     
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.transformation.impl.ITransformationResultManager#get(java.lang.String)
     */
    @Override
    public String get(String id) throws ResultExpiredException {
        Element element = cache.get(id);
        if (element != null) {
            if (element.isExpired()) {
                throw new ResultExpiredException("Element with id " + id + " has expired.");
            }
            Object result = element.getObjectValue();
            if (result != null) {
                return result.toString();
            }
        }
        return null;
    }
}
