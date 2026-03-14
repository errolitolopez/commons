package io.github.uncaughterrol.commons.exception;

/**
 * Thrown when an unexpected internal server error occurs.
 *
 * <p>Maps to HTTP 500 Internal Server Error. Use this as a last resort when no
 * other exception type is appropriate. Supports wrapping an underlying cause.
 *
 * <pre>{@code
 * try {
 *     // some operation
 * } catch (Exception e) {
 *     throw new InternalServerException(e);
 * }
 * }</pre>
 */
public class InternalServerException extends ApiException {

    private static final String TITLE = "Internal Server Error";
    private static final String DEFAULT_DETAIL = "An unexpected error occurred, please try again later";

    /**
     * Constructs an {@code InternalServerException} with a default detail message.
     */
    public InternalServerException() {
        super(TITLE, DEFAULT_DETAIL);
    }

    /**
     * Constructs an {@code InternalServerException} with a custom detail message.
     *
     * @param detail a human-readable explanation of the error
     */
    public InternalServerException(String detail) {
        super(TITLE, detail);
    }

    /**
     * Constructs an {@code InternalServerException} wrapping an underlying cause.
     *
     * @param cause the underlying exception that triggered this error; may not be {@code null}
     */
    public InternalServerException(Throwable cause) {
        super(TITLE, DEFAULT_DETAIL, cause);
    }

    /**
     * Constructs an {@code InternalServerException} with a custom detail message and underlying cause.
     *
     * @param detail a human-readable explanation of the error
     * @param cause  the underlying exception that triggered this error
     */
    public InternalServerException(String detail, Throwable cause) {
        super(TITLE, detail, cause);
    }
}