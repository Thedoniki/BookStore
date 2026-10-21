package com.example.TheFinalBookStore.services;

import com.example.TheFinalBookStore.models.Book;
import com.example.TheFinalBookStore.repositorys.IBookCrud;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final IBookCrud crud;

    public BookService(IBookCrud crud) {
        this.crud = crud;
    }

    public Book addBook(Book book) {
        return crud.addBook(book);
    }

    public Book updateBook(Book book) {
        return crud.updateBook(book);
    }

    public boolean deleteBook(Integer isbn) {
        return crud.deleteBook(isbn);
    }

    public Optional<Book> findByISBN(Integer isbn) {
        return crud.findByISBN(isbn);
    }

    public List<Book> findAll(String query, String sortBy) {
        return crud.findAll(query, sortBy);
    }
}
