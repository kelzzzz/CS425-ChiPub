package org.iitcs.database.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.database.connection.ConnectionWrapper;
import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;
import org.iitcs.database.dao.models.CardholderAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.iitcs.database.QueryConstants.*;
import static org.iitcs.util.Constants.LOGIN_ERROR_CODE;
import static org.iitcs.util.Util.generatePassword;
import static org.iitcs.util.Util.substringByRegex;

public class CardholderDao implements IDao{
    Connection connection;
    BookDao bd;
    private static final Logger LOGGER = LogManager.getLogger(CardholderDao.class);

    public CardholderDao() throws InstantiationException {
        bd = new BookDao();
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
            LOGGER.error(e.getMessage());
            LOGGER.error("Failed to find cardholder with id " + id);
        }
        return Optional.empty();
    }

    @Override
    public ArrayList<Cardholder> search(String searchTerm){
        ArrayList<Cardholder> ret = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(SEARCH_FOR_CARDHOLDER)){
            for(int i = 1; i < CARDHOLDER_SEARCH_PARAMETER_COUNT; i++){
                ps.setString(i,searchTerm);
            }

            ResultSet chs = ps.executeQuery();

            while(chs.next()){
                ret.add(createCardholderFromResultSet(chs));
            }

        }catch(SQLException e){
            LOGGER.info(e.getMessage());
        }
        return ret;
    }
    private Cardholder createCardholderFromResultSet(ResultSet cardholder) throws SQLException {
        //TODO set their phone numbers as well
        long chid = cardholder.getLong(1);
        CardholderAddress addr = createAddressFromResultSet(cardholder);

        Cardholder ch = new Cardholder(
                chid,
                cardholder.getString(2),
                cardholder.getString(3),
                cardholder.getString(4),
                addr,
                cardholder.getString(12)
        );

        setCardholderHoldsAndCheckOutIds(chid, ch);

        return ch;
    }

    private void setCardholderHoldsAndCheckOutIds(long chid, Cardholder ch) {
        ArrayList<Book> holds = new ArrayList<>();
        ArrayList<Long> holdBookIds = new ArrayList<>();

        ArrayList<Book> checkOuts = new ArrayList<>();
        ArrayList<Long> checkOutBookIds = new ArrayList<>();

        populateCardholderHoldIds(chid, holds, holdBookIds);
        populateCardholderCheckOutIds(chid, checkOuts, checkOutBookIds);

        ch.setHolds(holds);
        ch.setCheckOuts(checkOuts);
    }

    private void populateCardholderCheckOutIds(long chid, ArrayList<Book> checkOuts, ArrayList<Long> checkOutBookIds) {
        try(PreparedStatement ps = connection.prepareStatement(GET_CHECKOUT_BOOK_IDS_BY_CARDHOLDER_ID)) {
            ps.setLong(1, chid);
            ps.setLong(2, chid);
            ResultSet checkOutRs = ps.executeQuery();
            while (checkOutRs.next()) {
                checkOutBookIds.add(checkOutRs.getLong(1));
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
        }

        for(Long id : checkOutBookIds){
            checkOuts.add(bd.get(id).get());
        }
    }

    private void populateCardholderHoldIds(long chid, ArrayList<Book> holds, ArrayList<Long> holdBookIds) {
        try(PreparedStatement ps = connection.prepareStatement(GET_HOLD_BOOK_IDS)) {
            ps.setLong(1, chid);
            ps.setString(2, statusMapping.get(Status.PENDING));
            ResultSet holdRs = ps.executeQuery();
            while (holdRs.next()) {
                holdBookIds.add(holdRs.getLong(1));
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
        }

        for(Long id : holdBookIds){
            holds.add(bd.get(id).get());
        }
    }

    private static CardholderAddress createAddressFromResultSet(ResultSet cardholder) throws SQLException {
        CardholderAddress addr = new CardholderAddress(
                cardholder.getString(6),
                cardholder.getString(7),
                cardholder.getString(8),
                cardholder.getString(9),
                cardholder.getString(10),
                cardholder.getString(11)
        );
        return addr;
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
    public boolean save(Object item) {
        Cardholder saveWith = (Cardholder) item;
        try(PreparedStatement ps = connection.prepareStatement(INSERT_NEW_CARDHOLDER)){

            ps.setLong(1,saveWith.getChid());
            ps.setString(2,saveWith.getCardNum());
            ps.setString(3,saveWith.getFirstName());
            ps.setString(4,saveWith.getLastName());
            ps.setString(5,generatePassword());
            ps.setString(6,saveWith.getAddress().getAddrNum());
            ps.setString(7,saveWith.getAddress().getAddrStrt());
            ps.setString(8,saveWith.getAddress().getAddrApt());
            ps.setString(9,saveWith.getAddress().getAddrCity());
            ps.setString(10,saveWith.getAddress().getAddrState());
            ps.setString(11,saveWith.getAddress().getAddrZip());
            ps.setString(12,saveWith.getEmail());

            int i = ps.executeUpdate();
            if(i>0){
                LOGGER.info("New cardholder with id ".concat(String.valueOf(saveWith.getChid()).concat(" was inserted.")));
                return true;
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean update(Object o, String[] parameters) {
        Cardholder updateWith = (Cardholder) o;
        try(PreparedStatement ps = connection.prepareStatement(UPDATE_CARDHOLDER_INFORMATION)){
            ps.setString(1,updateWith.getFirstName());
            ps.setString(2,updateWith.getLastName());
            ps.setString(3,updateWith.getAddress().getAddrNum());
            ps.setString(4,updateWith.getAddress().getAddrStrt());
            ps.setString(5,updateWith.getAddress().getAddrApt());
            ps.setString(6,updateWith.getAddress().getAddrCity());
            ps.setString(7,updateWith.getAddress().getAddrState());
            ps.setString(8,updateWith.getAddress().getAddrZip());
            ps.setString(9,updateWith.getEmail());

            ps.setLong(10,updateWith.getChid());

            int i = ps.executeUpdate();
            if(i>0){
                LOGGER.info("Cardholder with id ".concat(String.valueOf(updateWith.getChid()).concat(" was updated.")));
                return true;
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean delete(Object o, String[] parameters) {
        Cardholder toDelete = (Cardholder) o;
        try(PreparedStatement ps = connection.prepareStatement(DELETE_CARDHOLDER)){
            ps.setLong(1,toDelete.getChid());
            int i = ps.executeUpdate();
            if(i>0){
                LOGGER.info("Cardholder with id ".concat(String.valueOf(toDelete.getChid()).concat(" was deleted.")));
                return true;
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
            return false;
        }
        return false;
    }
}
