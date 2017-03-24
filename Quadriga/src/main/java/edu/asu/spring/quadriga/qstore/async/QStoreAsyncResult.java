package edu.asu.spring.quadriga.qstore.async;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
public class QStoreAsyncResult {

    private String queryStatus;

    private String pollurl;

    private String result;

    public String getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }

    public String getPollurl() {
        return pollurl;
    }

    public void setPollurl(String pollurl) {
        this.pollurl = pollurl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "QStoreAsyncResult [queryStatus=" + queryStatus + ", pollurl=" + pollurl + ", result=" + result + "]";
    }

}
