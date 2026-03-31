package io.github.uncaughterrol.commons.utils;

import java.security.SecureRandom;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class for generating random tokens and composite keys.
 *
 * <p>Exposes two variants for all token methods:</p>
 * <ul>
 *   <li><b>Secure</b> — backed by {@link SecureRandom}; cryptographically strong.
 *       Use for OTPs, session tokens, password reset links, or any
 *       security-sensitive context.</li>
 *   <li><b>Fast</b> — backed by {@link ThreadLocalRandom}; statistically random
 *       but not cryptographically secure. Use for test data, display codes,
 *       idempotency keys, or any non-security context.</li>
 * </ul>
 *
 * <p>This class is not instantiable.</p>
 */
public final class TokenGenerator {

    private TokenGenerator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // -------------------------------------------------------------------------
    // RNG Strategy
    // -------------------------------------------------------------------------

    /**
     * Internal abstraction over a random integer source.
     *
     * <p>Allows {@link #randomString} to remain agnostic of the underlying RNG,
     * making it trivial to swap between secure and fast implementations.</p>
     */
    @FunctionalInterface
    private interface RandomSource {
        /**
         * Returns a random {@code int} in the range {@code [0, bound)}.
         *
         * @param bound the exclusive upper bound; must be positive
         * @return a non-negative random int less than {@code bound}
         */
        int nextInt(int bound);
    }

    /**
     * Cryptographically strong RNG, shared across all threads.
     *
     * <p>{@link SecureRandom} is thread-safe and seeded from OS entropy.
     * A single instance is reused to avoid repeated, expensive seeding.</p>
     */
    private static final SecureRandom SECURE_RNG = new SecureRandom();

    /**
     * Fast statistical RNG delegating to {@link ThreadLocalRandom}.
     *
     * <p>Not cryptographically secure, but contention-free and significantly
     * faster under concurrent load. Each thread maintains its own instance.</p>
     */
    private static final RandomSource FAST_RNG = bound -> ThreadLocalRandom.current().nextInt(bound);

    // -------------------------------------------------------------------------
    // Alphabets
    // -------------------------------------------------------------------------

    /**
     * Alphabet used for purely alphabetic tokens (A–Z, a–z).
     */
    private static final char[] ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Base-62 alphabet used for alphanumeric tokens (0–9, A–Z, a–z).
     */
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Alphabet used for numeric tokens (0–9).
     */
    private static final char[] NUMERIC = "0123456789".toCharArray();

    // -------------------------------------------------------------------------
    // Secure tokens  (use for OTPs, session IDs, password resets, etc.)
    // -------------------------------------------------------------------------

    /**
     * Generates a cryptographically secure alphanumeric token of the default length (6).
     *
     * <p>Example:</p>
     * <pre>
     * secureAlphanumericToken() → "aB3kQ7"
     * </pre>
     *
     * @return a 6-character secure alphanumeric token
     */
    public static String secureAlphanumericToken() {
        return secureAlphanumericToken(6);
    }

    /**
     * Generates a cryptographically secure numeric token of the default length (6).
     *
     * <p>Suitable for use as a one-time password (OTP).</p>
     *
     * <p>Example:</p>
     * <pre>
     * secureNumericToken() → "048273"
     * </pre>
     *
     * @return a 6-digit secure numeric token
     */
    public static String secureNumericToken() {
        return secureNumericToken(6);
    }

    /**
     * Generates a cryptographically secure alphanumeric token of the specified length.
     *
     * <p>Example:</p>
     * <pre>
     * secureAlphanumericToken(8) → "x3Kp9mAQ"
     * secureAlphanumericToken(5) → throws IllegalArgumentException
     * </pre>
     *
     * @param size the desired token length; must be at least 6
     * @return a secure alphanumeric token of the given length
     * @throws IllegalArgumentException if {@code size} is less than 6
     */
    public static String secureAlphanumericToken(int size) {
        if (size < 6) throw new IllegalArgumentException("Size must be at least 6");
        return randomString(BASE62, size, SECURE_RNG::nextInt);
    }

    /**
     * Generates a cryptographically secure alphabetic token of the specified length.
     *
     * <p>Example:</p>
     * <pre>
     * secureAlphaToken(4) → "kRmZ"
     * secureAlphaToken(0) → throws IllegalArgumentException
     * </pre>
     *
     * @param size the desired token length; must be at least 1
     * @return a secure alphabetic token of the given length
     * @throws IllegalArgumentException if {@code size} is less than 1
     */
    public static String secureAlphaToken(int size) {
        if (size < 1) throw new IllegalArgumentException("Size must be at least 1");
        return randomString(ALPHA, size, SECURE_RNG::nextInt);
    }

