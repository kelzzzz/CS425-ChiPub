package org.iitcs.database.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.database.QueryConstants;
import org.iitcs.database.connection.ConnectionWrapper;
import org.iitcs.database.dao.models.Book;
import org.iitcs.database.dao.models.Cardholder;

import java.sql.*;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.iitcs.database.QueryConstants.*;

public class BookDao implements IDao{
    private Connection connection;
    private static final Logger LOGGER = LogManager.getLogger(BookDao.class);
    public BookDao() throws InstantiationException {
        connection = ConnectionWrapper.getInstance().getConnection();

        try(PreparedStatement ps = connection.prepareStatement(BOOK_CREATE_TEMPORARY_INDEX_QUERY)){
            ps.execute();
            if(ps.getUpdateCount() > 0){
                LOGGER.info("Book index was retrieved.");
            }
        }catch(SQLException e){
            LOGGER.error(e.getMessage());
        }

    }
    @Override
    public Optional<Book> get(long id) {
        try(PreparedStatement ps = connection.prepareStatement(SELECT_FROM_MASTERBOOKINDEX)){
            ps.setLong(1, id);
            ResultSet books = ps.executeQuery();
            while(books.next()){
                LOGGER.info("Book with id " + id + " searched for.");
                return Optional.of(createBookFromResultSet(books));
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
            LOGGER.error("Failed to find book with id " + id);
        }
        return Optional.empty();
    }
    @Override
    public ArrayList<Book> search(String searchTerm){
        ArrayList<Book> ret = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(SEARCH_BOOK_MASTER_INDEX_TABLE)){
            for(int i = 1; i < BOOK_SEARCH_PARAMETER_COUNT; i++){
                ps.setString(i, searchTerm);
            }

            ResultSet books = ps.executeQuery();

            while(books.next()){
                ret.add(createBookFromResultSet(books));
            }

        }catch(SQLException e){
            LOGGER.info(e.getMessage());
        }
        return ret;
    }

    public boolean placeHold(Book book, Cardholder cardholder){
        try(PreparedStatement ps  = connection.prepareStatement(PLACE_HOLD)){
            Timestamp timestamp = new Timestamp(ZonedDateTime
                    .now(ZoneId.systemDefault())
                    .with(LocalTime.now())
                    .toInstant()
                    .toEpochMilli());
            ps.setTimestamp(1,timestamp);
            ps.setLong(2,book.getBookId());
            ps.setLong(3, cardholder.getChid());
            ps.setString(4,QueryConstants.statusMapping.get(QueryConstants.Status.PENDING));
            int i = ps.executeUpdate();
            if(i > 0){
                return true;
            }else{
                LOGGER.info("Placing hold for book ID:".concat(String.valueOf(book.getBookId())).concat("failed."));
                return false;
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
            return false;
        }
    }
    public boolean cancelHold(Book book, Cardholder cardholder) {
        try (PreparedStatement ps = connection.prepareStatement(CANCEL_HOLD)) {
            ps.setString(1, QueryConstants.statusMapping.get(QueryConstants.Status.CANCELLED));
            ps.setLong(2, book.getBookId());
            ps.setLong(3, cardholder.getChid());
            int i = ps.executeUpdate();
            if (i > 0) {
                return true;
            } else {
                LOGGER.info("Cancelling hold for book ID:".concat(String.valueOf(book.getBookId())).concat("failed."));
                return false;
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public boolean checkOut(long copyId, long cardHolderId){
        try(PreparedStatement ps = connection.prepareStatement(CHECK_OUT)){
            ps.setLong(1, copyId);
            ps.setLong(2, cardHolderId);
            ps.executeUpdate();
            LOGGER.info("Checked out book with copy ID ".concat(String.valueOf(copyId)));
            return true;
        }
        catch(SQLException e){
            LOGGER.info("Failed to check out book with copy ID ".concat(String.valueOf(copyId)));
            return false;
        }
    }

    public boolean checkIn(long copyId, long cardHolderId){
        try(PreparedStatement ps = connection.prepareStatement(CHECK_IN)){
            ps.setLong(1, copyId);
            ps.setLong(2, cardHolderId);
            int i = ps.executeUpdate();
            if(i > 0){
                LOGGER.info("Checked in book with copy ID ".concat(String.valueOf(copyId)));
                return true;
            }else{
                LOGGER.info("Failed to check in book with copy ID ".concat(String.valueOf(copyId)));
                return false;
            }
        }catch(SQLException e){
            LOGGER.error(e.getMessage());
            return false;
        }
    }
    public ArrayList<Long> getAvailableCopyIdsForBook(Book book){
        try(PreparedStatement ps = connection.prepareStatement(GET_AVAILABLE_COPY_IDS_FOR_BOOK)){
            ps.setLong(1,book.getBookId());
            ResultSet rs = ps.executeQuery();
            ArrayList<Long> ids = new ArrayList<>();
            while(rs.next()){
                ids.add(rs.getLong(1));
            }
            return ids;
        }catch(SQLException e){
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }
    public ArrayList<Long> getCheckedOutCopyIdsForBook(Book book){
        try(PreparedStatement ps = connection.prepareStatement(GET_CHECKED_OUT_COPY_IDS_FOR_BOOK)){
            ps.setLong(1,book.getBookId());
            ResultSet rs = ps.executeQuery();
            ArrayList<Long> ids = new ArrayList<>();
            while(rs.next()){
                ids.add(rs.getLong(1));
            }
            return ids;
        }catch(SQLException e){
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }
    @Override
    public List getAll() {
        return null;
    }

    @Override
    public boolean save(Object item) {
        //TODO Adding new books
        return false;
    }

    @Override
    public boolean update(Object o, String[] parameters) {
        //TODO Updating books
        return false;
    }

    @Override
    public boolean delete(Object o, String[] parameters) {
        //TODO Deleting books
        return false;
    }

    private Book createBookFromResultSet(ResultSet rs) throws SQLException {
        return new Book(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8));
    }
}
