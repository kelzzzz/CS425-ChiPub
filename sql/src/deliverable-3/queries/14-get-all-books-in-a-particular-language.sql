-- get all books in a particular language
select b.title as title,
    a.first_name as author_first_name,
    a.last_name as author_last_name,
    g.name as genre
from book as b
join author_book as ab on ab.book_id=b.bid
join author as a on ab.author_id=a.aid
join genre as g on b.genre_id=g.gid
join language_book as lb on lb.book_id=b.bid
join language as l on l.lid=lb.language_id
where l.name = "amet";
