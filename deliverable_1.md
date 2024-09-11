<div id="user-content-title">

# Chicago public library database design

- Kels Cavin, Peter Capuzzi, Andrew Chang-DeWitt
- CS 425, Fall 2024
- Sept. 13, 2024

</div>

## TODO:

- [ ] ERD
  - [x] get all E, R, & attrs into diagram
  - [ ] fix cardinality
  - [ ] include participation
  - [ ] fix arrowless link problem mermaid-js/mermaid/issues/5813
  - [ ] review for edits/corrections
- [ ] Relational Schema
  - [x] draft
  - [ ] review/make names match ERD
- [ ] Business Rules

## Entity Relationship Diagram

![Entity relationship diagram, using Chen notation](erd.svg)

## Relational Schema

The main entities, including all necessary data for books, where they're stored, & checking them in/out or placing holds is included in the following entities:

```
Book(id, isbn, title, genre_id, fiction, edition, pub_date)
Author(id, first_name, last_name, about)
Genre(id, name)
Subject(id, name)
Language(id, name)
Copy(id, book_id, branch_id)
Cardholder(id, card_num, first_name, last_name, password, street_addr, zip, phone, email)
Branch(id, name, street_addr, zip)
```

The following bridge entities are used to handle most many:many relationships:

```
Borrow(copy_id, cardholder_id, out, in, due_date)
Holds(timestamp, book_id, cardholder_id, status)
AuthorWriteBook(book_id, author_id)
SubjectDescribeBook(book_id, subject_id)
LanguageWrittenInBook(book_id, language_id)
```

## Business Rules

... insert rules here when done ...
