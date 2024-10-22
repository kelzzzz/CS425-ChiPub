select count(*) as books_written, (
    select author.first_name
    from author
    where author.aid = author_book.author_id) as a_fname,
    (select author.last_name
    from author
    where author.aid = author_book.author_id) as a_lname
from author_book
group by author_id;
