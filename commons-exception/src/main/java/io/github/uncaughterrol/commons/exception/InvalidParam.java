package io.github.uncaughterrol.commons.exception;

/**
 * Represents a single invalid parameter in an API request.
 *
 * <p>Typically used alongside {@link ApiException} to communicate which specific
 * fields or parameters failed validation and why. For example:
 *
 * <pre>{@code
 * List<InvalidParam> params = List.of(
 *     new InvalidParam("email", "must be a valid email address"),
 *     new InvalidParam("age", "must be greater than 0")
 * );
 *
 * throw new ApiException("Validation Failed", "Request contains invalid parameters", params);
 * }</pre>
 */
public class InvalidParam {

    /**
     * The name of the invalid parameter or field (e.g. {@code "email"}, {@code "userId"}).
     */
    private final String name;

    /**
     * A human-readable message describing why the parameter is invalid
     * (e.g. {@code "must not be blank"}, {@code "must be a valid email address"}).
     */
    private final String message;

    /**
     * Constructs an {@code InvalidParam} with a field name and validation message.
     *
     * @param name    the name of the invalid parameter; must not be {@code null}
     * @param message a description of why the parameter is invalid; must not be {@code null}
     */
    public InvalidParam(String name, String message) {
        this.name = name;
        this.message = message;
    }

    /**
     * Returns the name of the invalid parameter.
     *
     * @return the parameter name; never {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the message describing why the parameter is invalid.
     *
     * @return the validation message; never {@code null}
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns a string representation of this invalid parameter in the format
     * {@code InvalidParam{name='email', message='must be a valid email address'}}.
     *
     * @return a human-readable string representation
     */
    @Override
    public String toString() {
        return "InvalidParam{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}