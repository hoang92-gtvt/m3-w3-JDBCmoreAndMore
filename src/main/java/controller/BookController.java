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

                case "edit":
                    showFormEdit(request, response);
                    break;

                case "delete":
                    showFormDelete(request,response);
                default:
                    showAllBook(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFormDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/books/delete.jsp");
        dispatcher.forward(request,response);

    }

    private void showFormEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Category> categories = categoryService.findAll();
        Book oldBook = bookService.getBookById(id);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/books/edit.jsp");
            request.setAttribute("old", oldBook);
            request.setAttribute("categories", categories);
        dispatcher.forward(request, response);

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

                case "edit":
                    Edit(request, response);
                    break;

                case "delete":
                    delete(request,response);
                default:
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        bookService.delete(id);
        showAllBook(request, response);

    }

    private void Edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
//
        int id= Integer.parseInt(request.getParameter("id") );
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        Book updateBook = new Book(name, price);
//
        String[] categoriesStr = request.getParameterValues("categories");
        int[] categories_int = new int[categoriesStr.length];
        for (int i = 0; i < categoriesStr.length; i++) {
            categories_int[i] = Integer.parseInt(categoriesStr[i]);

        }
        bookService.edit(id, updateBook, categories_int);

        showAllBook(request,response);

    }

    private void Create(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price")) ;

        String[] categories_str = request.getParameterValues("categories");


        int[] categories_int = new int [categories_str.length];
        List<Category> categories = new ArrayList<>();


        for (int i = 0; i < categories_str.length; i++) {
            categories_int[i] = Integer.parseInt(categories_str[i]);

            Category newCategory = new Category(categories_int[i]);
            categories.add(newCategory);
        }

//        Book newBook = new Book(name, price,categories );
        Book newBook1 = new Book(name,price);

//        bookService.add(newBook);
        bookService.add(newBook1, categories_int);

        showAllBook(request,response);

    }






}