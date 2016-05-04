package edu.asu.spring.quadriga.web;

import static org.junit.Assert.assertEquals;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


public class HomeControllerTest {

    @Mock
    private Properties messages;

    @Mock
    private IRetrieveProjectManager projectManager = Mockito.mock(IRetrieveProjectManager.class);
    
    @InjectMocks
    private HomeController homeController;
    private Model model;
    private Principal principal;

    /**
     * This method sets up the needed objects for testing the {@link LoginController}
     *
     */
    @Before
    public void setUp() throws Exception {
        messages = Mockito.mock(Properties.class);
        MockitoAnnotations.initMocks(this);

        model =  new BindingAwareModelMap();
        principal = new Principal() {
            @Override
            public String getName() {
                return "jdoe";
            }
        };

        Mockito.when(messages.get(Mockito.anyString())).thenReturn("mocked property");
        // mock project manager
        Mockito.when(projectManager.getRecentProjectList(principal.getName())).thenReturn(new ArrayList<>());
    }

    @Test
    public void homeTest() throws QuadrigaStorageException {

        assertEquals("auth/home", homeController.home(new Locale("us"), model, principal));
        assertEquals(principal.getName(), ((ModelMap)model).get("username"));

    }
}
