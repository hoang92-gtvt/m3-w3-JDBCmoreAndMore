package service.book;

import model.Book;
import model.Category;
import service.ConnectionJDBC;
import service.category.CategoryService;
import service.category.ICategoryService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService implements IBookService{

    public static final String insertBook = "insert into book (name, price) value (?,?)";
    public static final String INSERTINTORELATIVE = "insert into relative(bookID, categoryID) value(?,?)";
    ICategoryService categoryService = new CategoryService();


    public static final String AllBOOK = "select * from book";
    Connection connection = ConnectionJDBC.getConnect();


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

        PreparedStatement statement = connection.prepareStatement(insertBook);
        statement.setString(1, book.getName());
        statement.setInt(2, book.getPrice());
        statement.executeUpdate();

        ResultSet result = statement.getGeneratedKeys();

        int idBook=0;
        while(result.next()){
            idBook = result.getInt("id");

        }
        PreparedStatement statement1 = connection.prepareStatement(INSERTINTORELATIVE);
        for (int i = 0; i < book.getCategories().size(); i++) {
            statement1.setInt(1,idBook);
            statement1.setInt(1,book.getCategories().get(i).getId());
            statement1.executeUpdate();

        }






    }

    @Override
    public void edit(int id, Book book) {

    }

    @Override
    public void delete(int id) {

    }




}
