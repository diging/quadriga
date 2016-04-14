package edu.asu.spring.quadriga.domain.impl.networks;

import edu.asu.spring.quadriga.domain.network.INetworkXML;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;

public class NetworkXML implements INetworkXML {

    private String NetworkXMLString;
    private ITextFile textFile;

    public String getNetworkXMLString() {
        return NetworkXMLString;
    }

    public void setNetworkXMLString(String networkXMLString) {
        NetworkXMLString = networkXMLString;
    }

    public ITextFile getTextFile() {
        return textFile;
    }

    public void setTextFile(ITextFile textFile) {
        this.textFile = textFile;
    }

}
