package org.iitcs.database.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.database.connection.ConnectionWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM BOOK WHERE BID = ?");){
            ps.setInt(1,(int)id);
            ResultSet books = ps.executeQuery();
            while(books.next()){
                LOGGER.info("Book with id " + id + " searched for.");
                return Optional.of(createBookFromResultSet(books));
            }
        }catch(SQLException e){
            //do something
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

            ResultSet books = ps.executeQuery();

            while(books.next()){
                ret.add(createBookFromResultSet(books));
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return ret;
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
