package io.github.uncaughterrol.commons.exception;

/**
 * Thrown when a caller is not authorized to perform an action.
 *
 * <p>Maps to HTTP 403 Forbidden. Use this when the caller is authenticated
 * but lacks the required permissions.
 *
 * <pre>{@code
 * throw new ForbiddenException("You do not have permission to delete this resource");
 * }</pre>
 */
public class ForbiddenException extends ApiException {

    private static final String TITLE = "Forbidden";
    private static final String DEFAULT_DETAIL = "You do not have permission to perform this action";

    /**
     * Constructs a {@code ForbiddenException} with a default detail message.
     */
    public ForbiddenException() {
        super(TITLE, DEFAULT_DETAIL);
    }

    /**
     * Constructs a {@code ForbiddenException} with a custom detail message.
     *
     * @param detail a human-readable explanation of why the action is forbidden
     */
    public ForbiddenException(String detail) {
        super(TITLE, detail);
    }
}