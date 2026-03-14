package io.github.uncaughterrol.commons.exception;

import java.util.Collection;

/**
 * Factory class for creating common {@link ApiException} instances.
 *
 * <p>Provides static factory methods as a cleaner alternative to using {@code new} directly.
 * Centralizes exception creation and makes call sites more readable.
 *
 * <pre>{@code
 * // instead of:
 * throw new ResourceNotFoundException("User", 42);
 *
 * // use:
 * throw ExceptionFactory.notFound("User", 42);
 * }</pre>
 */
public final class ExceptionFactory {

    /**
     * Private constructor to prevent instantiation of this factory class.
     */
    private ExceptionFactory() {
    }

    // -------------------------------------------------------------------------
    // 400 Bad Request
    // -------------------------------------------------------------------------

    /**
     * Creates a {@link ValidationException} with a list of invalid parameters.
     *
     * @param invalidParams the collection of invalid parameters; must not be {@code null}
     * @return a new {@link ValidationException}
     */
    public static ValidationException validationFailed(Collection<InvalidParam> invalidParams) {
        return new ValidationException(invalidParams);
    }

    /**
     * Creates a {@link ValidationException} with a custom detail message and invalid parameters.
     *
     * @param detail        a human-readable explanation of the validation failure
     * @param invalidParams the collection of invalid parameters; must not be {@code null}
     * @return a new {@link ValidationException}
     */
    public static ValidationException validationFailed(String detail, Collection<InvalidParam> invalidParams) {
        return new ValidationException(detail, invalidParams);
    }

    // -------------------------------------------------------------------------
    // 401 Unauthorized
    // -------------------------------------------------------------------------

    /**
     * Creates an {@link UnauthorizedException} with a default detail message.
     *
     * @return a new {@link UnauthorizedException}
     */
    public static UnauthorizedException unauthorized() {
        return new UnauthorizedException();
    }

    /**
     * Creates an {@link UnauthorizedException} with a custom detail message.
     *
     * @param detail a human-readable explanation of the authentication failure
     * @return a new {@link UnauthorizedException}
     */
    public static UnauthorizedException unauthorized(String detail) {
        return new UnauthorizedException(detail);
    }

    // -------------------------------------------------------------------------
    // 403 Forbidden
    // -------------------------------------------------------------------------

    /**
     * Creates a {@link ForbiddenException} with a default detail message.
     *
     * @return a new {@link ForbiddenException}
     */
    public static ForbiddenException forbidden() {
        return new ForbiddenException();
    }

    /**
     * Creates a {@link ForbiddenException} with a custom detail message.
     *
     * @param detail a human-readable explanation of why the action is forbidden
     * @return a new {@link ForbiddenException}
     */
    public static ForbiddenException forbidden(String detail) {
        return new ForbiddenException(detail);
    }

    // -------------------------------------------------------------------------
    // 404 Not Found
    // -------------------------------------------------------------------------

    /**
     * Creates a {@link ResourceNotFoundException} for a resource identified by a numeric ID.
     *
     * @param resource the name of the resource type (e.g. {@code "User"}, {@code "Order"})
     * @param id       the numeric identifier of the resource that was not found
     * @return a new {@link ResourceNotFoundException}
     */
    public static ResourceNotFoundException notFound(String resource, long id) {
        return new ResourceNotFoundException(resource, id);
    }

    /**
     * Creates a {@link ResourceNotFoundException} for a resource identified by a string key.
     *
     * @param resource the name of the resource type (e.g. {@code "User"}, {@code "Order"})
     * @param key      the string identifier of the resource that was not found
     * @return a new {@link ResourceNotFoundException}
     */
    public static ResourceNotFoundException notFound(String resource, String key) {
        return new ResourceNotFoundException(resource, key);
    }

    /**
     * Creates a {@link ResourceNotFoundException} with a custom detail message.
     *
     * @param detail a human-readable explanation of what was not found
     * @return a new {@link ResourceNotFoundException}
     */
    public static ResourceNotFoundException notFound(String detail) {
        return new ResourceNotFoundException(detail);
    }

    // -------------------------------------------------------------------------
    // 409 Conflict
    // -------------------------------------------------------------------------

    /**
     * Creates a {@link ResourceAlreadyExistsException} for a resource with a conflicting field.
     *
     * @param resource the name of the resource type (e.g. {@code "User"})
     * @param field    the name of the conflicting field (e.g. {@code "email"})
     * @param value    the conflicting value (e.g. {@code "john@example.com"})
     * @return a new {@link ResourceAlreadyExistsException}
     */
    public static ResourceAlreadyExistsException alreadyExists(String resource, String field, String value) {
        return new ResourceAlreadyExistsException(resource, field, value);
    }

    /**
     * Creates a {@link ResourceAlreadyExistsException} with a custom detail message.
     *
     * @param detail a human-readable explanation of the conflict
     * @return a new {@link ResourceAlreadyExistsException}
     */
    public static ResourceAlreadyExistsException alreadyExists(String detail) {
        return new ResourceAlreadyExistsException(detail);
    }

    // -------------------------------------------------------------------------
    // 429 Too Many Requests
    // -------------------------------------------------------------------------

    /**
     * Creates a {@link RateLimitException} with a default detail message.
     *
     * @return a new {@link RateLimitException}
     */
    public static RateLimitException rateLimitExceeded() {
        return new RateLimitException();
    }

    /**
     * Creates a {@link RateLimitException} with a custom detail message.
     *
     * @param detail a human-readable explanation of the rate limit violation
     * @return a new {@link RateLimitException}
     */
    public static RateLimitException rateLimitExceeded(String detail) {
        return new RateLimitException(detail);
    }

    // -------------------------------------------------------------------------
    // 500 Internal Server Error
    // -------------------------------------------------------------------------

    /**
     * Creates an {@link InternalServerException} with a default detail message.
     *
     * @return a new {@link InternalServerException}
     */
    public static InternalServerException internal() {
        return new InternalServerException();
    }

    /**
     * Creates an {@link InternalServerException} with a custom detail message.
     *
     * @param detail a human-readable explanation of the error
     * @return a new {@link InternalServerException}
     */
    public static InternalServerException internal(String detail) {
        return new InternalServerException(detail);
    }

    /**
     * Creates an {@link InternalServerException} wrapping an underlying cause.
     *
     * @param cause the underlying exception that triggered this error
     * @return a new {@link InternalServerException}
     */
    public static InternalServerException internal(Throwable cause) {
        return new InternalServerException(cause);
    }

    /**
     * Creates an {@link InternalServerException} with a custom detail message and underlying cause.
     *
     * @param detail a human-readable explanation of the error
     * @param cause  the underlying exception that triggered this error
     * @return a new {@link InternalServerException}
     */
    public static InternalServerException internal(String detail, Throwable cause) {
        return new InternalServerException(detail, cause);
    }
}