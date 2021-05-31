package service.book;

import model.Book;
import service.IService;

import java.sql.SQLException;

public interface IBookService extends IService<Book> {


    public void add(Book book, int[] category_id) throws SQLException;

    public  Book getBookById(int id) throws SQLException;

    public void edit(int id, Book updateBook, int[] categories_int) throws SQLException;
}
