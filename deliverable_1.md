<div id="user-content-title">

# Chicago public library database design

- Kels Cavin, Peter Capuzzi, Andrew Chang-DeWitt
- CS 425, Fall 2024
- Sept. 13, 2024

</div>

## Entity Relationship Diagram

<!--![Entity relationship diagram, using Chen notation](erd.svg)-->

```mermaid
graph LR
    aid(author_id) --- A
    af(first_name) --- A
    al(last_name) --- A
    aa(about) --- A
    A[Author] ~~~ AB{writes} --> A
    gid(genre_id) --- G
    gnm(name) --- G
    G[Genre] --- GB{belong to}
    sid(subject_id) --- S
    sn(name) --- S
    S[Subject] ~~~ SB{belong to} --> S
    lid(lang_id) --- L
    lnm(lang_name) --- L
    L[Language] ~~~ LB{written} --> L
    AB --> B[Book]
    GB ---> B
    SB --> B
    LB --> B
    bt(title) --- B
    bid(book_id) --- B
    bf(fiction) --- B
    be(edition) --- B
    bp(pub_date) --- B
    B --- CB{has}
    C[Copy] ~~~ CB --> C
    C ~~~ CBr{is located at} --> C
    C --- CT{subject of}
    C --- CH{subject of}
    C --- cid(copy_ID)
    C --- cbr(branch)
    CBr --- Br[Branch]
    Br --- brd(branch_id)
    Br --- bn(name)
    Br --- ba(street_addr)
    Br --- bz(zip)
    CT --> T[Transaction]
    CH --> H[Hold]
    T ~~~ TCh{executed by} --> T
    T --- tid(transaction_id)
    T --- tch(cardholder)
    T --- tcp(copy)
    T --- tts(timestamp)
    T --- ttp(type)
    T --- tdt(due_date)
    TCh --- Ch[Cardholder]
    H ~~~ HCh{requested by} --> H
    H --- hid(hold_id)
    H --- hch(cardholder)
    H --- hbk(book)
    H --- hts(timestamp)
    H --- hst(status)
    HCh --- Ch
    Ch --- chid(cardholder_id)
    Ch --- chnm(card_num)
    Ch --- chfn(first_name)
    Ch --- chln(last_name)
    Ch --- chsa(street_address)
    Ch --- chz(zip)
    Ch --- chpw(password)
```

## Relational Schema

The main entities, including all necessary data for books, where they're stored, & checking them in/out or placing holds is included in the following entities:

```
Book(BID, ISBN, title, genre_ID, fiction, edition, pub_date)
Author(AID, first_name, last_name, about)
Genre(GID, name)
Subject(SID, name)
Language(LID, name)
Transactions(TID, timestamp, type, inventory_ID, cardholder_ID, due_date)
Holds(HID, timestamp, book_ID, cardholder_ID, status)
Inventory(IID, book_ID, location_ID)
Cardholder(CID, card_num, first_name, last_name, password, street_addr, zip, phone, email)
Location(LoID, name, street_addr, zip)
```

The following bridge entities are used to handle most many:many relationships:

```
Book_to_Author(book_ID, author_ID)
Book_to_Subject(book_ID, subject_ID)
Book_to_Language(book_ID, language_ID)
```

## Business Rules

... insert rules here when done ...
