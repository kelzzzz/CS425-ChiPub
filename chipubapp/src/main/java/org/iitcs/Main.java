package org.iitcs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.iitcs.database.QueryConstants;
import org.iitcs.database.connection.ConnectionWrapper;
import org.iitcs.gui.ApplicationStateManager;
import org.iitcs.util.PropertiesLoader;

import java.sql.SQLException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static ConnectionWrapper cw;

    public static void main(String... args) {
        try{
            PropertiesLoader pl = PropertiesLoader.getInstance();
            QueryConstants qc = new QueryConstants();
            ConnectionWrapper.initializeConnectionWrapper(pl.getDbJdbcUrl(), pl.getJdbcAdminCredentials());
            ConnectionWrapper.getInstance().connect();
        }catch(InstantiationException | SQLException e){
            LOGGER.error("Something went wrong trying to connect to database.");
            LOGGER.error(e.getMessage());
        }
        ApplicationStateManager as = ApplicationStateManager.getInstance();
        as.setState(ApplicationStateManager.GuiState.LOGIN);


        //CommandLine cmd = new CommandLine(new Cli());
        //int exitCode = cmd.execute(args);
        //System.exit(exitCode);
    }
}
