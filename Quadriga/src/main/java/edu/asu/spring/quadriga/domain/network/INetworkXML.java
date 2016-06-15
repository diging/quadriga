package edu.asu.spring.quadriga.domain.network;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;

/**
 * @author Nischal Samji
 * 
 *         Interface for the Domain Object to read NetworkXML Strings. Stores a
 *         Textfile Object if the String contains a text field.
 *
 */
public interface INetworkXML {

    public String getNetworkXMLString();

    public void setNetworkXMLString(String networkXML);

    public ITextFile getTextFile();

    public void setTextFile(ITextFile txtFile);

}
