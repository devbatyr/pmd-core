package kz.shakenov.pmdcore.exception;

/**
 * Thrown when PMD analysis fails due to execution or I/O errors.
 */
public class PmdExecutionException extends RuntimeException {

    /**
     * Constructs a new PmdExecutionException with the specified detail message and cause.
     *
     * @param message the detail message explaining the error
     * @param cause   the underlying cause of the exception
     */
    public PmdExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}