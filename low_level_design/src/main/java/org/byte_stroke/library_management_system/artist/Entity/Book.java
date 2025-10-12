package org.byte_stroke.library_management_system.artist.Entity;

import org.byte_stroke.library_management_system.artist.enums.BookType;

import java.util.*;
import java.util.List;

public class Book {
    private String isbn;
    private String title;
    private String language;
    private BookType bookType;
    private String publication;
    private Date publicationDate;
    private List<Author> author;

    public Book(String isbn, String title, BookType bookType, String publication,
                Date publicationDate, List<Author> author, String language) {
        this.isbn = isbn;
        this.title = title;
        this.bookType = bookType;
        this.publication = publication;
        this.publicationDate = publicationDate;
        this.author = author;
        this.language = language;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }
}
