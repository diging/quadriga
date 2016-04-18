package edu.asu.spring.quadriga.utilities;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.TextFileParseException;

public interface ITextXMLParser {

    public ITextFile parseTextXML(String xml, String wsId, String projId) throws TextFileParseException;
}
