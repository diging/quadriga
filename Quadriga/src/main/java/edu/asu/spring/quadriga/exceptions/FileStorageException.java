package edu.asu.spring.quadriga.exceptions;

/**
 * Exception thrown when saving a file to file system fails.
 * 
 * @author Nischal Samji
 * 
 *
 */
public class FileStorageException extends Exception {
    private static final long serialVersionUID = -3850218568287768164L;

    public FileStorageException() {
        super();
    }

    /**
     * @param customMsg
     *            Custom message to be thrown when exception occurs
     */
    public FileStorageException(String customMsg) {
        super(customMsg);
    }

    public FileStorageException(Exception e) {
        super(e);
    }

    public FileStorageException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
