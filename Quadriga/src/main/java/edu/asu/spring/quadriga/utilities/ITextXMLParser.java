package edu.asu.spring.quadriga.utilities;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.TextFileParseException;

/**
 * Interface for TextFile XML Parsing Methods
 * @author Nischal Samji
 *
 */
public interface ITextXMLParser {

    /**
     * @param xml
     *          XML content from the rest endpoint as string
     * @param wsId
     *          Workspace id for the Textfile to be stored.
     * @param projId
     *          Project id for the Textfile to be stored.
     * @return
     *          Returns a TextFile Object that is formed from the XML.
     * @throws TextFileParseException
     */
    public ITextFile parseTextXML(String xml, String wsId, String projId) throws TextFileParseException;
}
