package io.github.uncaughterrol.commons.exception;

/**
 * Thrown when a request exceeds the allowed rate limit.
 *
 * <p>Maps to HTTP 429 Too Many Requests.
 *
 * <pre>{@code
 * throw new RateLimitException();
 * }</pre>
 */
public class RateLimitException extends ApiException {

    private static final String TITLE = "Too Many Requests";
    private static final String DEFAULT_DETAIL = "You have exceeded the allowed request rate limit";

    /**
     * Constructs a {@code RateLimitException} with a default detail message.
     */
    public RateLimitException() {
        super(TITLE, DEFAULT_DETAIL);
    }

    /**
     * Constructs a {@code RateLimitException} with a custom detail message.
     *
     * @param detail a human-readable explanation of the rate limit violation
     */
    public RateLimitException(String detail) {
        super(TITLE, detail);
    }
}