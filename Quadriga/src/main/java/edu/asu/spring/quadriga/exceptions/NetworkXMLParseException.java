package edu.asu.spring.quadriga.exceptions;

/**
 * Exception to handle exceptions thrown while parsing the network xml.
 * 
 * @author skollur1
 *
 */
public class NetworkXMLParseException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8521964165493041575L;

    public NetworkXMLParseException() {
        super();
    }

    public NetworkXMLParseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public NetworkXMLParseException(Throwable arg0) {
        super(arg0);
    }

    public NetworkXMLParseException(String message) {
        super(message);
    }

}
