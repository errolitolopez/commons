# commons

![Java](https://img.shields.io/badge/Java-8+-orange)
![License](https://img.shields.io/badge/license-MIT-blue)
![Lightweight](https://img.shields.io/badge/dependencies-0-brightgreen)
![Maven Central](https://img.shields.io/maven-central/v/io.github.uncaughterrol/commons)

**commons** is a collection of lightweight, dependency-free Java utility libraries designed for everyday backend development.

Each module is **independently published** — pull only what you need.

---

# Modules

| Module                                   | Description                                  | Version                                                                                            |
|------------------------------------------|----------------------------------------------|----------------------------------------------------------------------------------------------------|
| [commons-security](./commons-security)   | HMAC-SHA256 request signing and verification | ![Maven Central](https://img.shields.io/maven-central/v/io.github.uncaughterrol/commons-security)  |
| [commons-exception](./commons-exception) | Standardized API exceptions & factory        | ![Maven Central](https://img.shields.io/maven-central/v/io.github.uncaughterrol/commons-exception) |

> More modules coming soon.

---

# Installation

Pick only the modules you need.

## Maven

```xml

<dependency>
    <groupId>io.github.uncaughterrol</groupId>
    <artifactId>commons-security</artifactId>
    <version>0.1.0</version>
</dependency>

<dependency>
    <groupId>io.github.uncaughterrol</groupId>
    <artifactId>commons-exception</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Gradle

```gradle
implementation 'io.github.uncaughterrol:commons-security:0.1.0'
implementation 'io.github.uncaughterrol:commons-exception:0.1.0'
```

---

# Module Details

## commons-security

HMAC-SHA256 request signing and verification.

```java
Map<String, String> payload = Map.of(
        "userId", "42",
        "action", "transfer"
);

// Sign
String signature = EncryptionUtils.sign(payload, "my-secret-key");

// Verify
boolean valid = EncryptionUtils.verify(payload, signature, "my-secret-key");
```

→ [Full documentation](./commons-security/README.md)


## commons-exception

Standardized API exceptions & factory
```
// 404 Not Found
throw ExceptionFactory.notFound("User", 42);

// 409 Conflict
throw ExceptionFactory.alreadyExists("User", "email", "john@example.com");

// 500 Internal Error
throw ExceptionFactory.internal("Connection failed", databaseError);

```


→ [Full documentation](./commons-exception/README.md)

---

# License

MIT License