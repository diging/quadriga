package edu.asu.spring.quadriga.service.passthroughproject;

import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProjectInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;

public interface IPassThroughProjectDocumentReader {

    IPassThroughProject getPassThroughProject(PassThroughProjectInfo passThroughProjectInfo);

    String getNetwork(String xml);

    PassThroughProjectInfo getPassThroughProjectInfo(String xml) throws DocumentParserException;

}
