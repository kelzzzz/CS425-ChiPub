package org.iitcs.database;

public class QueryConstants {

    public static final String BOOK_CREATE_TEMPORARY_INDEX_QUERY = "{call CreateMasterBookIndex()}";
    public static final String CARDHOLDER_CREATE_TEMPORARY_INDEX_QUERY = "{call CreateCardholderTableWithPhone()}";

    public static final String SEARCH_BOOK_MASTER_INDEX_TABLE = "select * from MasterBookIndex where " +
            "isbn = ?" +
            " or title like CONCAT( '%',?,'%')" +
            " or author_last_name like CONCAT( '%',?,'%') " +
            " or author_first_name like CONCAT( '%',?,'%')" +
            " or genre like CONCAT( '%',?,'%')" +
            " or language like CONCAT( '%',?,'%')" +
            " or subject like CONCAT( '%',?,'%')";
    public static final String SQL_USE_SCHEMA_QUERY = "use chipub";

}
