package com.example.TheFinalBookStore.controllers;

import com.example.TheFinalBookStore.models.Book;
import com.example.TheFinalBookStore.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
class BooksControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    private static Book sampleBook() {
        return new Book(1, "Author", "Title", "Svenska", "2020", "Förlag", "100", "Roman", "3", "");
    }

    @Test
    void bookshopPassesSearchAndSortToService() throws Exception {
        when(bookService.findAll("hobbit", "author")).thenReturn(List.of(sampleBook()));

        mvc.perform(get("/bookshop").param("q", "hobbit").param("sort", "author"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksviewmysql"));
    }

    @Test
    void bookshopUsesTitleSortByDefault() throws Exception {
        when(bookService.findAll(any(), anyString())).thenReturn(List.of());

        mvc.perform(get("/bookshop")).andExpect(status().isOk());
        verify(bookService).findAll(null, "title");
    }

    @Test
    void unknownBookReturnsNotFound() throws Exception {
        when(bookService.findByISBN(42)).thenReturn(Optional.empty());

        mvc.perform(get("/book/42")).andExpect(status().isNotFound());
    }

    @Test
    void deleteIsOnlyAllowedWithPost() throws Exception {
        mvc.perform(get("/book/1/drop")).andExpect(status().isMethodNotAllowed());
        verify(bookService, never()).deleteBook(any());
    }

    @Test
    void postDeleteRemovesBook() throws Exception {
        when(bookService.deleteBook(1)).thenReturn(true);

        mvc.perform(post("/book/1/drop"))
                .andExpect(status().isOk())
                .andExpect(view().name("changesSaved"))
                .andExpect(model().attribute("q", ""));
    }

    @Test
    void failedUpdateShowsEditFormWithError() throws Exception {
        when(bookService.updateBook(any())).thenThrow(new IllegalArgumentException("Ingen bok"));

        MvcResult result = mvc.perform(post("/book/1/edit")
                        .param("title", "Ny titel")
                        .param("author", "Ny författare"))
                .andExpect(status().isOk())
                .andExpect(view().name("editview"))
                .andExpect(model().attribute("errorMessage", "Ingen bok"))
                .andExpect(model().attribute("q", ""))
                .andReturn();

        Book book = (Book) result.getModelAndView().getModel().get("book");
        assertEquals(1, book.getISBN());
        assertEquals("Ny titel", book.getTitle());
        assertEquals("Ny författare", book.getAuthor());
    }
}
