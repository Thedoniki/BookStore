package com.example.TheFinalBookStore.repositorys;

import com.example.TheFinalBookStore.models.Book;

import java.util.List;

public interface IBookCrud {
    Book addBook(Book book);
    List<Book> getAllBooks();
    Book deleteBook(Integer bookISBN);
    Book updateBook(Book book);
    Book getBookByISBN(Integer bookISBN);


}
