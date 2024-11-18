package org.iitcs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.iitcs.database.QueryConstants;
import org.iitcs.database.connection.ConnectionWrapper;
import org.iitcs.controller.ApplicationContext;
import org.iitcs.controller.state.concretestate.LoginState;
import org.iitcs.util.Constants;
import org.iitcs.util.PropertiesLoader;

import java.sql.SQLException;

import static org.iitcs.util.Constants.SYSTEM_PROPERTY_KEY;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static ConnectionWrapper cw;

    public static void main(String... args) {
        String propertiesFileName = getPropertiesFileNameFromSystemProperties();
        PropertiesLoader pl = PropertiesLoader.getInstance(propertiesFileName);
        QueryConstants qc = new QueryConstants();
        try{
            //in the run command, pass in a properties file name with -Dprops=myfile.properties
            ConnectionWrapper.initializeConnectionWrapper(pl.getDbJdbcUrl(), pl.getJdbcAdminCredentials());
            ConnectionWrapper.getInstance().connect();
        }catch(InstantiationException | SQLException e){
            LOGGER.error("Something went wrong trying to connect to database.");
            LOGGER.error(e.getMessage());
            throw new RuntimeException();
        }
        ApplicationContext context = ApplicationContext.getInstance();
        context.setState(new LoginState(context));
        addConnectionClosingShutdownHook();
    }

    private static String getPropertiesFileNameFromSystemProperties() {
        String propertiesFileName = System.getProperty(SYSTEM_PROPERTY_KEY);

        if(noPropertiesFound(propertiesFileName)){
            LOGGER.info("No properties file found -- switching to default.");
            propertiesFileName = Constants.DEFAULT_PROPERTIES_FILE;
        }else if(propertiesFileNameInvalid(propertiesFileName)){
            throw new RuntimeException("Invalid properties file passed to JVM.");
        }
        return propertiesFileName;
    }

    private static void addConnectionClosingShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    ConnectionWrapper.getInstance().releaseConnection();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Shutdown hook"));
    }

    public static boolean noPropertiesFound(String pfn){
        return pfn == null;
    }
    public static boolean propertiesFileNameInvalid(String pfn){
        return !pfn.contains(".properties");
    }
}
