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
- **Concepts:** Undo/redo via dual stacks, full state restore via Memento

## UC-07: Delete Contact
- **Patterns:** Observer (`DeleteObserver` → `DeleteLogger`)
- **Concepts:** Soft delete vs Hard delete, confirmation, restore

## UC-08: Bulk Operations / Contact Groups
- **Patterns:** Composite (`ContactComponent` → `ContactLeaf`, `ContactGroup`)
- **Concepts:** Recursive tree, bulk soft-delete, sub-groups

## UC-09: Search Contacts
- **Patterns:** Specification (`Specification<T>` → `NameSpecification`, `PhoneSpecification`, `EmailSpecification`, `ContactTypeSpecification`)
- **Concepts:** Composable AND/OR/NOT criteria, Stream filtering

## UC-10: Advanced Filtering & Sorting
- **Patterns:** Strategy (`FilterStrategy` → `DateAddedFilter`, `ContactTypeFilter`, `HasPhoneFilter`; `SortStrategy` → `SortByName`, `SortByDateAdded`, `SortByType`)
- **Concepts:** Swappable filter/sort algorithms, combined filter+sort pipeline, Comparator, functional interfaces

### Strategy Pattern Details
- `FilterStrategy` — interface: `filter(List<Contact>) → List<Contact>`
- `SortStrategy` — interface: `getComparator() → Comparator<Contact>`
- Filters: by type, by date added, has phone number
- Sorts: by name (A-Z), by date (newest first), by type
- Combined: filter first, then sort

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
├── groups/          # UC-08: Composite pattern
├── search/          # UC-09: Specification pattern
└── filtersort/      # UC-10: Strategy pattern
```

### How to Run
```bash
javac -d out -sourcepath src src/com/mycontacts/Main.java
java -cp out com.mycontacts.Main
```
