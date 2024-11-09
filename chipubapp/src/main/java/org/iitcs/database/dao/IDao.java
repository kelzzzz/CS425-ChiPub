package org.iitcs.database.dao;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {
    Optional<T> get(long id);

    List<T> getAll();

    void save (T item);

    void update(T t, String[] parameters);

    void delete(T t, String[] parameters);
}
