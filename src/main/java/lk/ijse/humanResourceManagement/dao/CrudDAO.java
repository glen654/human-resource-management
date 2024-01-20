package lk.ijse.humanResourceManagement.dao;


import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    boolean save(T entity) throws SQLException;

    String generateId() throws SQLException;

    String splitId(String currentId);

    List<T> loadAll() throws SQLException;

    T search(String id) throws SQLException;

    boolean delete(String id) throws SQLException;

    boolean update(T entity) throws SQLException;

    int getCount() throws SQLException;
}
