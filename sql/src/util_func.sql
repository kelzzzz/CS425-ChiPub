use chi_pub;

##################
#  Stored Procs  #
##################
#Stored proc to look up books by author last name
DELIMITER //
CREATE PROCEDURE SelectBookByAuthorLastName (a_lname varchar(510))
BEGIN
SELECT bid, isbn, title 
FROM book, (
	SELECT book_id 
    FROM author_book, (
		SELECT aid FROM author
        WHERE author.last_name = a_lname
		) AS author_ids
	WHERE author_book.author_id = author_ids.aid
    ) AS bookidsfromauthor
WHERE book.bid = bookidsfromauthor.book_id;
END //
DELIMITER ;

#CALL SelectBookByAuthorLastName("Lebsack");

#Stored proc to check in a book and update its checked_in timestamp
DELIMITER //
CREATE PROCEDURE Check_in(copid int, chid int)
BEGIN
UPDATE cardholder_copy
	SET checked_in = current_timestamp() 
    WHERE copy_id = copid AND cardholder_id = chid AND checked_in IS NULL;
END //
DELIMITER ;

#CALL Check_in(3811930,0);


##TODO! Stored proc to use the copystatus temp table and get available 
##copies of specific books at specific branches

##################
#  Functions     #
##################


#Function to return the due date-- 2 weeks from the checkout date
DELIMITER //
CREATE FUNCTION calculate_due_date(checkoutdate TIMESTAMP)
RETURNS TIMESTAMP DETERMINISTIC
BEGIN
	RETURN date_add(checkoutdate, interval 2 week);
END//
DELIMITER ;

#SELECT cardholder_id, calculate_due_date(checked_out) AS due_dates FROM cardholder_copy WHERE checked_in IS NULL;
