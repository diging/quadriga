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

import edu.asu.spring.quadriga.dao.impl.publicwebsite.AboutTextDAO;
import edu.asu.spring.quadriga.dao.publicwebsite.IAboutTextDAO;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.impl.publicwebsite.AboutTextManager;

public class AboutTextManagerTest {

    @InjectMocks
    private AboutTextManager aboutTextManager;

    @Mock
    private IAboutTextDAO mockedAboutTextDAO = Mockito.mock(AboutTextDAO.class);

    private ArgumentCaptor<AboutTextDTO> argument;

    @Before
    public void setUp() throws QuadrigaStorageException {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testSaveAboutNewDTO() throws QuadrigaStorageException {

        Mockito.when(mockedAboutTextDAO.generateUniqueID()).thenReturn("UniqueId1");
        aboutTextManager.saveAbout("1st", "Title", "Description");
        
        Mockito.verify(mockedAboutTextDAO).generateUniqueID();

        argument = ArgumentCaptor.forClass(AboutTextDTO.class);
        Mockito.verify(mockedAboutTextDAO).saveOrUpdateDTO(argument.capture());
        assertEquals("UniqueId1",argument.getValue().getId());
        assertEquals("1st", argument.getValue().getProjectId());
        assertEquals("Title", argument.getValue().getTitle());
        assertEquals("Description", argument.getValue().getDescription());
        
    }

    @Test
    public void testSaveAboutUpdateDTO() throws QuadrigaStorageException {
        
        aboutTextManager.saveAbout("1st", "NewTitle", "NewDescription");
        
        AboutTextDTO dto = new AboutTextDTO();
        dto.setProjectId("1st");
        dto.setTitle("NewTitle");
        dto.setDescription("NewDescription");
        Mockito.when(mockedAboutTextDAO.getDTOByProjectId("1st")).thenReturn(dto);

        
        aboutTextManager.saveAbout("1st", "NewTitle2", "NewDescription2");

        Mockito.verify(mockedAboutTextDAO, Mockito.times(1)).generateUniqueID();
        
        argument = ArgumentCaptor.forClass(AboutTextDTO.class);
        Mockito.verify(mockedAboutTextDAO, Mockito.times(2)).saveOrUpdateDTO(argument.capture());
        assertEquals("1st", argument.getValue().getProjectId());
        assertEquals("NewTitle2", argument.getValue().getTitle());
        assertEquals("NewDescription2", argument.getValue().getDescription());

    }

}
