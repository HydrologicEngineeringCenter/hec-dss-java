package apifinal;

/**
 * Runtime exception for DSS operations.
 */
public class DssException extends RuntimeException {
    public DssException(String message) {
        super(message);
    }

    public DssException(String message, Throwable cause) {
        super(message, cause);
    }
}
