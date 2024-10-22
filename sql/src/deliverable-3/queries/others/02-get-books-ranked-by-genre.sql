select title, isbn, (
    select genre.name
    from genre
    where book.genre_id=genre.gid
) as genre,
dense_rank() over(order by genre_id) as ranking
from book
order by ranking asc;
