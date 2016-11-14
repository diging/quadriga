package edu.asu.spring.quadriga.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class handles exceptions thrown in Controller classes.
 * 
 * @author Julia Damerow
 *
 */
@ControllerAdvice
public class QuadrigaExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(QuadrigaExceptionHandler.class);

    /**
     * For now this method handles all exceptions thrown in Controller classes.
     * Eventually this method can be replaced by methods that handle individual
     * exceptions.
     * 
     * @param ex
     *            The exception thrown in a controller.
     * @return Information about the exception page.
     */
    @ExceptionHandler(QuadrigaException.class)
    public ModelAndView handleNotImplementedEx(Exception ex) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/notImplemented");
        modelAndView.addObject("ex_name", ex.getClass().getName());
        modelAndView.addObject("ex_message", ex.getMessage());
        logger.error(ex.getMessage(), ex);
        return modelAndView;
    }

    @ExceptionHandler(QuadrigaStorageException.class)
    public ModelAndView handleSQLException(QuadrigaStorageException ex) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/storageissue");
        modelAndView.addObject("ex_name", ex.getClass().getName());
        modelAndView.addObject("ex_message", ex.getMessage());
        logger.error(ex.getMessage(), ex);
        return modelAndView;
    }

    @ExceptionHandler(value = { QuadrigaAccessException.class })
    public ModelAndView handleUserAccessException(QuadrigaAccessException ex) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/accessissue");
        modelAndView.addObject("ex_name", ex.getClass().getName());
        modelAndView.addObject("ex_message", ex.getMessage());
        logger.error(ex.getMessage(), ex);
        return modelAndView;
    }

    @ExceptionHandler(value = { Quadriga404Exception.class })
    public ModelAndView handle404Exception(Quadriga404Exception ex) {
        ModelAndView modelAndView = new ModelAndView("auth/404");
        modelAndView.addObject("ex_msg", ex.getMessage());
        logger.error(ex.getMessage(), ex);
        return modelAndView;
    }

    @ExceptionHandler(value = { IllegalObjectException.class })
    public ModelAndView handleInvalidCastException(IllegalObjectException ice) {
        ModelAndView modelAndView = new ModelAndView("auth/404");
        modelAndView.addObject("ex_msg", ice.getMessage());
        logger.error(ice.getMessage(), ice);
        return modelAndView;
    }

}
