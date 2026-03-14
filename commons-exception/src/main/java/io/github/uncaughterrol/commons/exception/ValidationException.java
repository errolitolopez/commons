package io.github.uncaughterrol.commons.exception;

import io.github.uncaughterrol.commons.model.InvalidParam;

import java.util.Collection;

/**
 * Thrown when a request fails validation.
 *
 * <p>Maps to HTTP 400 Bad Request. Carries a list of {@link InvalidParam}
 * describing which fields failed and why.
 *
 * <pre>{@code
 * throw new ValidationException(List.of(
 *     new InvalidParam("email", "must be a valid email address"),
 *     new InvalidParam("age", "must be greater than 0")
 * ));
 * }</pre>
 */
public class ValidationException extends ApiException {

    private static final String TITLE = "Validation Failed";
    private static final String DEFAULT_DETAIL = "Request contains one or more invalid parameters";

    /**
     * Constructs a {@code ValidationException} with a list of invalid parameters.
     *
     * @param invalidParams the collection of invalid parameters; must not be {@code null}
     */
    public ValidationException(Collection<InvalidParam> invalidParams) {
        super(TITLE, DEFAULT_DETAIL, invalidParams);
    }

    /**
     * Constructs a {@code ValidationException} with a custom detail message and invalid parameters.
     *
     * @param detail        a human-readable explanation of the validation failure
     * @param invalidParams the collection of invalid parameters; must not be {@code null}
     */
    public ValidationException(String detail, Collection<InvalidParam> invalidParams) {
        super(TITLE, detail, invalidParams);
    }
}