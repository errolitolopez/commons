package io.github.uncaughterrol.commons.exception;

import io.github.uncaughterrol.commons.model.InvalidParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ExceptionFactory")
class ExceptionFactoryTest {

    // -------------------------------------------------------------------------
    // 400 Bad Request
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("validationFailed()")
    class ValidationFailed {

        @Test
        @DisplayName("should create ValidationException with invalid params")
        void shouldCreateWithInvalidParams() {
            Collection<InvalidParam> params = List.of(
                    new InvalidParam("email", "must be a valid email address"),
                    new InvalidParam("age", "must be greater than 0")
            );

            ValidationException ex = ExceptionFactory.validationFailed(params);

            assertEquals("Validation Failed", ex.getTitle());
            assertEquals("Request contains one or more invalid parameters", ex.getDetail());
            assertEquals(2, ex.getInvalidParams().size());
        }

        @Test
        @DisplayName("should create ValidationException with custom detail and invalid params")
        void shouldCreateWithCustomDetailAndInvalidParams() {
            Collection<InvalidParam> params = List.of(
                    new InvalidParam("email", "must be a valid email address")
            );

            ValidationException ex = ExceptionFactory.validationFailed("Custom validation message", params);

            assertEquals("Validation Failed", ex.getTitle());
            assertEquals("Custom validation message", ex.getDetail());
            assertEquals(1, ex.getInvalidParams().size());
        }

        @Test
        @DisplayName("should not allow mutation of invalid params after creation")
        @SuppressWarnings("ThrowableNotThrown")
        void shouldReturnUnmodifiableInvalidParams() {
            Collection<InvalidParam> params = List.of(
                    new InvalidParam("email", "must be a valid email address")
            );

            ValidationException ex = ExceptionFactory.validationFailed(params);

            assertThrows(UnsupportedOperationException.class, () ->
                    ex.getInvalidParams().add(new InvalidParam("name", "must not be blank"))
            );
        }
    }

    // -------------------------------------------------------------------------
    // 401 Unauthorized
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("unauthorized()")
    class Unauthorized {

        @Test
        @DisplayName("should create UnauthorizedException with default detail")
        void shouldCreateWithDefaultDetail() {
            UnauthorizedException ex = ExceptionFactory.unauthorized();

            assertEquals("Unauthorized", ex.getTitle());
            assertEquals("Authentication is required to access this resource", ex.getDetail());
        }

        @Test
        @DisplayName("should create UnauthorizedException with custom detail")
        void shouldCreateWithCustomDetail() {
            UnauthorizedException ex = ExceptionFactory.unauthorized("Token has expired");

            assertEquals("Unauthorized", ex.getTitle());
            assertEquals("Token has expired", ex.getDetail());
        }
    }

    // -------------------------------------------------------------------------
    // 403 Forbidden
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("forbidden()")
    class Forbidden {

        @Test
        @DisplayName("should create ForbiddenException with default detail")
        void shouldCreateWithDefaultDetail() {
            ForbiddenException ex = ExceptionFactory.forbidden();

            assertEquals("Forbidden", ex.getTitle());
            assertEquals("You do not have permission to perform this action", ex.getDetail());
        }

        @Test
        @DisplayName("should create ForbiddenException with custom detail")
        void shouldCreateWithCustomDetail() {
            ForbiddenException ex = ExceptionFactory.forbidden("Admin access required");

            assertEquals("Forbidden", ex.getTitle());
            assertEquals("Admin access required", ex.getDetail());
        }
    }

    // -------------------------------------------------------------------------
    // 404 Not Found
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("notFound()")
    class NotFound {

        @Test
        @DisplayName("should create ResourceNotFoundException with numeric ID")
        void shouldCreateWithNumericId() {
            ResourceNotFoundException ex = ExceptionFactory.notFound("User", 42L);

            assertEquals("Resource Not Found", ex.getTitle());
            assertEquals("User with identifier 42 could not be found", ex.getDetail());
        }

        @Test
        @DisplayName("should create ResourceNotFoundException with string key")
        void shouldCreateWithStringKey() {
            ResourceNotFoundException ex = ExceptionFactory.notFound("User", "john@example.com");

            assertEquals("Resource Not Found", ex.getTitle());
            assertEquals("User with identifier john@example.com could not be found", ex.getDetail());
        }