    /**
     * Generates a cryptographically secure numeric token of the specified length.
     *
     * <p>Suitable for use as a one-time password (OTP).</p>
     *
     * <p>Example:</p>
     * <pre>
     * secureNumericToken(4) → "7392"
     * secureNumericToken(0) → throws IllegalArgumentException
     * </pre>
     *
     * @param size the desired token length; must be at least 1
     * @return a secure numeric token of the given length
     * @throws IllegalArgumentException if {@code size} is less than 1
     */
    public static String secureNumericToken(int size) {
        if (size < 1) throw new IllegalArgumentException("Size must be at least 1");
        return randomString(NUMERIC, size, SECURE_RNG::nextInt);
    }

    // -------------------------------------------------------------------------
    // Fast tokens  (use for test data, display codes, idempotency keys, etc.)
    // -------------------------------------------------------------------------

    /**
     * Generates a fast (non-secure) alphanumeric token of the default length (6).
     *
     * <p>Not cryptographically secure. Do <b>not</b> use for authentication,
     * session management, or any security-sensitive context.</p>
     *
     * <p>Example:</p>
     * <pre>
     * fastAlphanumericToken() → "aB3kQ7"
     * </pre>
     *
     * @return a 6-character fast alphanumeric token
     */
    public static String fastAlphanumericToken() {
        return fastAlphanumericToken(6);
    }

    /**
     * Generates a fast (non-secure) numeric token of the default length (6).
     *
     * <p>Not cryptographically secure. Do <b>not</b> use for OTPs or
     * any security-sensitive context.</p>
     *
     * <p>Example:</p>
     * <pre>
     * fastNumericToken() → "048273"
     * </pre>
     *
     * @return a 6-digit fast numeric token
     */
    public static String fastNumericToken() {
        return fastNumericToken(6);
    }

    /**
     * Generates a fast (non-secure) alphanumeric token of the specified length.
     *
     * <p>Not cryptographically secure. Do <b>not</b> use for authentication,
     * session management, or any security-sensitive context.</p>
     *
     * <p>Example:</p>
     * <pre>
     * fastAlphanumericToken(8) → "x3Kp9mAQ"
     * fastAlphanumericToken(5) → throws IllegalArgumentException
     * </pre>
     *
     * @param size the desired token length; must be at least 6
     * @return a fast alphanumeric token of the given length
     * @throws IllegalArgumentException if {@code size} is less than 6
     */
    public static String fastAlphanumericToken(int size) {
        if (size < 6) throw new IllegalArgumentException("Size must be at least 6");
        return randomString(BASE62, size, FAST_RNG);
    }

    /**
     * Generates a fast (non-secure) alphabetic token of the specified length.
     *
     * <p>Not cryptographically secure. Do <b>not</b> use for authentication,
     * session management, or any security-sensitive context.</p>
     *
     * <p>Example:</p>
     * <pre>
     * fastAlphaToken(4) → "kRmZ"
     * fastAlphaToken(0) → throws IllegalArgumentException
     * </pre>
     *
     * @param size the desired token length; must be at least 1
     * @return a fast alphabetic token of the given length
     * @throws IllegalArgumentException if {@code size} is less than 1
     */
    public static String fastAlphaToken(int size) {
        if (size < 1) throw new IllegalArgumentException("Size must be at least 1");
        return randomString(ALPHA, size, FAST_RNG);
    }

    /**
     * Generates a fast (non-secure) numeric token of the specified length.
     *
     * <p>Not cryptographically secure. Do <b>not</b> use for OTPs or
     * any security-sensitive context.</p>
     *
     * <p>Example:</p>
     * <pre>
     * fastNumericToken(4) → "7392"
     * fastNumericToken(0) → throws IllegalArgumentException
     * </pre>
     *
     * @param size the desired token length; must be at least 1
     * @return a fast numeric token of the given length
     * @throws IllegalArgumentException if {@code size} is less than 1
     */
    public static String fastNumericToken(int size) {
        if (size < 1) throw new IllegalArgumentException("Size must be at least 1");
        return randomString(NUMERIC, size, FAST_RNG);
    }

    // -------------------------------------------------------------------------
    // Secure bank account number  (use for masking, display, or reference generation)
    // -------------------------------------------------------------------------

    /**
     * Generates a cryptographically secure bank account number of the default length (10)
     * that satisfies the Luhn checksum algorithm.
     *
     * <p>Example:</p>
     * <pre>
     * secureBankAccountNumber() → "3827461052"
     * </pre>
     *
     * @return a 10-digit secure numeric bank account number passing the Luhn check
     */
    public static String secureBankAccountNumber() {
        return secureBankAccountNumber(10);
    }

