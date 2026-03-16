# MyContacts App

A Java console application demonstrating object-oriented design, design patterns, and core Java concepts through a contact management system.

## UC-01: User Registration
- **Patterns:** Builder (`UserBuilder`), Factory (`UserFactory`)
- **Concepts:** SHA-256 hashing, regex validation, UUID, Optional, enhanced switch

## UC-02: User Authentication
- **Patterns:** Strategy (`AuthenticationStrategy` → `BasicAuth`), Singleton (`SessionManager`)
- **Concepts:** Optional<User>, lazy initialization, session management

## UC-03: User Profile Management
- **Patterns:** Command (`ProfileCommand` → `UpdateNameCommand`, `UpdateEmailCommand`, `ChangePasswordCommand`)
- **Concepts:** Execute/undo, command history tracking, password verification

## UC-04: Create Contact
- **Patterns:** Builder (`ContactBuilder`), Factory (`ContactFactory`)
- **OOP:** Inheritance (`Contact` → `Person`/`Organization`), Composition (`List<PhoneNumber>`, `List<Email>`)
- **Concepts:** UUID, LocalDateTime, Collections, regex email validation, soft delete flag

## UC-05: View Contact Details
- **Patterns:** Decorator (`DisplayDecorator` → `UpperCaseDecorator`, `MaskedEmailDecorator`)
- **OOP:** Interface-based polymorphism, decorator chaining
- **Concepts:** String manipulation, display formatting, composable decorators

### Decorator Pattern Details
- `ContactDisplay` — interface with `display(Contact)` method
- `BaseContactDisplay` — default formatter using `toString()`
- `DisplayDecorator` — abstract decorator wrapping another `ContactDisplay`
- `UpperCaseDecorator` — uppercases the contact name
- `MaskedEmailDecorator` — masks emails (e.g. `j***@gmail.com`)
- Decorators can be **chained**: `MaskedEmail(UpperCase(Base))`

### Package Structure
```
src/com/mycontacts/
├── Main.java
├── common/          # User, Contact models, repositories
├── registration/    # UC-01: UserBuilder, UserFactory
├── authentication/  # UC-02: Strategy, SessionManager
├── profile/         # UC-03: Command pattern
├── contact/         # UC-04: ContactBuilder, ContactFactory
└── viewcontact/     # UC-05: Decorator pattern
```

### How to Run
```bash
javac -d out -sourcepath src src/com/mycontacts/Main.java
java -cp out com.mycontacts.Main
```
