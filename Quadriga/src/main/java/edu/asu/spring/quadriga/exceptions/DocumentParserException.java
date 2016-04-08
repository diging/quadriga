package edu.asu.spring.quadriga.exceptions;

public class DocumentParserException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3092148117483763798L;

    public DocumentParserException() {
        super();
    }

    public DocumentParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentParserException(String message) {
        super(message);
    }

    public DocumentParserException(Throwable cause) {
        super(cause);
    }

}
