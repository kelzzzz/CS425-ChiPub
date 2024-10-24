package org.iitcs.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.Main;
import org.iitcs.util.Constants.CRUD;
import org.iitcs.util.PropertiesLoader;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Properties;

public class QueryExecutor {

    /*Update queries can give back a state of 0 (nothing changes), 1 (update success), or -1 (something went wrong)*/
    private ChiPubConnection conn;
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final PropertiesLoader props = PropertiesLoader.getInstance();
    private static final QueryLoader queries = QueryLoader.getInstance();
    public QueryExecutor(){
        Properties dbCredentials = new Properties();
        dbCredentials.put("user", props.getDbAdminUsername());
        dbCredentials.put("password", props.getDbAdminPassword());

        conn = new ChiPubConnection(props.getDbJdbcUrl(), props.getDbJdbcSchema(), dbCredentials);
    }

    public int executeCheckIn(int cid, int chid){
        String q = queries.get(CRUD.UPDATE, "checkin");
        try(CallableStatement cs = conn.getChiPubConnectionObj().prepareCall(q)){
            cs.setInt(1, cid);
            cs.setInt(2,chid);
            cs.execute();
            LOGGER.info("Check in occurred.");
            if(cs.getUpdateCount() == 0 ){
                return 0; //update ran successfully but nothing was checked in
            }
            return 1;
        }catch(SQLException e){
            LOGGER.error(e.getMessage());
        }
        return -1;
    }

    public int executeCheckOut(int cid, int chid){
        String q = queries.get(CRUD.UPDATE, "checkout");
        try(CallableStatement cs = conn.getChiPubConnectionObj().prepareCall(q)){
            cs.setInt(1, cid);
            cs.setInt(2,chid);
            cs.execute();
            LOGGER.info("Check out occurred.");
            if(cs.getUpdateCount() == 0){
                return 0; //update ran successfully but nothing was checked out
            }
            return 1;
        }catch(SQLException e){
            LOGGER.error(e.getMessage());
        }
        return -1;
    }
}
