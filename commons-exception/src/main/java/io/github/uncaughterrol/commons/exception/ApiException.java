package io.github.uncaughterrol.commons.exception;

import io.github.uncaughterrol.commons.model.InvalidParam;

import java.util.Collection;
import java.util.Collections;

/**
 * Base runtime exception for API-level errors.
 *
 * <p>Designed to carry structured error information suitable for returning
 * machine-readable error responses (e.g. RFC 9457 Problem Details for HTTP APIs).
 *
 * <p>Subclass this to define specific error types:
 * <pre>{@code
 * public class ResourceNotFoundException extends ApiException {
 *     public ResourceNotFoundException(String resource) {
 *         super("Resource Not Found", resource + " could not be found");
 *     }
 * }
 * }</pre>
 */
public class ApiException extends RuntimeException {

    /**
     * A short, human-readable summary of the error type.
     * Should not change between occurrences of the same error (e.g. "Validation Failed").
     */
    private final String title;

    /**
     * A human-readable explanation specific to this occurrence of the error.
     * (e.g. "Email address is already in use").
     */
    private final String detail;

    /**
     * Optional list of invalid parameters that caused this exception.
     * Populated when the error is the result of request validation failures.
     */
    private Collection<InvalidParam> invalidParams;

    /**
     * Constructs an {@code ApiException} with a title, detail message, and a list of
     * invalid parameters.
     *
     * <p>Use this constructor when the error is caused by one or more invalid request
     * parameters, and you want to communicate them back to the caller.
     *
     * @param title         a short summary of the error type; must not be {@code null}
     * @param detail        a human-readable explanation of this specific error; must not be {@code null}
     * @param invalidParams the collection of invalid parameters that triggered this error;
     *                      may be {@code null} or empty
     */
    public ApiException(String title, String detail, Collection<InvalidParam> invalidParams) {
        super(detail);
        this.title = title;
        this.detail = detail;
        this.invalidParams = invalidParams != null
                ? Collections.unmodifiableCollection(invalidParams)
                : Collections.emptyList();
    }

    /**
     * Constructs an {@code ApiException} with a title and detail message.
     *
     * <p>Use this constructor for general API errors that are not caused by
     * invalid request parameters.
     *
     * @param title  a short summary of the error type; must not be {@code null}
     * @param detail a human-readable explanation of this specific error; must not be {@code null}
     */
    public ApiException(String title, String detail) {
        super(detail);
        this.title = title;
        this.detail = detail;
        this.invalidParams = Collections.emptyList();
    }

    /**
     * Constructs an {@code ApiException} with a title, detail message, and an underlying cause.
     *
     * <p>Use this constructor when wrapping a lower-level exception while still providing
     * structured API error information.
     *
     * @param title  a short summary of the error type; must not be {@code null}
     * @param detail a human-readable explanation of this specific error; must not be {@code null}
     * @param cause  the underlying exception that caused this error; may be {@code null}
     */
    public ApiException(String title, String detail, Throwable cause) {
        super(detail, cause);
        this.title = title;
        this.detail = detail;
        this.invalidParams = Collections.emptyList();
    }

    /**
     * Returns the short summary of the error type.
     *
     * @return the error title; never {@code null}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the human-readable explanation of this specific error occurrence.
     *
     * @return the error detail; never {@code null}
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Returns the collection of invalid parameters associated with this error.
     *
     * @return an unmodifiable collection of {@link InvalidParam}; never {@code null},
     *         empty if no invalid parameters were provided
     */
    public Collection<InvalidParam> getInvalidParams() {
        return invalidParams;
    }

    /**
     * Replaces the collection of invalid parameters on this exception.
     *
     * <p>The provided collection is wrapped in an unmodifiable view to prevent
     * external mutation after assignment.
     *
     * @param invalidParams the new collection of invalid parameters; may be {@code null},
     *                      in which case an empty collection is used instead
     */
    public void setInvalidParams(Collection<InvalidParam> invalidParams) {
        this.invalidParams = invalidParams != null
                ? Collections.unmodifiableCollection(invalidParams)
                : Collections.emptyList();
    }
}