package edu.asu.spring.quadriga.service.transformation;

import edu.asu.spring.quadriga.service.transformation.exception.ResultExpiredException;

public interface ITransformationResultCache {

    String add(String id, String result);

    String get(String id) throws ResultExpiredException;

    void remove(String id);

}