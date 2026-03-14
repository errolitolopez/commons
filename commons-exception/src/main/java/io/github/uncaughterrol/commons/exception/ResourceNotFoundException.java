package io.github.uncaughterrol.commons.exception;

/**
 * Thrown when a requested resource could not be found.
 *
 * <p>Maps to HTTP 404 Not Found.
 *
 * <pre>{@code
 * throw new ResourceNotFoundException("User", 42);
 * // detail: "User with identifier 42 could not be found"
 * }</pre>
 */
public class ResourceNotFoundException extends ApiException {

    private static final String TITLE = "Resource Not Found";

    /**
     * Constructs a {@code ResourceNotFoundException} for a resource identified by a numeric ID.
     *
     * @param resource the name of the resource type (e.g. {@code "User"}, {@code "Order"})
     * @param id       the numeric identifier of the resource that was not found
     */
    public ResourceNotFoundException(String resource, long id) {
        super(TITLE, resource + " with identifier " + id + " could not be found");
    }

    /**
     * Constructs a {@code ResourceNotFoundException} for a resource identified by a string key.
     *
     * @param resource the name of the resource type (e.g. {@code "User"}, {@code "Order"})
     * @param key      the string identifier of the resource that was not found
     */
    public ResourceNotFoundException(String resource, String key) {
        super(TITLE, resource + " with identifier " + key + " could not be found");
    }

    /**
     * Constructs a {@code ResourceNotFoundException} with a custom detail message.
     *
     * @param detail a human-readable explanation of what was not found
     */
    public ResourceNotFoundException(String detail) {
        super(TITLE, detail);
    }
}