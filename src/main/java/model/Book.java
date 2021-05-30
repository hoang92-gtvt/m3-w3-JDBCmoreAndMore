package model;

import java.util.List;

public class Book {
    private int id;
    private String name;
    private int price;
    private List<Category> categories;


    public Book() {

    }

    public Book(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Book(String name, int price, List<Category> categories) {
        this.name = name;
        this.price = price;
        this.categories = categories;
    }

    public Book(int id, String name, int price, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
