package org.iitcs.database;

import java.util.HashMap;

public class QueryConstants {

    public static final String BOOK_CREATE_TEMPORARY_INDEX_QUERY = "{call CreateMasterBookIndex()}";
    public static final String CARDHOLDER_CREATE_TEMPORARY_INDEX_QUERY = "{call CreateCardholderTableWithPhone()}";

    public static final String GET_CHECKOUT_BOOK_IDS_BY_CARDHOLDER_ID = "SELECT book.bid, copy.cid, \n" +
            "\t(SELECT cardholder_id FROM cardholder_copy WHERE copy_id = copy.cid \n" +
            "    AND cardholder_id=?\n" +
            "    AND checked_in is null) as checkedOutTo\n" +
            "FROM book \n" +
            "INNER JOIN copy \n" +
            "ON book.bid = copy.book_id\n" +
            "Having checkedOutTo = ?;";
    public static final String GET_AVAILABLE_COPY_IDS_FOR_BOOK = "SELECT copy.cid as availableCopyIds\n" +
            "FROM book \n" +
            "INNER JOIN copy \n" +
            "ON book.bid = copy.book_id\n" +
            "WHERE book.bid=? "+
            "AND (SELECT checked_in FROM cardholder_copy where copy_id = copy.cid order by checked_out desc limit 1) is not null";

    public static final String GET_CHECKED_OUT_COPY_IDS_FOR_BOOK = "SELECT copy.cid as checkedOutCopyIds\n" +
            "FROM book \n" +
            "INNER JOIN copy \n" +
            "ON book.bid = copy.book_id\n" +
            "WHERE book.bid=? "+
            "AND (SELECT checked_in FROM cardholder_copy where copy_id = copy.cid order by checked_out desc limit 1) is null";
    public static final String SEARCH_BOOK_MASTER_INDEX_TABLE =
            "select *,\n" +
                    "dense_rank() over(order by ((CASE WHEN title = ? THEN 1 ELSE 0 END) +\n" +
                    "(CASE WHEN author_last_name = ? THEN 1 ELSE 0 END) +\n" +
                    "(CASE WHEN author_first_name = ? THEN 1 ELSE 0 END) +\n" +
                    "(CASE WHEN genre = CONCAT(?,char(13)) OR genre = ? THEN 1 ELSE 0 END) +\n" +
                    "(CASE WHEN subject = CONCAT(?,char(13)) OR subject = ? THEN 1 ELSE 0 END) +\n" +
                    "(CASE WHEN language = CONCAT(?,char(13)) OR language = ? THEN 1 ELSE 0 END)) desc) as match_rank\n" +
                    "from MasterBookIndex where isbn = ?\n" +
                    "or title like CONCAT( '%',?,'%')\n" +
                    "or author_last_name like CONCAT( '%',?,'%')\n" +
                    "or author_first_name like CONCAT( '%',?,'%')\n" +
                    "or genre like CONCAT( '%',?,'%')\n" +
                    "or language like CONCAT( '%',?,'%')\n" +
                    "or subject like CONCAT( '%',?,'%')"+
                    "or CONCAT(author_first_name,' ', author_last_name) like CONCAT( '%',?,'%')\n" +
                    "or CONCAT(author_last_name,' ',author_first_name) like CONCAT( '%',?,'%')"+
                    "or CONCAT(author_last_name,', ',author_first_name) like CONCAT( '%',?,'%')"+
                    "order by match_rank, title asc";
    public static final String SQL_USE_SCHEMA_QUERY = "USE chipub";
    public static final String SQL_SELECT_CARDHOLDER = "SELECT * FROM cardholder WHERE chid = ?";
    public static final String SQL_SELECT_CARDHOLDER_PASSWORD = "SELECT password FROM cardholder WHERE chid = ? AND last_name = ?";
    public static final String PASSWORD_COLUMN_NAME = "password";

    public enum Status{
        PENDING, FULLFILLED, CANCELLED;
    }
    public static HashMap<Status, String> statusMapping = new HashMap<>(){{
        put(Status.PENDING, "pending");
        put(Status.FULLFILLED, "fulfilled");
        put(Status.CANCELLED, "cancelled");
    }};
}
