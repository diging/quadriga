package edu.asu.spring.quadriga.service.passthroughproject;

import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProjectInfo;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;

public interface IPassThroughProjectDocumentReader {

    String getNetwork(String xml);

    PassThroughProjectInfo getPassThroughProjectInfo(String xml) throws DocumentParserException;

}
