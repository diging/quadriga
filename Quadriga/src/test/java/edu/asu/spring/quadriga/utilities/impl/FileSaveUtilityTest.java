package edu.asu.spring.quadriga.utilities.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;
import junit.framework.Assert;

public class FileSaveUtilityTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Autowired
    @Qualifier("txtfileSaveUtil")
    @Mock
    private IFileSaveUtility fileSaveUtil;
    
    @Before
    public void init() throws Exception{
    	
    	fileSaveUtil = Mockito.mock(FileSaveUtility.class);
        File tempFile = tempFolder.newFile("test.txt");
        FileUtils.fileWrite(tempFile.getAbsolutePath(), "testdata");
        
    }
    
    @Test
    public void successFileWrite() throws FileNotFoundException, FileStorageException, IOException{
        Assert.assertEquals(true,fileSaveUtil.saveFiletoDir(tempFolder.getRoot().getAbsolutePath(),"zapak", "chillar".getBytes()));
    }
    
    @Test
    public void testFileRetrieval() throws FileStorageException{
    	System.out.println(tempFolder.getRoot().getAbsolutePath().toString());
    	Assert.assertEquals("testdata", fileSaveUtil.readFileContent("test.txt", tempFolder.getRoot().getAbsolutePath().toString()));
    	
    }
}
