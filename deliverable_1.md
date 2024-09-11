<div id="user-content-title">

# Chicago public library database design

- Kels Cavin, Peter Capuzzi, Andrew Chang-DeWitt
- CS 425, Fall 2024
- Sept. 13, 2024

</div>

## Entity Relationship Diagram

![Entity relationship diagram, using Chen notation](erd.svg)

## Relational Schema

The main entities, including all necessary data for books & where they're stored:

1. `Book(id, isbn, title, genre_id, fiction, edition, pub_date)`
1. `Author(id, first_name, last_name, about)`
1. `Genre(id, name)`
1. `Subject(id, name)`
1. `Language(id, name)`
1. `Copy(id, book_id, branch_id)`
1. `Cardholder(id, card_num, first_name, last_name, password, street_addr, zip, phone, email)`
1. `Branch(id, name, street_addr, zip)`

The following bridge entities are used to handle checking borrowed books in/out or placing/modifying hold requests, as well as any other many-to-many relationships:

1. `Borrow(copy_id, cardholder_id, out, in, due_date)`
1. `Holds(timestamp, book_id, cardholder_id, status)`
1. `AuthorWriteBook(book_id, author_id)`
1. `SubjectDescribeBook(book_id, subject_id)`
1. `LanguageWrittenInBook(book_id, language_id)`

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
