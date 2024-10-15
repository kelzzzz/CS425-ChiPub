-- get all overdue books for a given cardholder
select b.title, a.first_name, a.last_name, (cc.checked_out + interval 2 week) as due_date
from cardholder as ch
join cardholder_copy as cc on ch.chid=cc.cardholder_id
join copy as c on cc.copy_id=c.cid
join book as b on c.book_id=b.bid
join author_book as ab on b.bid=ab.book_id
join author as a on a.aid=ab.author_id
where cc.checked_out < now() - interval 2 week
and unix_timestamp(cc.checked_in) != 0
and ch.chid = 23;
