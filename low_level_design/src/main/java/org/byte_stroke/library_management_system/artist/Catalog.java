package org.byte_stroke.library_management_system.artist;

import org.byte_stroke.library_management_system.artist.Entity.Author;
import org.byte_stroke.library_management_system.artist.Entity.BookItems;
import org.byte_stroke.library_management_system.artist.enums.BookType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalog  implements Search{
    Map<String, List<BookItems>> bookByTitle = new HashMap<>();
    Map<String, List<BookItems>> bookByAuthor = new HashMap<>();
    Map<String, List<BookItems>> bookByBookType = new HashMap<>();


    public void addBookItem(BookItems bookItems){
        // adding book in title
        bookByTitle.computeIfAbsent(bookItems.getBook().getTitle(), k-> new ArrayList<>()).add(bookItems);

        //
        List<Author> bookAuthors = bookItems.getBook().getAuthor();
       for(Author author: bookAuthors){
           bookByAuthor.computeIfAbsent(author.getName(), k -> new ArrayList<>()).add(bookItems);
       }

        bookByBookType.computeIfAbsent(bookItems.getBook().getBookType().name(), k->new ArrayList<>())
                .add(bookItems);
    }


    @Override
    public List<BookItems> searchByTile(String title) {
        return bookByTitle.getOrDefault(title, new ArrayList<>());
    }

    @Override
    public List<BookItems> searchByAuthor(String author) {
        return bookByAuthor.getOrDefault(author, new ArrayList<>());
    }

    @Override
    public List<BookItems> searchByBookType(BookType booktype) {
        return bookByBookType.getOrDefault(booktype.name(), new ArrayList<>());
    }
}
