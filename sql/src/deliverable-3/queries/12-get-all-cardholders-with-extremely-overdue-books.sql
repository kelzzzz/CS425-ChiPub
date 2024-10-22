-- get all cardholders with very overdue books
select ch.chid as id_num,
    ch.first_name as first_name,
    ch.last_name as last_name,
    chp.phone_number as phone_number,
    concat(ch.addr_num, " ", ch.addr_street) as address_first_line,
    ch.addr_apt as address_second_line,
    concat(ch.addr_city, ", ", ch.addr_state, " ", ch.addr_zip) as address_third_line
from cardholder as ch
join cardholder_copy as cc on ch.chid=cc.cardholder_id
join cardholder_phone as chp on ch.chid=chp.cardholder_id
where cc.checked_out < now() - interval 6 week
and unix_timestamp(cc.checked_in) != 0;
