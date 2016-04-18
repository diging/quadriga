package edu.asu.spring.quadriga.utilities;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;

public interface ITextXMLParser {

    public ITextFile parseTextXML(String xml) throws QuadrigaException;
}
