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
- **Patterns:** Command + Memento (`EditCommand`, `ContactMemento`, `CommandHistory`)
- **Concepts:** Undo/redo via dual stacks, full state restore via Memento snapshot

## UC-07: Delete Contact
- **Patterns:** Observer (`DeleteObserver` → `DeleteLogger`)
- **Concepts:** Soft delete vs Hard delete, confirmation, restore capability

## UC-08: Bulk Operations / Contact Groups
- **Patterns:** Composite (`ContactComponent` → `ContactLeaf`, `ContactGroup`)
- **OOP:** Recursive tree structure, uniform treatment of individuals and groups
- **Concepts:** Bulk soft-delete, sub-groups, recursive contact counting

### Composite Pattern Details
- `ContactComponent` — interface with `display()`, `getContactCount()`
- `ContactLeaf` — wraps a single `Contact`
- `ContactGroup` — composite holding contacts and sub-groups recursively
- `GroupMenu` — create groups, add/remove contacts, bulk delete, add sub-groups

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
├── editcontact/     # UC-06: Command + Memento
├── deletecontact/   # UC-07: Observer, soft/hard delete
└── groups/          # UC-08: Composite pattern
```

### How to Run
```bash
javac -d out -sourcepath src src/com/mycontacts/Main.java
java -cp out com.mycontacts.Main
```
