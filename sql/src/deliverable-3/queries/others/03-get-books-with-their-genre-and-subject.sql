select title, isbn, (
    select genre.name
    from genre
    where genre.gid=book.genre_id
) as genre, (
    select subject.name
    from subject
    join subject_book
    on subject.sid=subject_book.subject_id
    where subject_book.book_id=book.bid
) as subject
from book;
