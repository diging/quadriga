package edu.asu.spring.quadriga.domain.network.json;

public class PublicSearchObject {
    String jsonString;
    String searchNodeLabel;
    String description;
    String unixName;
    boolean isNetworkEmpty;
    int status;

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getSearchNodeLabel() {
        return searchNodeLabel;
    }

    public void setSearchNodeLabel(String searchNodeLabel) {
        this.searchNodeLabel = searchNodeLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnixName() {
        return unixName;
    }

    public void setUnixName(String unixName) {
        this.unixName = unixName;
    }

    public boolean isNetworkEmpty() {
        return isNetworkEmpty;
    }

    public void setNetworkEmpty(boolean isNetworkEmpty) {
        this.isNetworkEmpty = isNetworkEmpty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
}
