package io.github.uncaughterrol.commons.model;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void shouldCreateApiResponseViaConstructorWithAllFields() {
        InvalidParam param = new InvalidParam("field", "must not be null");
        ApiResponse<String> response = new ApiResponse<>("Title", "Detail", 200, "data", List.of(param));

        assertEquals("Title", response.getTitle());
        assertEquals("Detail", response.getDetail());
        assertEquals(200, response.getStatus());
        assertEquals("data", response.getData());
        assertEquals(1, response.getInvalidParams().size());
    }

    @Test
    void successShouldSetTitleDetailStatusAndData() {
        ApiResponse<String> response = ApiResponse.success("OK", "Operation successful", 200, "payload");

        assertEquals("OK", response.getTitle());
        assertEquals("Operation successful", response.getDetail());
        assertEquals(200, response.getStatus());
        assertEquals("payload", response.getData());
    }

    @Test
    void successShouldReturnEmptyInvalidParams() {
        ApiResponse<String> response = ApiResponse.success("OK", "Done", 200, "payload");

        assertNotNull(response.getInvalidParams());
        assertTrue(response.getInvalidParams().isEmpty());
    }

    @Test
    void successShouldSupportNullData() {
        ApiResponse<Void> response = ApiResponse.success("OK", "No content", 204, null);

        assertNull(response.getData());
    }

    @Test
    void successShouldSupportGenericPayloadType() {
        record UserDto(String name) {
        }
        UserDto user = new UserDto("Alice");

        ApiResponse<UserDto> response = ApiResponse.success("User Created", "User was created successfully", 201, user);

        assertEquals(user, response.getData());
        assertEquals(201, response.getStatus());
    }


    @Test
    void errorWithoutParamsShouldSetTitleDetailAndStatus() {
        ApiResponse<Void> response = ApiResponse.error("Not Found", "Resource does not exist", 404);

        assertEquals("Not Found", response.getTitle());
        assertEquals("Resource does not exist", response.getDetail());
        assertEquals(404, response.getStatus());
    }

    @Test
    void errorWithoutParamsShouldHaveNullData() {
        ApiResponse<Void> response = ApiResponse.error("Error", "Something went wrong", 500);

        assertNull(response.getData());
    }

    @Test
    void errorWithoutParamsShouldReturnEmptyInvalidParams() {
        ApiResponse<Void> response = ApiResponse.error("Error", "Something went wrong", 500);

        assertNotNull(response.getInvalidParams());
        assertTrue(response.getInvalidParams().isEmpty());
    }

    @Test
    void errorWithParamsShouldSetTitleDetailAndStatus() {
        List<InvalidParam> params = List.of(new InvalidParam("email", "must be a valid email address"));
        ApiResponse<Void> response = ApiResponse.error("Validation Failed", "Invalid request", 400, params);

        assertEquals("Validation Failed", response.getTitle());
        assertEquals("Invalid request", response.getDetail());
        assertEquals(400, response.getStatus());
    }

    @Test
    void errorWithParamsShouldHaveNullData() {
        List<InvalidParam> params = List.of(new InvalidParam("email", "must be a valid email address"));
        ApiResponse<Void> response = ApiResponse.error("Validation Failed", "Invalid request", 400, params);

        assertNull(response.getData());
    }

    @Test
    void errorWithParamsShouldReturnProvidedInvalidParams() {
        InvalidParam emailParam = new InvalidParam("email", "must be a valid email address");
        InvalidParam ageParam = new InvalidParam("age", "must be greater than 0");
        List<InvalidParam> params = List.of(emailParam, ageParam);

        ApiResponse<Void> response = ApiResponse.error("Validation Failed", "Invalid request", 400, params);

        Collection<InvalidParam> result = response.getInvalidParams();
        assertEquals(2, result.size());
        assertTrue(result.contains(emailParam));
        assertTrue(result.contains(ageParam));
    }

    @Test
    void errorWithEmptyParamsShouldReturnEmptyInvalidParams() {
        ApiResponse<Void> response = ApiResponse.error("Validation Failed", "Invalid request", 400, List.of());

        assertNotNull(response.getInvalidParams());
        assertTrue(response.getInvalidParams().isEmpty());
    }
}