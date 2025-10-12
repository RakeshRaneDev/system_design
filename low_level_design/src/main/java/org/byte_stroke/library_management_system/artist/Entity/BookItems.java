package org.byte_stroke.library_management_system.artist.Entity;

import org.byte_stroke.library_management_system.artist.enums.BookStatus;

import java.util.*;


public class BookItems {
    private boolean isReferenceOnly;
    private Date borrowedDate;
    private Date dueDate;
    private double price;
    private Date purchaseDate;
    private Rack rack;

    private BookStatus status;
    private Book book;

    public BookItems(boolean isReferenceOnly, Date borrowedDate, Date dueDate, double price, Date purchaseDate, Rack rack, BookStatus status, Book book) {
        this.isReferenceOnly = isReferenceOnly;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.rack = rack;
        this.status = status;
        this.book = book;
    }

    public void checkout(String memberId){

    };

    public boolean isReferenceOnly() {
        return isReferenceOnly;
    }

    public void setReferenceOnly(boolean referenceOnly) {
        isReferenceOnly = referenceOnly;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
