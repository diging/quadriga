package edu.asu.spring.quadriga.web.workbench;

import static org.junit.Assert.assertEquals;

import java.security.Principal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;

public class DeleteProjectControllerTest {
    
    @InjectMocks
    private DeleteProjectController deleteProjectController;
    @Mock
    private IModifyProjectManager projectManager;
    private Principal principal;
    
    @Before
    public void setup(){
        projectManager = Mockito.mock(IModifyProjectManager.class);
        MockitoAnnotations.initMocks(this);
        principal = new Principal() {           
            @Override
            public String getName() {
                return "test";
            }
        };
        
    }

    @Test
    public void deleteProjectRequestTest() throws QuadrigaStorageException, QuadrigaAccessException{
        String mockProjectId = "testProjectId";
        RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
        ArrayList<String> projectIdList = new ArrayList<String>();
        projectIdList.add(mockProjectId);
        
        ModelAndView actualModel = deleteProjectController.deleteProjectRequest(mockProjectId, redirectAttributes,principal);
        Mockito.verify(projectManager, Mockito.atLeastOnce()).deleteProjectRequest(projectIdList,principal);

        String expectedViewName = "redirect:/auth/workbench";
        assertEquals(expectedViewName,actualModel.getViewName());
    }    
}
