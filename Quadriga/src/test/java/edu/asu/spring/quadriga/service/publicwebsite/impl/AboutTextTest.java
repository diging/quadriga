    package edu.asu.spring.quadriga.service.publicwebsite.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.dao.impl.publicwebsite.AboutTextDAO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.impl.publicwebsite.AboutTextManager;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;



public class AboutTextTest {

    @InjectMocks
    private IAboutTextManager mockedAboutTextManager = Mockito
            .mock(IAboutTextManager.class);

    @InjectMocks
    private AboutTextDAO aboutTextDAO;

   
    @Before
    public void saveAbout() throws QuadrigaStorageException {
        MockitoAnnotations.initMocks(this);
        mockedAboutTextManager.saveAbout("1st","Title1","Description1");
        
    }

    @Test
    public void testMockCreation() throws QuadrigaStorageException{

        assertNotNull(aboutTextDAO);
        assertNotNull(mockedAboutTextManager);

    }

    @Test
    public void testSaveAboutCall() throws QuadrigaStorageException{
        Mockito.verify(mockedAboutTextManager).saveAbout("1st","Title1","Description1");
    }
    
   
}
