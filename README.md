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

## UC-05: View Contact Details
- **Patterns:** Decorator (`DisplayDecorator` → `UpperCaseDecorator`, `MaskedEmailDecorator`)
- **Concepts:** Decorator chaining, display formatting

## UC-06: Edit Contact
- **Patterns:** Command (`EditCommand` → `EditNameCommand`, `EditPhoneCommand`, `EditEmailCommand`, `EditNotesCommand`), Memento (`ContactMemento`)
- **OOP:** Undo/redo via dual stacks in `CommandHistory`, full state restore via Memento snapshot

### Command + Memento Details
- `EditCommand` — interface with `execute()`, `undo()`, `getDescription()`
- `CommandHistory` — manages undo/redo stacks; new actions clear redo stack
- `ContactMemento` — immutable snapshot of contact state (name, notes, phones, emails)
- `EditContactMenu` — select a contact, edit fields, undo/redo individual changes, or restore entire original state via Memento

### Package Structure
```
src/com/mycontacts/
├── Main.java
├── common/          # User, Contact models, repositories
├── registration/    # UC-01: Builder, Factory
├── authentication/  # UC-02: Strategy, Singleton
├── profile/         # UC-03: Command pattern
├── contact/         # UC-04: ContactBuilder, ContactFactory
├── viewcontact/     # UC-05: Decorator pattern
└── editcontact/     # UC-06: Command + Memento
```

### How to Run
```bash
javac -d out -sourcepath src src/com/mycontacts/Main.java
java -cp out com.mycontacts.Main
```
