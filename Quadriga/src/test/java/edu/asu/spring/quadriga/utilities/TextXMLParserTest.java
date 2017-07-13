package edu.asu.spring.quadriga.utilities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.factory.workspace.ITextFileFactory;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.domain.workspace.impl.TextFile;
import edu.asu.spring.quadriga.exceptions.TextFileParseException;
import edu.asu.spring.quadriga.utilities.ITextXMLParser;
import edu.asu.spring.quadriga.utilities.impl.TextXMLParser;

/**
 * Unit Test for Text XML Parser Functions
 * 
 * @author Nischal Samji
 *
 */
public class TextXMLParserTest {

    @InjectMocks
    private ITextXMLParser textXMLParser = new TextXMLParser();

    @Mock
    private ITextFileFactory txtFileFactory;

    @Rule
    public ExpectedException textFileException = ExpectedException.none();

    private String wellFormedXML;
    private String malformedXML;
    private String invalidXML;
    private String missingXML;
    private String projid;
    private String wsid;

    @Before
    public void init() throws TextFileParseException {
        MockitoAnnotations.initMocks(this);

        wellFormedXML = new String(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><textfile><text>This is the actual textfile content</text><handle>thishandle</handle><file_name>newfile</file_name><accessibility>public</accessibility></textfile>");
        malformedXML = new String(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><textfile>This is the actual textfile content</text><handle>thishandle</handle><file_name>newfile</file_name></textfile>");
        invalidXML = new String(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><textfile><text>This is the actual textfile content</text><handle>thishandle</handle><file_name></file_name><accessibility>public</accessibility></textfile>");
        missingXML = new String(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><textfile><handle>thishandle</handle><file_name>newfile</file_name></textfile>");

        String projid = "PROJ_XYZ_WERT";
        String wsid = "WS_123_456";
        
        ITextFile properText = new TextFile();

        Mockito.when(txtFileFactory.createTextFileObject()).thenReturn(properText);

    }

    @Test
    public void testWellFormedXML() throws TextFileParseException {
        ITextFile txtFile = textXMLParser.parseTextXML(wellFormedXML, wsid, projid);
        Assert.assertEquals(wsid, txtFile.getWorkspaceId());
        Assert.assertEquals(projid, txtFile.getProjectId());
        Assert.assertEquals("thishandle", txtFile.getRefId());
        Assert.assertEquals("This is the actual textfile content", txtFile.getFileContent());
        Assert.assertEquals("newfile", txtFile.getFileName());
        Assert.assertEquals(ETextAccessibility.PUBLIC, txtFile.getAccessibility());
    }

    @Test
    public void testMalformedXML() throws Exception {
        textFileException.expect(TextFileParseException.class);
        textXMLParser.parseTextXML(malformedXML, wsid, projid);
    }

    @Test
    public void testInvalidXML() throws Exception {
        textFileException.expect(TextFileParseException.class);
        textFileException.expectMessage("Filename cannot be empty");
        textXMLParser.parseTextXML(invalidXML, wsid, projid);
    }
    
    @Test
    public void testMissingXML() throws Exception {
        textFileException.expect(TextFileParseException.class);
        textFileException.expectMessage("File content must be specified in the input XML");
        textXMLParser.parseTextXML(missingXML, wsid, projid);
    }
}