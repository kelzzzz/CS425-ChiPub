package org.iitcs.util;

import java.util.HashMap;

public class Constants {
    public static final String QUERY_PROPERTIES_FILE = "/queries.properties";
    public static final String DEFAULT_PROPERTIES_FILE = "/default_properties.properties";
    public enum CRUD{
        CREATE, READ, UPDATE, DELETE
    }
    public static final HashMap<CRUD, String> QUERY_KEYS = new HashMap<>(){{
        put(CRUD.CREATE, "sql.create.");
        put(CRUD.READ, "sql.read.");
        put(CRUD.UPDATE, "sql.update.");
        put(CRUD.DELETE, "sql.delete.");
    }};
}
