# MyContacts App

A Java console application demonstrating object-oriented design, design patterns, and core Java concepts through a contact management system.

## UC-01: User Registration

**Actor:** New User

**Description:** User creates an account with email, password, and profile information. Supports two account types — Free and Premium.

### OOP Concepts Used
- **Encapsulation** — Private fields with getters/setters in `User` class
- **Inheritance** — `FreeUser` and `PremiumUser` extend abstract `User`
- **Abstraction** — Abstract `User` class with `getUserType()` method
- **Polymorphism** — Factory returns different user subtypes via a common reference

### Design Patterns
- **Builder Pattern** (`UserBuilder`) — Step-by-step user construction with input validation
- **Factory Pattern** (`UserFactory`) — Creates the correct user subtype based on type string

### Java Concepts
- `MessageDigest` (SHA-256) for password hashing
- Regular expressions for email validation
- `UUID` for unique user IDs
- `Optional` for safe user lookups
- `LocalDateTime` for timestamps
- Exception handling for validation errors
- Enhanced switch expressions

### Package Structure
```
src/com/mycontacts/
├── Main.java
├── common/
│   ├── User.java
│   ├── FreeUser.java
│   ├── PremiumUser.java
│   └── UserRepository.java
└── registration/
    ├── UserBuilder.java
    ├── UserFactory.java
    ├── RegistrationService.java
    └── RegistrationMenu.java
```

### How to Run
```bash
javac -d out -sourcepath src src/com/mycontacts/Main.java
java -cp out com.mycontacts.Main
```

### Sample Flow
```
===== User Registration =====
Enter your name: John Doe
Enter your email: john@example.com
Enter password (min 6 chars): secret123
Account type (free/premium): free
Registration successful!
Welcome, John Doe [Free account]
```
