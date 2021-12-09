package com.example.TheFinalBookStore.services;


import com.example.TheFinalBookStore.models.Book;
import com.example.TheFinalBookStore.repositorys.IBookCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//Component based annotation https://www.youtube.com/watch?v=N0EGju17TTI&list=PLGTrAf5-F1YIiJH5rpBcv3M6DSrbi08vI&index=8
public class BookService {

    @Autowired // https://www.youtube.com/watch?v=DnbllV7vpx8&list=PLGTrAf5-F1YIiJH5rpBcv3M6DSrbi08vI&index=9
    private IBookCrud crud;


    public Book addBook(Book book){
        return crud.addBook(book);
    }
    public List<Book> getAllBooks(){
        return crud.getAllBooks();
    }
    public Book deleteBook(Integer bookISBN){
        return crud.deleteBook(bookISBN);
    }
    public Book updateBook(Book book){
        return crud.updateBook(book);
    }
    public Book getBookByISBN(Integer bookISBN){
        return crud.getBookByISBN(bookISBN);
    }




}
