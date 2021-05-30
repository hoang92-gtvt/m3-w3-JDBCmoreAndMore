package service;

import java.sql.SQLException;
import java.util.List;

public interface IService <E>{
    List<E> findAll() throws SQLException;
    void add(E e) throws SQLException;
    void edit(int id , E e);
    void delete(int id);

}
