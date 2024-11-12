package org.iitcs.database;

public class QueryConstants {

    public static final String BOOK_CREATE_TEMPORARY_INDEX_QUERY = "{call CreateMasterBookIndex()}";
    public static final String CARDHOLDER_CREATE_TEMPORARY_INDEX_QUERY = "{call CreateCardholderTableWithPhone()}";

    public static final String SEARCH_BOOK_MASTER_INDEX_TABLE = "SELECT * FROM MasterBookIndex WHERE " +
            "isbn = ?" +
            " OR title LIKE CONCAT( '%',?,'%')" +
            " OR author_last_name LIKE CONCAT( '%',?,'%') " +
            " OR author_first_name LIKE CONCAT( '%',?,'%')" +
            " OR genre LIKE CONCAT( '%',?,'%')" +
            " OR language LIKE CONCAT( '%',?,'%')" +
            " OR subject LIKE CONCAT( '%',?,'%')";
    public static final String SQL_USE_SCHEMA_QUERY = "USE chipub";
    public static final String SQL_SELECT_CARDHOLDER = "SELECT * FROM cardholder WHERE chid = ?";
    public static final String SQL_SELECT_CARDHOLDER_PASSWORD = "SELECT password FROM cardholder WHERE chid = ? AND last_name = ?";
    public static final String PASSWORD_COLUMN_NAME = "password";

}
