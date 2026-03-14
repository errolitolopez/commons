package io.github.uncaughterrol.commons.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidParamTest {

    @Test
    void shouldCreateInvalidParamWithNameAndMessage() {
        InvalidParam param = new InvalidParam("email", "must be a valid email address");

        assertEquals("email", param.getName());
        assertEquals("must be a valid email address", param.getMessage());
    }

    @Test
    void shouldReturnCorrectName() {
        InvalidParam param = new InvalidParam("userId", "must not be null");

        assertEquals("userId", param.getName());
    }

    @Test
    void shouldReturnCorrectMessage() {
        InvalidParam param = new InvalidParam("age", "must be greater than 0");

        assertEquals("must be greater than 0", param.getMessage());
    }

    @Test
    void shouldReturnCorrectToString() {
        InvalidParam param = new InvalidParam("email", "must be a valid email address");

        String expected = "InvalidParam{name='email', message='must be a valid email address'}";
        assertEquals(expected, param.toString());
    }

    @Test
    void shouldHandleEmptyStringName() {
        InvalidParam param = new InvalidParam("", "some message");

        assertEquals("", param.getName());
    }

    @Test
    void shouldHandleEmptyStringMessage() {
        InvalidParam param = new InvalidParam("field", "");

        assertEquals("", param.getMessage());
    }

    @Test
    void shouldHandleSpecialCharactersInName() {
        InvalidParam param = new InvalidParam("user.address.street", "must not be blank");

        assertEquals("user.address.street", param.getName());
    }

    @Test
    void shouldHandleSpecialCharactersInMessage() {
        InvalidParam param = new InvalidParam("price", "must be >= 0 and <= 9999.99");

        assertEquals("must be >= 0 and <= 9999.99", param.getMessage());
    }

    @Test
    void toStringShouldContainName() {
        InvalidParam param = new InvalidParam("username", "must not be blank");

        assertTrue(param.toString().contains("username"));
    }

    @Test
    void toStringShouldContainMessage() {
        InvalidParam param = new InvalidParam("username", "must not be blank");

        assertTrue(param.toString().contains("must not be blank"));
    }
}