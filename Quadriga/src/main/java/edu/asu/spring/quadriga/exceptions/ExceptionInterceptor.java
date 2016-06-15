/**
 * 
 */
package edu.asu.spring.quadriga.exceptions;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author satyaswaroop boddu
 * 
 */
@Aspect
@Component
public class ExceptionInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(ExceptionInterceptor.class);

	@AfterThrowing(pointcut = "within(edu.asu.spring.quadriga.web..*)", throwing = "t")
	public void toRuntimeException(Throwable t)
			throws QuadrigaStorageException, RestException,
			QuadrigaAccessException, QuadrigaException {
		if (!(t instanceof QuadrigaException)) {
			QuadrigaException se = new QuadrigaException(t.getMessage());
			se.setStackTrace(t.getStackTrace());
			throw se;
		}

	}

}
