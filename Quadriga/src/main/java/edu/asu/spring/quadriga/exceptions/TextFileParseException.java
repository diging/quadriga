package edu.asu.spring.quadriga.exceptions;

/**
 * Custom Exception for Text File Parsing errors
 * 
 * @author Nischal Samji
 * 
 *
 */
public class TextFileParseException extends Exception{
    private static final long serialVersionUID = 8523464165493041575L;

    public TextFileParseException() {
        super();
    }

    public TextFileParseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public TextFileParseException(Throwable arg0) {
        super(arg0);
    }

    public TextFileParseException(String message) {
        super(message);
    }
}
