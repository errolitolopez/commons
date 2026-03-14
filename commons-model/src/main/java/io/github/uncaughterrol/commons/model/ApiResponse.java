package io.github.uncaughterrol.commons.model;

import java.util.Collection;
import java.util.Collections;

/**
 * Standard API response wrapper used for both success and error responses.
 *
 * <p>Contains a title, detail message, HTTP status code, optional response data,
 * and optional invalid parameters typically used for validation errors.
 *
 * <pre>{@code
 * ApiResponse<UserDto> response = ApiResponse.success(
 *   "User Created",
 *   "User was created successfully",
 *   201,
 *   userDto
 * );
 * }</pre>
 *
 * <pre>{@code
 * ApiResponse<Void> response = ApiResponse.error(
 *   "Validation Failed",
 *   "Request contains invalid parameters",
 *   400,
 *   invalidParams
 * );
 * }</pre>
 *
 * @param <T> the type of the response payload
 */
public class ApiResponse<T> {

    private final String title;
    private final String detail;
    private final int status;
    private final T data;
    private final Collection<InvalidParam> invalidParams;

    /**
     * Constructs an {@code ApiResponse}.
     *
     * @param title         a short summary of the response
     * @param detail        a human-readable explanation of the response
     * @param status        the HTTP status code
     * @param data          the response payload; may be {@code null}
     * @param invalidParams the collection of invalid parameters; may be empty
     */
    public ApiResponse(String title, String detail, int status, T data, Collection<InvalidParam> invalidParams) {
        this.title = title;
        this.detail = detail;
        this.status = status;
        this.data = data;
        this.invalidParams = invalidParams;
    }

    /**
     * Creates a success response.
     *
     * @param title  a short summary of the response
     * @param detail a human-readable explanation of the success
     * @param status the HTTP status code
     * @param data   the response payload
     * @param <T>    the payload type
     * @return a success {@code ApiResponse}
     */
    public static <T> ApiResponse<T> success(String title, String detail, int status, T data) {
        return new ApiResponse<>(title, detail, status, data, Collections.emptyList());
    }

    /**
     * Creates an error response without invalid parameters.
     *
     * @param title  a short summary of the error
     * @param detail a human-readable explanation of the error
     * @param status the HTTP status code
     * @param <T>    the payload type
     * @return an error {@code ApiResponse}
     */
    public static <T> ApiResponse<T> error(String title, String detail, int status) {
        return new ApiResponse<>(title, detail, status, null, Collections.emptyList());
    }

    /**
     * Creates an error response with invalid parameters.
     *
     * @param title         a short summary of the error
     * @param detail        a human-readable explanation of the error
     * @param status        the HTTP status code
     * @param invalidParams the collection of invalid parameters
     * @param <T>           the payload type
     * @return an error {@code ApiResponse}
     */
    public static <T> ApiResponse<T> error(String title, String detail, int status, Collection<InvalidParam> invalidParams) {
        return new ApiResponse<>(title, detail, status, null, invalidParams);
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Collection<InvalidParam> getInvalidParams() {
        return invalidParams;
    }
}