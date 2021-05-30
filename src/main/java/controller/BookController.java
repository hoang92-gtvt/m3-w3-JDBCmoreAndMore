package controller;

import model.Book;
import model.Category;
import service.book.BookService;
import service.book.IBookService;
import service.category.CategoryService;
import service.category.ICategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookController", value = "/book")
public class BookController extends HttpServlet {

    IBookService bookService = new BookService();
    ICategoryService categoryService = new CategoryService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                action = "";
            }
            switch (action) {
                case "creat":
                    showFormCreate(request, response);
                    break;
                default:
                    showAllBook(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFormCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        List<Category> categories = categoryService.findAll();

        RequestDispatcher dispatcher = request.getRequestDispatcher("/books/creat.jsp");
        request.setAttribute("categories", categories);
        dispatcher.forward(request, response);
    }

    private void showAllBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Book> bookList = null;

        try {
            bookList = bookService.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/books/list.jsp");
        request.setAttribute("bookList", bookList);

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                action = "";
            }
            switch (action) {
                case "creat":
                    Create(request, response);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Create(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price")) ;

        String[] categories_str = request.getParameterValues("categories");


        int[] categories_int = new int [categories_str.length-1];
        List<Category> categories = new ArrayList<>();


        for (int i = 0; i < categories_str.length; i++) {
            categories_int[i] = Integer.parseInt(categories_str[i]);

            Category newCategory = new Category(categories_int[i]);
            categories.add(newCategory);
        }

        Book newBook = new Book(name, price,categories );

        bookService.add(newBook);

        showAllBook(request,response);





    }
}