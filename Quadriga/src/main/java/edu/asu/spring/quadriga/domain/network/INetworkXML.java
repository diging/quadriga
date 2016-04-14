package edu.asu.spring.quadriga.domain.network;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;

public interface INetworkXML {
    
    public String getNetworkXMLString();
    public void setNetworkXMLString(String networkXML);
    public ITextFile getTextFile();
    public void setTextFile(ITextFile txtFile);

}
