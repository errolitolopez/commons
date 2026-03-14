package io.github.uncaughterrol.commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;


class EncryptionUtilsTests {

    private static final String SECRET = "test-secret";

    @Test
    void shouldCreateAndVerifySignature() {

        Map<String, String> payload = Map.of(
                "recipient", "user@email.com",
                "type", "FORGOT_PASSWORD",
                "timestamp", "1710000000"
        );

        String signature = EncryptionUtils.sign(payload, SECRET);

        boolean valid = EncryptionUtils.verify(payload, signature, SECRET);

        Assertions.assertTrue(valid);
    }

    @Test
    void shouldFailIfPayloadModified() {

        Map<String, String> payload = Map.of(
                "recipient", "user@email.com",
                "type", "FORGOT_PASSWORD",
                "timestamp", "1710000000"
        );

        String signature = EncryptionUtils.sign(payload, SECRET);

        Map<String, String> tampered = Map.of(
                "recipient", "attacker@email.com",
                "type", "FORGOT_PASSWORD",
                "timestamp", "1710000000"
        );

        boolean valid = EncryptionUtils.verify(tampered, signature, SECRET);

        Assertions.assertFalse(valid);
    }

    @Test
    void shouldFailIfSecretDifferent() {
        Map<String, String> payload = Map.of(
                "recipient", "user@email.com",
                "type", "FORGOT_PASSWORD",
                "timestamp", "1710000000"
        );

        String signature = EncryptionUtils.sign(payload, SECRET);

        boolean valid = EncryptionUtils.verify(payload, signature, "wrong-secret");

        Assertions.assertFalse(valid);
    }
}
