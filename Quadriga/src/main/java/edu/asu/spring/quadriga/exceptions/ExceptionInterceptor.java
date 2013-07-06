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
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);
	 @AfterThrowing(pointcut="execution(* *.*(..))", throwing="t")
	  public void toRuntimeException(Throwable t)  {
		 logger.info("In Exeception Interpreter");
	    /*if (t instanceof QuadrigaAcessException) {
	      throw (QuadrigaAcessException) t;
		}
	    else if(t instanceof QuadrigaStorageException)
	    {
	    	 throw (QuadrigaStorageException) t;
	    }
	    else if(t instanceof RestException)
	    {
	    	throw (RestException) t;
	    }
	    else {
	      QuadrigaException se = new QuadrigaException(t.getMessage());
	      se.setStackTrace(t.getStackTrace());				
	      throw se;
	    }*/
	  }

}
