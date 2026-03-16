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

---

## UC-02: User Authentication

**Actor:** Registered User

**Description:** User logs in with credentials to access their contact list. Supports logout and session tracking.

### OOP Concepts Used
- **Polymorphism** — `AuthenticationStrategy` interface with concrete `BasicAuth` implementation
- **Encapsulation** — Password comparison logic hidden inside `BasicAuth`
- **Abstraction** — Authentication methods abstracted behind a common interface

### Design Patterns
- **Strategy Pattern** (`AuthenticationStrategy` → `BasicAuth`) — Swappable authentication algorithms
- **Singleton Pattern** (`SessionManager`) — Single instance tracks the currently logged-in user

### Java Concepts
- `Optional<User>` for handling nullable login results
- `MessageDigest` for password hash comparison
- Singleton with lazy initialization
- Session state management

---

## UC-03: User Profile Management

**Actor:** Logged-in User

**Description:** User views and updates profile information (name, email, password) with undo capability for all changes.

### OOP Concepts Used
- **Polymorphism** — `ProfileCommand` interface with multiple concrete implementations
- **Encapsulation** — Each command stores old/new values internally for undo
- **Abstraction** — Profile operations abstracted behind a uniform command interface

### Design Patterns
- **Command Pattern** (`ProfileCommand` → `UpdateNameCommand`, `UpdateEmailCommand`, `ChangePasswordCommand`) — Encapsulates profile edits as objects with execute/undo
- **Command History** (`ProfileCommandHistory`) — Tracks executed commands for undo support

### Java Concepts
- Interface-based polymorphism
- Password verification before change
- Input validation (email regex, password length)
- `List` as a command stack for history tracking

### Package Structure
```
src/com/mycontacts/
├── Main.java
├── common/
│   ├── User.java
│   ├── FreeUser.java
│   ├── PremiumUser.java
│   └── UserRepository.java
├── registration/
│   ├── UserBuilder.java
│   ├── UserFactory.java
│   ├── RegistrationService.java
│   └── RegistrationMenu.java
├── authentication/
│   ├── AuthenticationStrategy.java
│   ├── BasicAuth.java
│   ├── SessionManager.java
│   └── LoginMenu.java
└── profile/
    ├── ProfileCommand.java
    ├── UpdateNameCommand.java
    ├── UpdateEmailCommand.java
    ├── ChangePasswordCommand.java
    ├── ProfileCommandHistory.java
    └── ProfileMenu.java
```

### How to Run
```bash
javac -d out -sourcepath src src/com/mycontacts/Main.java
java -cp out com.mycontacts.Main
```

### Sample Flow
```
--- Main Menu ---
1. Register
2. Login
3. My Profile
4. Logout (John Doe)
0. Exit
Choose an option: 3

===== Profile Management =====
Name:    John Doe
Email:   john@example.com
Type:    Free
Joined:  2026-03-16
------------------------------
1. Update Name
2. Update Email
3. Change Password
4. Undo Last Change
5. View Change History
0. Back to Main Menu
Choose an option: 1
Enter new name: Johnny
Name updated to: Johnny
```
