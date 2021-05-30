package service.category;

import model.Category;
import service.ConnectionJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService{
    public static final String FINDCATEGORYBYIDOFBOOK =
            "select c.id as catagoryId, c.name as catagoryName from category as c join relative r on c.id = r.categoryID join book b on r.bookID = b.id where b.id =?";
    public static final String allCategory = "select * from category";
    Connection connection = ConnectionJDBC.getConnect();

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> categories = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(allCategory);
        ResultSet resultSet =statement.executeQuery();
        while(resultSet.next()){
            int categoryId = resultSet.getInt(1);
            String categoryName= resultSet.getString(2);
            String categoryDescription= resultSet.getString(3);

            categories.add(new Category(categoryId, categoryName));

        }

        return categories;
    }

    @Override
    public void add(Category category) {

    }

    @Override
    public void edit(int id, Category category) {

    }

    @Override
    public void delete(int id) {

    }


    @Override
    public List<Category> findCategoryByID(int idOfBook) throws SQLException {
        List<Category> categories= new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(FINDCATEGORYBYIDOFBOOK);
        statement.setInt(1, idOfBook);

        ResultSet resultSet= statement.executeQuery();

        while(resultSet.next()){
            int categoryId = resultSet.getInt(1);
            String  categoryName = resultSet.getString(2);
            Category category = new Category(categoryId, categoryName);

            categories.add(category);
            System.out.println("");

        }
        System.out.println(categories);
        return categories;

    }
}
