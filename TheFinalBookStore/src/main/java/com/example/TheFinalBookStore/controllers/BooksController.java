package com.example.TheFinalBookStore.controllers;

import com.example.TheFinalBookStore.models.Book;
import com.example.TheFinalBookStore.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class BooksController {


    @Autowired// https://www.youtube.com/watch?v=DnbllV7vpx8&list=PLGTrAf5-F1YIiJH5rpBcv3M6DSrbi08vI&index=9
    private BookService bookService; // min 7ish explains SOLID accheved

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    //Get All Books
    @GetMapping("/bookshop") // Förstasida med alla böcker
    public String getAllBooks(Model model){
        model.addAttribute("books",bookService.getAllBooks());
        return "booksviewmysql";
    }
    //Get All Books end

    //get a use  depending on ket and value match 19 min in. May help.
    //https://www.youtube.com/watch?v=nSbThWBSZ7k
    //27 min in förk på get,put osv

    //Get book by ISBN
    @GetMapping("book/{ISBN}") // visar specifik bokspecifikation
    public String getBookByISBN(Model model,@PathVariable Integer ISBN){

        model.addAttribute("book", bookService.getBookByISBN(ISBN));
        return"bookview";
    }
    //Get book by ISBN end


    //Add book
    @GetMapping(value ={ "book/add"})//Tar väderna som skrivit in och skapar ett nytt bokobjekt
    public String addBook(Model model) {

        model.addAttribute("book", new Book());
        return "addview";//
    }

    @PostMapping(value="book/add") //tar det inskrivna datat och gör det möjligt att visa specifik bok info
    public String addedBook(@ModelAttribute("book")Book book, @RequestParam Map<String,String> allRequestedParams ){

        bookService.addBook(book);
        return "redirect:/book/" + book.getISBN(); // Redirectar till den addade bokens sida via ISBN
    }
    // Add end //

    // Update book start //
    @GetMapping(value={"/book/{ISBN}/edit"})
    public String editBook(Model model, @PathVariable("ISBN") Integer ISBN) {

        model.addAttribute("book",bookService.getAllBooks());
        model.addAttribute("book",  bookService.getBookByISBN(ISBN));
        return "editview";
    }


    @PostMapping(value={"/book/{ISBN}/edit"}) //https://www.dariawan.com/tutorials/spring/spring-boot-mustache-crud-example/
    public String updateBook(Model model, @PathVariable Integer ISBN,@ModelAttribute("book") Book book){

        try {
            book.setISBN(ISBN);
            bookService.updateBook(book);

            return "redirect:/book/" + book.getISBN();
        } catch (Exception ex){
        // log exception first,
        // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);

            model.addAttribute("errorMessage", errorMessage);
            return "editview";
        }
    }
    // Update book end //

    // Delete start //
    @GetMapping(value = {"/book/{ISBN}/delete"}) // funkar men redirectar ingenstans
    public String showDeleteUser(Model model, @PathVariable("ISBN") Integer ISBN) {

        Book book =  bookService.getBookByISBN(ISBN);

        model.addAttribute("book", book);
        return "bookDelete";
    }


    @GetMapping(value = {"/book/{ISBN}/drop"})
    public Comparable<String> deleteUserById( @PathVariable("ISBN") Integer ISBN) {

        bookService.deleteBook(ISBN);

        return "changesSaved";
        }
    // Delete end //

    }//Class end

