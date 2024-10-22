package org.iitcs.util;

public class Constants {
    public static final String QUERY_PROPERTIES_FILE = "/queries.properties";
    public static final String DEFAULT_PROPERTIES_FILE = "/default_properties.properties";
    public static final String QUERIES_KEY_CREATE = "sql.create.";
    public static final String QUERIES_KEY_READ = "sql.read.";
    public static final String QUERIES_KEY_UPDATE = "sql.update.";
    public static final String QUERIES_KEY_DELETE = "sql.delete.";
    public enum CRUD{
        CREATE, READ, UPDATE, DELETE
    }
}
