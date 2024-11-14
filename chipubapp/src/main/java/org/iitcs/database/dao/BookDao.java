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

import static org.iitcs.database.QueryConstants.BOOK_CREATE_TEMPORARY_INDEX_QUERY;
import static org.iitcs.database.QueryConstants.SEARCH_BOOK_MASTER_INDEX_TABLE;

public class BookDao implements IDao{
    Connection connection;
    private static final Logger LOGGER = LogManager.getLogger(BookDao.class);
    public BookDao() throws InstantiationException {
        connection = ConnectionWrapper.getInstance().getConnection();

        try(PreparedStatement ps = connection.prepareStatement(BOOK_CREATE_TEMPORARY_INDEX_QUERY)){
            ps.execute();
            if(ps.getUpdateCount() > 0){
                System.out.println("Book search temp table was created.");
            }
        }catch(SQLException e){

        }

    }
    @Override
    public Optional<Book> get(long id) {
        //run query for a book with the id
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM MasterBookIndex WHERE BID = ?");){
            ps.setLong(1, id);
            ResultSet books = ps.executeQuery();
            while(books.next()){
                LOGGER.info("Book with id " + id + " searched for.");
                return Optional.of(createBookFromResultSet(books));
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
        }
        return Optional.empty();
    }
    public ArrayList<Book> search(String searchTerm){
        ArrayList<Book> ret = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(SEARCH_BOOK_MASTER_INDEX_TABLE)){
            ps.setString(1, searchTerm);
            ps.setString(2, searchTerm);
            ps.setString(3, searchTerm);
            ps.setString(4, searchTerm);
            ps.setString(5, searchTerm);
            ps.setString(6, searchTerm);
            ps.setString(7, searchTerm);

            ps.setString(8, searchTerm);
            ps.setString(9, searchTerm);
            ps.setString(10, searchTerm);
            ps.setString(11, searchTerm);
            ps.setString(12, searchTerm);
            ps.setString(13, searchTerm);

            ps.setString(14, searchTerm);
            ps.setString(15, searchTerm);
            ps.setString(16, searchTerm);
            ps.setString(17, searchTerm);
            ps.setString(18, searchTerm);
            ps.setString(19, searchTerm);

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
        try(PreparedStatement ps  = connection.prepareStatement("INSERT INTO book_cardholder (timestamp, book_id, cardholder_id, status) VALUES(?,?,?,?)")){
            Timestamp timestamp = new Timestamp(ZonedDateTime
                    .now(ZoneId.systemDefault())
                    .with(LocalTime.now())
                    .toInstant()
                    .toEpochMilli());
            ps.setTimestamp(1,timestamp);
            ps.setLong(2,book.getBookId());
            ps.setLong(3, cardholder.getChid());
            ps.setString(4,QueryConstants.statusMapping.get(QueryConstants.Status.PENDING));
            ps.executeUpdate();
            if(ps.getUpdateCount() > 0){
                return true;
            }else{
                LOGGER.info("Placing hold for book ID:".concat(String.valueOf(book.getBookId())).concat("failed."));
                return false;
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
        }
        return false;
    }
    public boolean cancelHold(Book book, Cardholder cardholder){
        try(PreparedStatement ps  = connection.prepareStatement("UPDATE book_cardholder SET status = ? WHERE book_id = ? and cardholder_id = ?")){
            ps.setString(1,QueryConstants.statusMapping.get(QueryConstants.Status.CANCELLED));
            ps.setLong(2,book.getBookId());
            ps.setLong(3, cardholder.getChid());
            ps.executeUpdate();
            if(ps.getUpdateCount() > 0){
                return true;
            }else{
                LOGGER.info("Cancelling hold for book ID:".concat(String.valueOf(book.getBookId())).concat("failed."));
                return false;
            }
        }catch(SQLException e){
            LOGGER.info(e.getMessage());
        }
        return false;
    }
    @Override
    public List getAll() {

        return null;
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