        @Test
        @DisplayName("should create ResourceNotFoundException with custom detail")
        void shouldCreateWithCustomDetail() {
            ResourceNotFoundException ex = ExceptionFactory.notFound("The requested resource was not found");

            assertEquals("Resource Not Found", ex.getTitle());
            assertEquals("The requested resource was not found", ex.getDetail());
        }
    }

    // -------------------------------------------------------------------------
    // 409 Conflict
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("alreadyExists()")
    class AlreadyExists {

        @Test
        @DisplayName("should create ResourceAlreadyExistsException with resource, field and value")
        void shouldCreateWithResourceFieldAndValue() {
            ResourceAlreadyExistsException ex = ExceptionFactory.alreadyExists("User", "email", "john@example.com");

            assertEquals("Resource Already Exists", ex.getTitle());
            assertEquals("User with email john@example.com already exists", ex.getDetail());
        }

        @Test
        @DisplayName("should create ResourceAlreadyExistsException with custom detail")
        void shouldCreateWithCustomDetail() {
            ResourceAlreadyExistsException ex = ExceptionFactory.alreadyExists("Email is already taken");

            assertEquals("Resource Already Exists", ex.getTitle());
            assertEquals("Email is already taken", ex.getDetail());
        }
    }

    // -------------------------------------------------------------------------
    // 429 Too Many Requests
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("rateLimitExceeded()")
    class RateLimitExceeded {

        @Test
        @DisplayName("should create RateLimitException with default detail")
        void shouldCreateWithDefaultDetail() {
            RateLimitException ex = ExceptionFactory.rateLimitExceeded();

            assertEquals("Too Many Requests", ex.getTitle());
            assertEquals("You have exceeded the allowed request rate limit", ex.getDetail());
        }

        @Test
        @DisplayName("should create RateLimitException with custom detail")
        void shouldCreateWithCustomDetail() {
            RateLimitException ex = ExceptionFactory.rateLimitExceeded("Maximum 100 requests per minute allowed");

            assertEquals("Too Many Requests", ex.getTitle());
            assertEquals("Maximum 100 requests per minute allowed", ex.getDetail());
        }
    }

    // -------------------------------------------------------------------------
    // 500 Internal Server Error
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("internal()")
    class Internal {

        @Test
        @DisplayName("should create InternalServerException with default detail")
        void shouldCreateWithDefaultDetail() {
            InternalServerException ex = ExceptionFactory.internal();

            assertEquals("Internal Server Error", ex.getTitle());
            assertEquals("An unexpected error occurred, please try again later", ex.getDetail());
        }

        @Test
        @DisplayName("should create InternalServerException with custom detail")
        void shouldCreateWithCustomDetail() {
            InternalServerException ex = ExceptionFactory.internal("Database connection failed");

            assertEquals("Internal Server Error", ex.getTitle());
            assertEquals("Database connection failed", ex.getDetail());
        }

        @Test
        @DisplayName("should create InternalServerException wrapping a cause")
        void shouldCreateWrappingCause() {
            RuntimeException cause = new RuntimeException("Something went wrong");

            InternalServerException ex = ExceptionFactory.internal(cause);

            assertEquals("Internal Server Error", ex.getTitle());
            assertEquals(cause, ex.getCause());
        }

        @Test
        @DisplayName("should create InternalServerException with custom detail and cause")
        void shouldCreateWithCustomDetailAndCause() {
            RuntimeException cause = new RuntimeException("Something went wrong");

            InternalServerException ex = ExceptionFactory.internal("Database connection failed", cause);

            assertEquals("Internal Server Error", ex.getTitle());
            assertEquals("Database connection failed", ex.getDetail());
            assertEquals(cause, ex.getCause());
        }
    }

    // -------------------------------------------------------------------------
    // InvalidParam
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("InvalidParam")
    class InvalidParamTest {

        @Test
        @DisplayName("should store name and message correctly")
        void shouldStoreNameAndMessage() {
            InvalidParam param = new InvalidParam("email", "must be a valid email address");

            assertEquals("email", param.getName());
            assertEquals("must be a valid email address", param.getMessage());
        }

        @Test
        @DisplayName("should return correct toString representation")
        void shouldReturnCorrectToString() {
            InvalidParam param = new InvalidParam("email", "must be a valid email address");

            assertEquals("InvalidParam{name='email', message='must be a valid email address'}", param.toString());
        }
    }
}