    /**
     * Generates a cryptographically secure bank account number of the specified length
     * that satisfies the Luhn checksum algorithm.
     *
     * <p>The first digit is guaranteed to be non-zero (1–9). The last digit is the
     * Luhn check digit, computed deterministically from the preceding digits.</p>
     *
     * <p>Example:</p>
     * <pre>
     * secureBankAccountNumber(12) → "483920174658"
     * secureBankAccountNumber(7)  → throws IllegalArgumentException
     * </pre>
     *
     * @param size the desired account number length; must be at least 8
     * @return a Luhn-valid secure numeric bank account number of the given length
     * @throws IllegalArgumentException if {@code size} is less than 8
     */
    public static String secureBankAccountNumber(int size) {
        if (size < 8) throw new IllegalArgumentException("Bank account number size must be at least 8");
        char[] buf = new char[size];
        buf[0] = NUMERIC[1 + SECURE_RNG.nextInt(9)];
        for (int i = 1; i < size - 1; i++) {
            buf[i] = NUMERIC[SECURE_RNG.nextInt(NUMERIC.length)];
        }
        buf[size - 1] = (char) ('0' + luhnCheckDigit(new String(buf, 0, size - 1)));
        return new String(buf);
    }

    /**
     * Generates a cryptographically secure bank account number with a given prefix
     * and total length, satisfying the Luhn checksum algorithm.
     *
     * <p>The prefix is prepended as-is. The remaining digits are randomly generated,
     * with the last digit being the Luhn check digit.</p>
     *
     * <p>Example:</p>
     * <pre>
     * secureBankAccountNumber("1000", 10) → "1000384729"
     * secureBankAccountNumber("2000", 20) → "20009374816253047865"
     * secureBankAccountNumber("1000", 5)  → throws IllegalArgumentException
     * secureBankAccountNumber("12AB", 10) → throws IllegalArgumentException
     * </pre>
     *
     * @param prefix the numeric prefix to prepend; must be non-null, non-empty, and digits only
     * @param size   the total desired length (prefix + random + check digit); must be at least 8
     *               and strictly greater than the prefix length
     * @return a Luhn-valid secure numeric bank account number of the given total length
     * @throws IllegalArgumentException if {@code prefix} is null, empty, contains non-digit characters,
     *                                  or if {@code size} is less than 8 or not greater than prefix length
     */
    public static String secureBankAccountNumber(String prefix, int size) {
        if (prefix == null || prefix.isEmpty())
            throw new IllegalArgumentException("Prefix must not be null or empty");
        for (char c : prefix.toCharArray()) {
            if (c < '0' || c > '9')
                throw new IllegalArgumentException("Prefix must contain only digits, got: '" + c + "'");
        }
        if (size < 8)
            throw new IllegalArgumentException("Bank account number size must be at least 8");
        if (size <= prefix.length())
            throw new IllegalArgumentException(
                    "Size must be greater than prefix length (" + prefix.length() + ")");

        char[] buf = new char[size];

        // Copy prefix
        for (int i = 0; i < prefix.length(); i++) {
            buf[i] = prefix.charAt(i);
        }

        // Fill random digits (leave last slot for check digit)
        for (int i = prefix.length(); i < size - 1; i++) {
            buf[i] = NUMERIC[SECURE_RNG.nextInt(NUMERIC.length)];
        }

        // Compute and append Luhn check digit
        buf[size - 1] = (char) ('0' + luhnCheckDigit(new String(buf, 0, size - 1)));

        return new String(buf);
    }

    /**
     * Generates a cryptographically secure bank account number with a given numeric prefix
     * and total length, satisfying the Luhn checksum algorithm.
     *
     * <p>Convenience overload that accepts the prefix as a {@code long}.
     * Delegates to {@link #secureBankAccountNumber(String, int)}.</p>
     *
     * <p>Example:</p>
     * <pre>
     * secureBankAccountNumber(1000L, 10) → "1000748362"
     * secureBankAccountNumber(2000L, 20) → "20001847362958401736"
     * </pre>
     *
     * @param prefix the numeric prefix to prepend; must be a positive value
     * @param size   the total desired length; must be at least 8 and strictly greater than
     *               the number of digits in {@code prefix}
     * @return a Luhn-valid secure numeric bank account number of the given total length
     * @throws IllegalArgumentException if {@code prefix} is not positive, or if size constraints are violated
     */
    public static String secureBankAccountNumber(long prefix, int size) {
        if (prefix <= 0)
            throw new IllegalArgumentException("Prefix must be a positive number");
        return secureBankAccountNumber(Long.toString(prefix), size);
    }

