package edu.asu.spring.quadriga.service.publicwebsite.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import edu.asu.spring.quadriga.dao.publicwebsite.IAboutTextDAO;
import edu.asu.spring.quadriga.dao.publicwebsite.impl.AboutTextDAO;
import edu.asu.spring.quadriga.domain.settings.IAboutText;
import edu.asu.spring.quadriga.domain.settings.impl.AboutText;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.publicwebsite.mapper.IAboutTextMapper;
import edu.asu.spring.quadriga.service.publicwebsite.mapper.impl.AboutTextMapper;

/**
 * @author Nischal Samji
 * 
 *         Test class for the AboutText Manager
 *
 */
public class AboutTextManagerTest {

    @InjectMocks
    private AboutTextManager aboutTextManager;

    @Spy
    private IAboutTextMapper abtTxtMapper = new AboutTextMapper();

    @Mock
    private IAboutTextDAO mockedAboutTextDAO = Mockito.mock(AboutTextDAO.class);

    private ArgumentCaptor<AboutTextDTO> argument;

    private IAboutText saveText;

    private IAboutText updateText;

    private IAboutText nullText;

    @Before
    public void setUp() throws QuadrigaStorageException {
        MockitoAnnotations.initMocks(this);

        // abtTxtMapper = new AboutTextMapper();

        saveText = new AboutText();
        saveText.setDescription("Save Description");
        saveText.setTitle("SaveTitle");
        saveText.setProjectId("PROJabscde");

        updateText = new AboutText();
        updateText.setId("testId1234");
        updateText.setDescription("Update Description");
        updateText.setTitle("UpdateTitle");
        updateText.setProjectId("PROJxyzwqr");

        nullText = new AboutText();

    }

    @Test
    public void testSaveAboutNewDTO() throws QuadrigaStorageException {

        Mockito.when(mockedAboutTextDAO.generateUniqueID()).thenReturn("UniqueId1");
        aboutTextManager.saveAbout("PROJabscde", saveText);

        Mockito.verify(mockedAboutTextDAO).generateUniqueID();

        argument = ArgumentCaptor.forClass(AboutTextDTO.class);

        Mockito.verify(mockedAboutTextDAO).saveOrUpdateDTO(argument.capture());
        assertEquals("UniqueId1", argument.getValue().getId());
        assertEquals("PROJabscde", argument.getValue().getProjectId());
        assertEquals("SaveTitle", argument.getValue().getTitle());
        assertEquals("Save Description", argument.getValue().getDescription());

    }

    @Test
    public void testSaveAboutUpdateDTO() throws QuadrigaStorageException {

        AboutTextDTO dto = new AboutTextDTO();
        dto.setId("UniqueId2");
        dto.setProjectId("1st");
        dto.setTitle("UpdateTitle");
        dto.setDescription("Update Description");
        Mockito.when(mockedAboutTextDAO.getDTOByProjectId("PROJxyzwqr")).thenReturn(dto);

        aboutTextManager.saveAbout("PROJxyzwqr", updateText);

        argument = ArgumentCaptor.forClass(AboutTextDTO.class);
        Mockito.verify(mockedAboutTextDAO, Mockito.times(1)).saveOrUpdateDTO(argument.capture());
        assertEquals("testId1234", argument.getValue().getId());
        assertEquals("PROJxyzwqr", argument.getValue().getProjectId());
        assertEquals("UpdateTitle", argument.getValue().getTitle());
        assertEquals("Update Description", argument.getValue().getDescription());

    }

    @Test
    public void testGetAboutTextByProjectId() throws QuadrigaStorageException {
        AboutTextDTO dto = new AboutTextDTO();
        dto.setProjectId("test1");
        dto.setTitle("TestTitle");
        dto.setDescription("Newestdescription");
        dto.setId("Test1");

        Mockito.when(mockedAboutTextDAO.getDTOByProjectId("test1")).thenReturn(dto);

        IAboutText result = aboutTextManager.getAboutTextByProjectId("test1");

        Mockito.verify(mockedAboutTextDAO, Mockito.times(1)).getDTOByProjectId("test1");

        assertNotNull(result);
        assertEquals("test1", result.getProjectId());
        assertEquals("TestTitle", result.getTitle());
        assertEquals("Newestdescription", result.getDescription());
        assertEquals("Test1", result.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyProject() throws QuadrigaStorageException {

        Mockito.when(mockedAboutTextDAO.getDTOByProjectId("test24")).thenReturn(null);
        aboutTextManager.getAboutTextByProjectId("test24");

    }

}
