package org.byte_stroke.library_management_system.artist.Entity;

import java.util.List;

public class Author extends  Person{
    private List<Book> books;

    public Author(String name, Address address, String email, String phone , List<Book> books) {
       super(name, address, email, phone);
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
