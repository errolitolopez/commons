package io.github.uncaughterrol.commons.exception;

/**
 * Thrown when a resource already exists and cannot be created again.
 *
 * <p>Maps to HTTP 409 Conflict.
 *
 * <pre>{@code
 * throw new ResourceAlreadyExistsException("User", "email", "john@example.com");
 * // detail: "User with email john@example.com already exists"
 * }</pre>
 */
public class ResourceAlreadyExistsException extends ApiException {

    private static final String TITLE = "Resource Already Exists";

    /**
     * Constructs a {@code ResourceAlreadyExistsException} for a resource with a conflicting field.
     *
     * @param resource the name of the resource type (e.g. {@code "User"})
     * @param field    the name of the conflicting field (e.g. {@code "email"})
     * @param value    the conflicting value (e.g. {@code "john@example.com"})
     */
    public ResourceAlreadyExistsException(String resource, String field, String value) {
        super(TITLE, resource + " with " + field + " " + value + " already exists");
    }

    /**
     * Constructs a {@code ResourceAlreadyExistsException} with a custom detail message.
     *
     * @param detail a human-readable explanation of the conflict
     */
    public ResourceAlreadyExistsException(String detail) {
        super(TITLE, detail);
    }
}