package com.example.TheFinalBookStore.controllers;

import com.example.TheFinalBookStore.models.Book;
import com.example.TheFinalBookStore.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class BooksController {

    private static final Logger logger = LoggerFactory.getLogger(BooksController.class);

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("q")
    public String defaultSearchQuery() {
        return "";
    }

    // Startsida: alla böcker, med valfri sökning och sortering
    @GetMapping("/bookshop")
    public String getAllBooks(Model model,
                              @RequestParam(name = "q", required = false) String query,
                              @RequestParam(name = "sort", defaultValue = "title") String sort) {
        model.addAttribute("books", bookService.findAll(query, sort));
        model.addAttribute("q", query == null ? "" : query);
        model.addAttribute("sort", sort);
        return "booksviewmysql";
    }

    // Visa en bok
    @GetMapping("/book/{ISBN}")
    public String getBookByISBN(Model model, @PathVariable Integer ISBN) {
        model.addAttribute("book", findOrNotFound(ISBN));
        return "bookview";
    }

    // Lägg till bok
    @GetMapping("/book/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addview";
    }

    @PostMapping("/book/add")
    public String addedBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/book/" + book.getISBN();
    }

    // Redigera bok
    @GetMapping("/book/{ISBN}/edit")
    public String editBook(Model model, @PathVariable Integer ISBN) {
        model.addAttribute("book", findOrNotFound(ISBN));
        return "editview";
    }

    @PostMapping("/book/{ISBN}/edit")
    public String updateBook(Model model, @PathVariable Integer ISBN, @ModelAttribute("book") Book book) {
        book.setISBN(ISBN);
        try {
            bookService.updateBook(book);
            return "redirect:/book/" + ISBN;
        } catch (RuntimeException ex) {
            logger.error("Kunde inte uppdatera bok {}", ISBN, ex);
            model.addAttribute("book", book);
            model.addAttribute("errorMessage", ex.getMessage());
            return "editview";
        }
    }

    // Bekräfta borttagning (GET visar bara en sida, ändrar inget)
    @GetMapping("/book/{ISBN}/delete")
    public String showDeleteBook(Model model, @PathVariable Integer ISBN) {
        model.addAttribute("book", findOrNotFound(ISBN));
        return "bookDelete";
    }

    // Tar bort boken. Endast POST, så att en länk eller förhämtning inte kan radera en bok.
    @PostMapping("/book/{ISBN}/drop")
    public String deleteBook(Model model, @PathVariable Integer ISBN) {
        if (!bookService.deleteBook(ISBN)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boken finns inte");
        }
        model.addAttribute("ISBN", ISBN);
        return "changesSaved";
    }

    private Book findOrNotFound(Integer isbn) {
        return bookService.findByISBN(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Boken finns inte"));
    }
}
