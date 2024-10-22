-- get all pending holds requested by all cardholders with books overdue
select bc.timestamp, bc.book_id, bc.cardholder_id
from book_cardholder as bc
join cardholder as ch on ch.chid=bc.cardholder_id
join cardholder_copy as cc on ch.chid=cc.cardholder_id
join copy as c on cc.copy_id=c.cid
where cc.checked_out < now() - interval 2 week
and unix_timestamp(cc.checked_in) != 0;
