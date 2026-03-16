# MyContacts App

A Java console application demonstrating object-oriented design, design patterns, and core Java concepts through a contact management system.

## UC-01: User Registration

**Actor:** New User

**Description:** User creates an account with email, password, and profile information. Supports two account types — Free and Premium.

### Design Patterns
- **Builder Pattern** (`UserBuilder`) — Step-by-step user construction with input validation
- **Factory Pattern** (`UserFactory`) — Creates the correct user subtype based on type string

### Java Concepts
- `MessageDigest` (SHA-256) for password hashing
- Regular expressions for email validation
- `UUID`, `Optional`, `LocalDateTime`, enhanced switch expressions

---

## UC-02: User Authentication

**Actor:** Registered User

**Description:** User logs in with credentials to access their contact list. Supports logout and session tracking.

### Design Patterns
- **Strategy Pattern** (`AuthenticationStrategy` → `BasicAuth`) — Swappable authentication algorithms
- **Singleton Pattern** (`SessionManager`) — Single instance tracks the currently logged-in user

### Java Concepts
- `Optional<User>` for nullable login results, Singleton with lazy initialization

---

## UC-03: User Profile Management

**Actor:** Logged-in User

**Description:** User views and updates profile information (name, email, password) with undo capability for all changes.

### Design Patterns
- **Command Pattern** (`ProfileCommand` → `UpdateNameCommand`, `UpdateEmailCommand`, `ChangePasswordCommand`) — Encapsulates profile edits as objects with execute/undo

### Java Concepts
- Interface-based polymorphism, command history tracking, password verification

---

## UC-04: Create Contact

**Actor:** Logged-in User

**Description:** User adds a new contact (Person or Organization) with multiple phone numbers, email addresses, and optional fields like nickname, industry, website, and notes.

### OOP Concepts Used
- **Inheritance** — `Contact` → `Person` / `Organization` hierarchy
- **Composition** — `Contact` has `List<PhoneNumber>` and `List<Email>`
- **Abstraction** — Abstract `Contact` class with `getContactType()` method
- **Encapsulation** — Private fields, defensive copies on getters

### Design Patterns
- **Builder Pattern** (`ContactBuilder`) — Fluent builder for step-by-step contact construction
- **Factory Pattern** (`ContactFactory`) — Creates `Person` or `Organization` using enhanced switch

### Java Concepts
- `UUID` for unique contact IDs
- `LocalDateTime` for created/updated timestamps
- `List<PhoneNumber>`, `List<Email>` — Collections for multi-valued fields
- Regex validation on email addresses
- Soft delete flag for future UC-07
- Method references (`contact::addPhone`)

### Package Structure
```
src/com/mycontacts/
├── Main.java
├── common/
│   ├── User.java, FreeUser.java, PremiumUser.java
│   ├── UserRepository.java
│   ├── Contact.java, Person.java, Organization.java
│   ├── PhoneNumber.java, Email.java
│   └── ContactRepository.java
├── registration/
│   ├── UserBuilder.java, UserFactory.java
│   ├── RegistrationService.java, RegistrationMenu.java
├── authentication/
│   ├── AuthenticationStrategy.java, BasicAuth.java
│   ├── SessionManager.java, LoginMenu.java
├── profile/
│   ├── ProfileCommand.java
│   ├── UpdateNameCommand.java, UpdateEmailCommand.java
│   ├── ChangePasswordCommand.java, ProfileCommandHistory.java
│   └── ProfileMenu.java
└── contact/
    ├── ContactBuilder.java, ContactFactory.java
    └── CreateContactMenu.java
```

### How to Run
```bash
javac -d out -sourcepath src src/com/mycontacts/Main.java
java -cp out com.mycontacts.Main
```
