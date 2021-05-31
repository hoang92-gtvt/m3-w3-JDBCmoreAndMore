package service.book;

import model.Book;
import model.Category;
import service.ConnectionJDBC;
import service.category.CategoryService;
import service.category.ICategoryService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService implements IBookService{

    public static final String insertBook = "insert into book (name, price) value (?,?)";
    public static final String INSERTINTORELATIVE = "insert into relative(bookID, categoryID) value(?,?)";
    public static final String findBookByID = "select * from book where id=?";
    ICategoryService categoryService = new CategoryService();


    public static final String AllBOOK = "select * from book";
    Connection connection = ConnectionJDBC.getConnect();

    @Override
    public  Book getBookById(int id) throws SQLException {
        Book oldBook = null;
        PreparedStatement statement = connection.prepareStatement(findBookByID);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            String  name = result.getString("name");
            int price = result.getInt("price");
            oldBook = new Book (name, price);
        }

        return oldBook;
    }

    @Override
    public void edit(int id, Book updateBook, int[] categories_int) throws SQLException {

        connection.setAutoCommit(false);
        CallableStatement statement = connection.prepareCall("call updateBook(?,?,?) ");
        statement.setInt(1, id);
        statement.setString(2, updateBook.getName());
        statement.setInt(3, updateBook.getPrice());
        statement.executeUpdate();

        CallableStatement statement1 = connection.prepareCall("call deleteRelativeById(?)");
        statement1.setInt(1, id);
        statement1.executeUpdate();

        CallableStatement statement2 = connection.prepareCall("call insertRalativeById(?,?)");
        for (int i = 0; i < categories_int.length; i++) {
            statement2.setInt(1, id);
            statement2.setInt(2, categories_int[i]);
            statement2.executeUpdate();
        }
        connection.commit();


    }


    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> bookList= new ArrayList<>();

        PreparedStatement statement =connection.prepareStatement(AllBOOK);

        ResultSet result = statement.executeQuery();

        while(result.next()){
            int idBook = result.getInt(1);
            String nameBook = result.getString("name");
            int price = result.getInt("price");

            List<Category> categories = categoryService.findCategoryByID(idBook);

            Book book = new Book(idBook,nameBook, price,categories );

            bookList.add(book);


        }

        return bookList;

    }

    @Override
    public void add(Book book) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(insertBook, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, book.getName());
        statement.setInt(2, book.getPrice());
        statement.executeUpdate();

        ResultSet result = statement.getGeneratedKeys();

        int idBook=0;
        while(result.next()){
            idBook = result.getInt(1);

        }
        PreparedStatement statement1 = connection.prepareStatement(INSERTINTORELATIVE);
        for (int i = 0; i < book.getCategories().size(); i++) {
            statement1.setInt(1,idBook);
            statement1.setInt(2,book.getCategories().get(i).getId());
            statement1.executeUpdate();

        }






    }

    @Override
    public void edit(int id, Book book) {

    }

    @Override
    public void delete(int id) throws SQLException {
        CallableStatement statement = connection.prepareCall("call deleteBookById(?)");
        statement.setInt(1, id);
        statement.executeUpdate();

    }


    @Override
    public void add(Book book, int[] category_id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(insertBook, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, book.getName());
        statement.setInt(2, book.getPrice());
        statement.executeUpdate();

        ResultSet result = statement.getGeneratedKeys();

        int idBook=0;
        while(result.next()){
            idBook = result.getInt(1);

        }
        PreparedStatement statement1 = connection.prepareStatement(INSERTINTORELATIVE);
        for (int i = 0; i < category_id.length; i++) {
            statement1.setInt(1,idBook);
            statement1.setInt(2,category_id[i]);
            statement1.executeUpdate();

        }

    }
}
