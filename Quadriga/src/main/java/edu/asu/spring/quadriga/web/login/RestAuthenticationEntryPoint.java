package edu.asu.spring.quadriga.web.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

	/**
	 * This is the rest authentication entry point
	 */
	@Override
	public void commence(HttpServletRequest arg0, HttpServletResponse response,
			AuthenticationException arg2) throws IOException, ServletException {
		logger.info("Accessing the rest authentication");
		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
		
	}
}
