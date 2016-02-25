package edu.asu.quadriga.validator;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.service.textfile.ITextFileService;
import edu.asu.spring.quadriga.service.textfile.impl.TextFileService;
import edu.asu.spring.quadriga.validator.AddTextValidator;
import junit.framework.Assert;

public class TextFileValidatorTest {
    
    @Mock
    private ITextFileService tfService;
    @InjectMocks
    private AddTextValidator txtValidator;
    
    private ITextFile properTxtFile;
    private ITextFile improperTxtFile;
    private ITextFile emptyTxtFile;
    
    @Before
    public void init(){
        tfService = Mockito.mock(ITextFileService.class);
        MockitoAnnotations.initMocks(this);
        
        properTxtFile = new TextFile();
        properTxtFile.setFileContent("Dummy File Content. This is Dummy");
        properTxtFile.setFileName("testfile.txt");
        properTxtFile.setProjectId(UUID.randomUUID().toString());
        properTxtFile.setRefId(UUID.randomUUID().toString());
        properTxtFile.setWorkspaceId(UUID.randomUUID().toString());
        
        improperTxtFile = new TextFile();
        improperTxtFile.setFileContent("");
        improperTxtFile.setFileName(".testtext");
        improperTxtFile.setProjectId(UUID.randomUUID().toString());
        improperTxtFile.setRefId(UUID.randomUUID().toString());
        improperTxtFile.setWorkspaceId(UUID.randomUUID().toString());
        
        emptyTxtFile = new TextFile();
        emptyTxtFile.setFileContent("");
        emptyTxtFile.setFileName("");
        emptyTxtFile.setWorkspaceId("");
        emptyTxtFile.setProjectId("");
        emptyTxtFile.setRefId("");
        
    }
    
        @Test
        public void testProperTextFile(){
            Errors errors =  new BindException(properTxtFile, "pTxtFile");
            ValidationUtils.invokeValidator(txtValidator, properTxtFile, errors);
            Assert.assertFalse(errors.hasErrors());
            Assert.assertNull(errors.getFieldError("fileName"));
            Assert.assertNull(errors.getFieldError("fileContent"));
        }
        
        @Test
        public void testImproperTextFile(){
            Errors errors =  new BindException(improperTxtFile, "ipTxtFile");
            ValidationUtils.invokeValidator(txtValidator, improperTxtFile, errors);
            Assert.assertTrue(errors.hasErrors());
            Assert.assertEquals(errors.getFieldError("fileName"),"filename.proper");
            Assert.assertEquals(errors.getFieldError("fileContent"),"filecontent.required");
        }
        
        @Test
        public void testEmptyTextFile(){
            Errors errors =  new BindException(emptyTxtFile, "eTxtFile");
            ValidationUtils.invokeValidator(txtValidator, emptyTxtFile, errors);
            Assert.assertTrue(errors.hasErrors());
            Assert.assertEquals(errors.getFieldError("fileName"),"filename.required");
            Assert.assertEquals(errors.getFieldError("fileContent"),"filecontent.required");
        }
}
