package org.iitcs.database;


import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class QueryConstants {
    public static String DELETE_CARDHOLDER;
    public static String INSERT_NEW_CARDHOLDER;
    public static String UPDATE_CARDHOLDER_INFORMATION;
    public static String PASSWORD_COLUMN_NAME;
    public static String SQL_SELECT_CARDHOLDER_PASSWORD;
    public static String SQL_SELECT_CARDHOLDER;
    public static String SQL_USE_SCHEMA_QUERY;
    public static String SEARCH_BOOK_MASTER_INDEX_TABLE;
    public static String GET_CHECKED_OUT_COPY_IDS_FOR_BOOK;
    public static String GET_AVAILABLE_COPY_IDS_FOR_BOOK;
    public static String GET_CHECKOUT_BOOK_IDS_BY_CARDHOLDER_ID;
    public static String SEARCH_FOR_CARDHOLDER;
    public static String BOOK_CREATE_TEMPORARY_INDEX_QUERY;
    public static String SQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String SELECT_FROM_MASTERBOOKINDEX;
    public static String PLACE_HOLD;
    public static String CANCEL_HOLD;
    public static String CHECK_OUT;
    public static String CHECK_IN;
    public static String GET_HOLD_BOOK_IDS;
    public static int BOOK_SEARCH_PARAMETER_COUNT = 20;
    public static int CARDHOLDER_SEARCH_PARAMETER_COUNT = 18;
    Properties queryProps = new Properties();

    public QueryConstants() {
        try {
            this.queryProps.load(QueryConstants.class.getResourceAsStream("/constant_queries.properties"));
            readPropertiesIntoVariables();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readPropertiesIntoVariables() {
        BOOK_CREATE_TEMPORARY_INDEX_QUERY = queryProps.getProperty("sql.storedproc.bookindex");
        SEARCH_FOR_CARDHOLDER=queryProps.getProperty("sql.select.ranked.cardholder");
        GET_CHECKOUT_BOOK_IDS_BY_CARDHOLDER_ID=queryProps.getProperty("sql.select.ownercheckouts");
        GET_AVAILABLE_COPY_IDS_FOR_BOOK=queryProps.getProperty("sql.select.checkedincopyids");
        GET_CHECKED_OUT_COPY_IDS_FOR_BOOK=queryProps.getProperty("sql.select.checkedoutcopyids");
        SEARCH_BOOK_MASTER_INDEX_TABLE=queryProps.getProperty("sql.select.rankedbooksearch");

        SQL_USE_SCHEMA_QUERY=queryProps.getProperty("sql.use.chipub");
        SQL_SELECT_CARDHOLDER=queryProps.getProperty("sql.select.cardholder");
        SQL_SELECT_CARDHOLDER_PASSWORD=queryProps.getProperty("sql.select.password");
        PASSWORD_COLUMN_NAME=queryProps.getProperty("sql.password.column.name");
        UPDATE_CARDHOLDER_INFORMATION=queryProps.getProperty("sql.update.cardholder");
        INSERT_NEW_CARDHOLDER=queryProps.getProperty("sql.insert.cardholder");
        DELETE_CARDHOLDER=queryProps.getProperty("sql.delete.cardholder");

        SELECT_FROM_MASTERBOOKINDEX =queryProps.getProperty("sql.select.books");
        PLACE_HOLD=queryProps.getProperty("sql.place.hold");
        CANCEL_HOLD=queryProps.getProperty("sql.cancel.hold");

        CHECK_OUT=queryProps.getProperty("sql.check.out");
        CHECK_IN=queryProps.getProperty("sql.check.in");

        GET_HOLD_BOOK_IDS=queryProps.getProperty("sql.get.hold.book.ids");
    }

    public enum Status{
        PENDING, FULLFILLED, CANCELLED
    }
    public static HashMap<Status, String> statusMapping = new HashMap<>(){{
        put(Status.PENDING, "pending");
        put(Status.FULLFILLED, "fulfilled");
        put(Status.CANCELLED, "cancelled");
    }};
}
