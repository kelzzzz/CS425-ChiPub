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

1. An Author can write one or more Books;
   however, every Book is written by at least one Author.
2. One Genre can contain one or more Books;
   however, every Book belongs to exactly one Genre.
3. One or more Subjects may describe a Book.
   One or more Books may be described by a Subject.
4. One or more Languages can be used to write a Book;
   a Book must be written in at least one Language.
5. A Book may be requested for Hold by one or more Cardholder.
   A Cardholder may request a Hold for one or more Book.
6. A book Book may have one or more Copy;
   however, every Copy has exactly one Book.
7. A Copy can have one or more Borrow records for one or more Cardholder.
   A Cardholder can have one or more Borrow records for one or more Copy.
8. A Copy must be located at exactly one Branch;
   however; a Branch may have one or more Copy.
