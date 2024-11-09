package org.iitcs.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static PropertiesLoader instance = null;
    private static final Logger LOGGER = LogManager.getLogger(PropertiesLoader.class);
    private Constants c;
    private final Properties properties = new Properties();
    private String dbJdbcUrl;
    private String dbJdbcSchema;
    private String dbAdminUsername;
    private String dbAdminPassword;

    /**
     * PropertiesManager is a singleton.
     * Use getInstance() to access global properties
     */
    public static synchronized PropertiesLoader getInstance(){
        if(instance != null){
            return instance;
        }
        return new PropertiesLoader();
    }
    private PropertiesLoader(){
        /*TODO: Should be able to pass a custom properties file in the JVM args*/
        try{
            this.properties.load(PropertiesLoader.class.getResourceAsStream(c.DEFAULT_PROPERTIES_FILE));
            readPropertiesIntoVariables();
        }
        catch(IOException e){
            LOGGER.error(e.getMessage());
            LOGGER.error("Default properties file: "
                    .concat(c.DEFAULT_PROPERTIES_FILE)
                    .concat(" not found in root directory.")
            );
        }
    }

    private void readPropertiesIntoVariables(){
        this.dbJdbcUrl = this.properties.getProperty("db.jdbc.url");
        this.dbJdbcSchema = this.properties.getProperty("db.jdbc.schema");
        this.dbAdminUsername = this.properties.getProperty("db.admin.username");
        this.dbAdminPassword = this.properties.getProperty("db.admin.password");
    }

    public String getDbJdbcUrl() { return dbJdbcUrl;}
    public String getDbJdbcSchema() { return dbJdbcSchema;}
    public Properties getJdbcAdminCredentials(){
        Properties props = new Properties();
        props.put("user", this.dbAdminUsername);
        props.put("password", this.dbAdminPassword);
        return props;
    }
    public String getDbAdminUsername() { return dbAdminUsername; }
    public String getDbAdminPassword() { return dbAdminPassword;}
}
