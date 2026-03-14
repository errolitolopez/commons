package io.github.uncaughterrol.commons.model;

/**
 * Represents a single invalid parameter in an API request.
 *
 * <p>Typically used in validation errors to describe which field failed
 * validation and the reason for the failure.
 *
 * <pre>{@code
 * List<InvalidParam> params = List.of(
 *     new InvalidParam("email", "must be a valid email address"),
 *     new InvalidParam("age", "must be greater than 0")
 * );
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

    @Override
    public String toString() {
        return "InvalidParam{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}