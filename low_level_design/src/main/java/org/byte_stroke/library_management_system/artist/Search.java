package org.byte_stroke.library_management_system.artist;

import org.byte_stroke.library_management_system.artist.Entity.BookItems;
import org.byte_stroke.library_management_system.artist.enums.BookType;

import java.util.List;
import java.util.Map;

public interface Search {
    List<BookItems> searchByTile(String title);
    List<BookItems> searchByAuthor(String author);
    List<BookItems>  searchByBookType(BookType booktype);
}
