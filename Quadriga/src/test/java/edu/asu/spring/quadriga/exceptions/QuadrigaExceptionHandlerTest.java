package edu.asu.spring.quadriga.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class QuadrigaExceptionHandlerTest {

    private QuadrigaExceptionHandler exceptionHandler;
    private Exception ex;

    @Before
    public void setUp() {
        exceptionHandler = new QuadrigaExceptionHandler();
        ex = new Exception();
    }

    @Test
    public void handleNotImplementedExTest() {
        ModelAndView view = exceptionHandler.handleNotImplementedEx(ex);

        assertEquals("auth/notImplemented", view.getViewName());
        assertEquals(ex.getClass().getName(), view.getModelMap().get("ex_name"));
        assertEquals(ex.getMessage(), view.getModelMap().get("ex_message"));
    }
}
