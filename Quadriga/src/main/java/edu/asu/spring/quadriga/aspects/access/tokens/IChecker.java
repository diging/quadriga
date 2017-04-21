package edu.asu.spring.quadriga.aspects.access.tokens;

import java.io.IOException;
import java.security.GeneralSecurityException;

import edu.asu.spring.quadriga.aspects.access.openid.google.CheckerResult;
import edu.asu.spring.quadriga.web.exceptions.InvalidTokenException;
import edu.asu.spring.quadriga.web.exceptions.ServerMisconfigurationException;


public interface IChecker {
    public String getId();

    public CheckerResult validateToken(String token, String appId)
            throws GeneralSecurityException, IOException, InvalidTokenException, ServerMisconfigurationException;
}