    /**
     * Validates whether the given numeric string passes the Luhn checksum algorithm.
     *
     * <p>The Luhn algorithm is widely used to validate identification numbers such as
     * credit card numbers, IMEI numbers, and similar account identifiers. It detects
     * all single-digit errors and most transposition errors.</p>
     *
     * <p>Example:</p>
     * <pre>
     * isValidLuhn("4532015112830366") → true   // valid Visa test number
     * isValidLuhn("1234567890")       → false  // invalid
     * isValidLuhn("abc")              → throws IllegalArgumentException
     * </pre>
     *
     * @param number a non-null, non-empty string of digits to validate
     * @return {@code true} if {@code number} satisfies the Luhn check; {@code false} otherwise
     * @throws IllegalArgumentException if {@code number} is null, empty, or contains non-digit characters
     */
    public static boolean isValidLuhn(String number) {
        if (number == null || number.isEmpty())
            throw new IllegalArgumentException("Number must not be null or empty");
        for (char c : number.toCharArray()) {
            if (c < '0' || c > '9')
                throw new IllegalArgumentException("Number must contain only digits, got: '" + c + "'");
        }
        return luhnSum(number) % 10 == 0;
    }

    // -------------------------------------------------------------------------
    // Luhn internals
    // -------------------------------------------------------------------------

    /**
     * Computes the Luhn check digit for the given partial number (all digits except the last).
     *
     * <p>The check digit is the value that, when appended to {@code partial}, makes the
     * full number pass the Luhn algorithm. It is always in the range {@code [0, 9]}.</p>
     *
     * @param partial a non-empty string of digits representing all but the final digit
     * @return the check digit to append, as an {@code int} in {@code [0, 9]}
     */
    private static int luhnCheckDigit(String partial) {
        int sum = luhnSum(partial + "0");
        return (10 - (sum % 10)) % 10;
    }

    /**
     * Computes the raw Luhn sum of the given digit string.
     *
     * <p>Starting from the rightmost digit and moving left, every second digit
     * is doubled; if the doubled value exceeds 9, its digits are summed (equivalent
     * to subtracting 9). All values are then accumulated.</p>
     *
     * @param number a non-empty string of digits
     * @return the Luhn sum (not yet reduced modulo 10)
     */
    private static int luhnSum(String number) {
        int sum = 0;
        boolean doubleIt = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = number.charAt(i) - '0';
            if (doubleIt) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            doubleIt = !doubleIt;
        }
        return sum;
    }

    // -------------------------------------------------------------------------
    // Composite key  (RNG-agnostic)
    // -------------------------------------------------------------------------

    /**
     * Builds a hyphen-delimited composite key from the given arguments.
     *
     * <p>Each argument must be either a {@link String} or a {@link Number};
     * any other type causes an {@link IllegalArgumentException} to be thrown.
     * Arguments are joined in order, separated by {@code -}.</p>
     *
     * <p>Example:</p>
     * <pre>
     * compositeKey("user", 42, "session")  → "user-42-session"
     * compositeKey("order", 99)            → "order-99"
     * compositeKey("x", new Object())      → throws IllegalArgumentException
     * </pre>
     *
     * @param args the values to join; each must be a {@code String} or {@code Number}
     * @return a hyphen-separated composite key
     * @throws IllegalArgumentException if any argument is not a {@code String} or {@code Number}
     */
    public static String compositeKey(Object... args) {
        StringJoiner joiner = new StringJoiner("-");
        for (Object arg : args) {
            if (arg instanceof String || arg instanceof Number) {
                joiner.add(arg.toString());
            } else {
                throw new IllegalArgumentException("Invalid argument: " + arg);
            }
        }
        return joiner.toString();
    }

    // -------------------------------------------------------------------------
    // Core
    // -------------------------------------------------------------------------

    /**
     * Generates a random string of the given length by sampling from the
     * provided alphabet using the given {@link RandomSource}.
     *
     * <p>The caller controls which RNG backs this call — either
     * {@link #SECURE_RNG} for cryptographic strength or {@link #FAST_RNG}
     * for maximum throughput.</p>
     *
     * @param alphabet the character pool to sample from; must not be empty
     * @param size     the number of characters to generate
     * @param rng      the random source to use
     * @return a randomly assembled string of the specified length
     */
    private static String randomString(char[] alphabet, int size, RandomSource rng) {
        final int len = alphabet.length;
        final char[] buf = new char[size];
        for (int i = 0; i < size; i++) {
            buf[i] = alphabet[rng.nextInt(len)];
        }
        return new String(buf);
    }
}