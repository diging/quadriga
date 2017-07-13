package edu.asu.spring.quadriga.domain.network.impl;

import edu.asu.spring.quadriga.domain.network.INetworkXML;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;

public class NetworkXML implements INetworkXML {

    private String networkXMLString;
    private ITextFile textFile;

    public String getNetworkXMLString() {
        return networkXMLString;
    }

    public void setNetworkXMLString(String networkXMLString) {
        this.networkXMLString = networkXMLString;
    }

    public ITextFile getTextFile() {
        return textFile;
    }

    public void setTextFile(ITextFile textFile) {
        this.textFile = textFile;
    }

}
