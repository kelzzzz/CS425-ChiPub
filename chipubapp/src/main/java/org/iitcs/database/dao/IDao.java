package org.iitcs.database.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iitcs.database.connection.ConnectionWrapper;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {
    static final Logger LOGGER = LogManager.getLogger(IDao.class);
    Optional<T> get(long id);

    List<T> getAll();

    void save (T item);

    void update(T t, String[] parameters);

    void delete(T t, String[] parameters);
}
