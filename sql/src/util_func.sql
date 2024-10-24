use chipub;

##################
#  Stored Procs  #
##################
#Stored proc to look up books by author last name
DELIMITER //
CREATE PROCEDURE SelectBookByAuthorLastName (a_lname varchar(510))
BEGIN
CREATE TEMPORARY TABLE booktempCLI_AuthLName AS
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

DELIMITER //
CREATE PROCEDURE SelectBookByAuthorFirstName (a_fname varchar(510))
BEGIN
CREATE TEMPORARY TABLE booktempCLI_AuthFName AS
SELECT bid, isbn, title 
FROM book, (
	SELECT book_id 
    FROM author_book, (
		SELECT aid FROM author
        WHERE author.first_name = a_fname
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

#Stored proc to check out a book and update its checked_out timestamp
DELIMITER //
CREATE PROCEDURE Check_out(copid int, chid int)
BEGIN
INSERT INTO cardholder_copy VALUES(
	copid, chid, current_timestamp(), null
);
END //
DELIMITER ;

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
