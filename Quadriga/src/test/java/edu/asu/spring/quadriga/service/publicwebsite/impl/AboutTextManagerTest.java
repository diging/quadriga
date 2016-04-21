package edu.asu.spring.quadriga.service.publicwebsite.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.impl.publicwebsite.AboutTextDAO;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.impl.publicwebsite.AboutTextManager;

public class AboutTextManagerTest {

    @InjectMocks
    private AboutTextManager aboutTextManager;

    @Mock
    private AboutTextDAO mockedAboutTextDAO;

    private ArgumentCaptor<AboutTextDTO> argument;
    private AboutTextDTO dto;

    @Before
    public void saveAbout() throws QuadrigaStorageException {
        MockitoAnnotations.initMocks(this);
        aboutTextManager.saveAbout("1st", "Title", "Description");
        aboutTextManager.saveAbout("1st", "NewTitle", "NewDescription");

    }

    @Test
    public void testSaveAboutCall() throws QuadrigaStorageException {
        dto = new AboutTextDTO();
        dto.setProjectId("1st");
        dto.setTitle("Title");
        dto.setDescription("Description");

        argument = ArgumentCaptor.forClass(AboutTextDTO.class);
        Mockito.verify(mockedAboutTextDAO, Mockito.times(2)).saveOrUpdateDTO(argument.capture());
        assertEquals(dto.getProjectId(), argument.getAllValues().get(0).getProjectId());
        assertEquals(dto.getTitle(), argument.getAllValues().get(0).getTitle());
        assertEquals(dto.getDescription(), argument.getAllValues().get(0).getDescription());

    }

    @Test
    public void testSaveAboutReCall() {
        dto = new AboutTextDTO();
        dto.setProjectId("1st");
        dto.setTitle("NewTitle");
        dto.setDescription("NewDescription");

        argument = ArgumentCaptor.forClass(AboutTextDTO.class);
        Mockito.verify(mockedAboutTextDAO, Mockito.times(2)).saveOrUpdateDTO(argument.capture());
        assertEquals(dto.getProjectId(), argument.getAllValues().get(1).getProjectId());
        assertEquals(dto.getTitle(), argument.getAllValues().get(1).getTitle());
        assertEquals(dto.getDescription(), argument.getAllValues().get(1).getDescription());

    }

}
