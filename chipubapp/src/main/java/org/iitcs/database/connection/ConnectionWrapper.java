package org.iitcs.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.database.QueryConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionWrapper {
    private static ConnectionWrapper instance;
    private Connection connection;
    private final Properties credentials;
    private final String url;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionWrapper.class);
    private ConnectionWrapper(String url, Properties credentials){
        this.url = url;
        this.credentials = credentials;
        try{
            loadDriver();
        }catch(ClassNotFoundException e){
            LOGGER.error("Driver failed to initialize");
            LOGGER.error(e.getMessage());
        }
    }
    public static void initializeConnectionWrapper(String url, Properties credentials){
        if(instance != null){
            instance.releaseConnection();
        }
        instance = new ConnectionWrapper(url, credentials);
        LOGGER.info("New ConnectionWrapper instance was initialized.");
    }
    public static ConnectionWrapper getInstance() throws InstantiationException {
        if(instance == null){
            LOGGER.error("ConnectionWrapper instance tried to retrieve before initialization method called.");
            throw new InstantiationException("Connection instance was never initialized.");
        }
        return instance;
    }
    private void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        LOGGER.info("Driver loaded.");
    }
    public Connection getConnection(){
        return connection;
    }
    public Properties getCredentials(){
        return this.credentials;
    }
    public boolean connect() throws SQLException {

        LOGGER.info("Attempting to connect to "
                .concat(url)
                .concat(" with credentials: ")
                .concat(credentials.getProperty("user"))
                .concat(", ")
                .concat(credentials.getProperty("password")));

        connection = DriverManager.getConnection(url, credentials);
        PreparedStatement ps = connection.prepareStatement(QueryConstants.SQL_USE_SCHEMA_QUERY);
        ps.execute();
        if(connection.isValid(1)){
            LOGGER.info("Connection successful to: ".concat(url));
            return true;
        }
        LOGGER.error("Connection failure.");
        return false;
    }
    public boolean releaseConnection(){
        try{
            LOGGER.info("Releasing current DB connection.");
            connection.close();
            return true;
        }catch(SQLException e){
            LOGGER.error("Unable to close connection.");
            LOGGER.error(e.getMessage());
            return false;
        }
    }

}
