package com.example.TheFinalBookStore.repositorys;

import com.example.TheFinalBookStore.models.Book;

import java.util.List;
import java.util.Optional;

public interface IBookCrud {

    Book addBook(Book book);

    /**
     * Uppdaterar en bok.
     *
     * @throws IllegalArgumentException om ingen bok med angivet ISBN finns
     */
    Book updateBook(Book book);

    /** @return true om en bok togs bort */
    boolean deleteBook(Integer isbn);

    Optional<Book> findByISBN(Integer isbn);

    /**
     * @param query  sökterm mot ISBN, titel och författare, eller null för alla böcker
     * @param sortBy nyckel från {@link com.example.TheFinalBookStore.repositorys.BookCrud#SORTABLE_COLUMNS}
     */
    List<Book> findAll(String query, String sortBy);
}
