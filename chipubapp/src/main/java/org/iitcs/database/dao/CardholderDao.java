package org.iitcs.database.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.database.connection.ConnectionWrapper;
import org.iitcs.database.dao.models.Cardholder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.iitcs.database.QueryConstants.*;
import static org.iitcs.util.Constants.LOGIN_ERROR_CODE;
import static org.iitcs.util.Util.substringByRegex;

public class CardholderDao implements IDao{
    Connection connection;
    private static final Logger LOGGER = LogManager.getLogger(BookDao.class);

    public CardholderDao() throws InstantiationException {
        connection = ConnectionWrapper.getInstance().getConnection();
    }
    @Override
    public Optional<Cardholder> get(long id) {
        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_CARDHOLDER)){
            ps.setLong(1,id);
            ResultSet cardholder = ps.executeQuery();
            while(cardholder.next()){
                LOGGER.info("Cardholder with id " + id + " searched for.");
                return Optional.of(createCardholderFromResultSet(cardholder));
            }
        }catch(SQLException e){
            //do something
        }
        return Optional.empty();
    }

    private Cardholder createCardholderFromResultSet(ResultSet cardholder) throws SQLException {
        //TODO set their address and phone numbers as well
        return new Cardholder(
                cardholder.getLong(1),
                cardholder.getString(2),
                cardholder.getString(3),
                cardholder.getString(4),
                null,
                cardholder.getString(12)
        );
    }

    @Override
    public List getAll() {
        return null;
    }
    public boolean validateCredentials(String username, String password){
        Long chid = getChIDFromUsername(username);
        String lastName = getLastNameFromUsername(username);

        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_CARDHOLDER_PASSWORD)){
            ps.setLong(1,chid);
            ps.setString(2,lastName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString(PASSWORD_COLUMN_NAME).equals(password)){
                    return true;
                }
            }

        }catch(SQLException e){
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Login attempt failed for: ".concat(username).concat(", ").concat(password));
        return false;
    }
    public Long getChIDFromUsername(String username){
        String chid = substringByRegex("([0-9]+)", username);
        if(chid!=null){
            return Long.parseLong(chid);
        }
        return LOGIN_ERROR_CODE;
    }
    public String getLastNameFromUsername(String username){
        String name = substringByRegex("([aA-zZ]+)", username);
        if(name!=null){
            return name;
        }
        return LOGIN_ERROR_CODE.toString();
    }
    @Override
    public void save(Object item) {

    }

    @Override
    public void update(Object o, String[] parameters) {

    }

    @Override
    public void delete(Object o, String[] parameters) {

    }
}
