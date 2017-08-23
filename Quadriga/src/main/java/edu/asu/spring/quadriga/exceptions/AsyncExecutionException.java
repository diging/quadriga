package edu.asu.spring.quadriga.exceptions;

public class AsyncExecutionException extends Exception {

    private static final long serialVersionUID = -957396267621831476L;

    public AsyncExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AsyncExecutionException(String message) {
        super(message);
    }

    public AsyncExecutionException(Throwable cause) {
        super(cause);
    }

}
