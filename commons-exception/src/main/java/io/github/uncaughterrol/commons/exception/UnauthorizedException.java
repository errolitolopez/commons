package io.github.uncaughterrol.commons.exception;

/**
 * Thrown when a caller is not authenticated.
 *
 * <p>Maps to HTTP 401 Unauthorized. Use this when the caller has not provided
 * valid credentials or their session has expired.
 *
 * <pre>{@code
 * throw new UnauthorizedException();
 * }</pre>
 */
public class UnauthorizedException extends ApiException {

    private static final String TITLE = "Unauthorized";
    private static final String DEFAULT_DETAIL = "Authentication is required to access this resource";

    /**
     * Constructs an {@code UnauthorizedException} with a default detail message.
     */
    public UnauthorizedException() {
        super(TITLE, DEFAULT_DETAIL);
    }

    /**
     * Constructs an {@code UnauthorizedException} with a custom detail message.
     *
     * @param detail a human-readable explanation of the authentication failure
     */
    public UnauthorizedException(String detail) {
        super(TITLE, detail);
    }
}