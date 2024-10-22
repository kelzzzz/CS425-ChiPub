package org.iitcs.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.util.PropertiesManager;

import java.io.IOException;
import java.util.Properties;

import org.iitcs.util.Constants;
import org.iitcs.util.Constants.CRUD;
public class QueriesManager {
    private static final Logger LOGGER = LogManager.getLogger(QueriesManager.class);
    private Constants c;
    private final Properties queries = new Properties();
    private static QueriesManager instance;

    /**
     * QueriesManager is a singleton.
     * Use getInstance() to access global properties
     */
    public static synchronized QueriesManager getInstance(){
        if(instance != null){
            return instance;
        }
        return new QueriesManager();
    }
    private QueriesManager(){
        try{
            this.queries.load(PropertiesManager.class.getResourceAsStream(c.QUERY_PROPERTIES_FILE));
        }
        catch(IOException e){
            LOGGER.error(e.getMessage());
            LOGGER.error("Query properties file: "
                    .concat(c.QUERY_PROPERTIES_FILE)
                    .concat(" not found in root directory.")
            );
        }
    }

    public String get(CRUD crud, String tag){
        switch(crud){
            case CREATE:
                return queries.getProperty(c.QUERIES_KEY_CREATE.concat(tag));
            case READ:
                return queries.getProperty(c.QUERIES_KEY_READ.concat(tag));
            case UPDATE:
                return queries.getProperty(c.QUERIES_KEY_UPDATE.concat(tag));
            case DELETE:
                return queries.getProperty(c.QUERIES_KEY_DELETE.concat(tag));
        }
        return null;
    }

}
