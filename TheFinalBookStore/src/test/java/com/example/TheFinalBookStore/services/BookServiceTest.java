package com.example.TheFinalBookStore.services;

import com.example.TheFinalBookStore.models.Book;
import com.example.TheFinalBookStore.repositorys.IBookCrud;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private IBookCrud crud;

    @InjectMocks
    private BookService service;

    @Test
    void findByISBNReturnsEmptyWhenMissing() {
        when(crud.findByISBN(1)).thenReturn(Optional.empty());
        assertTrue(service.findByISBN(1).isEmpty());
    }

    @Test
    void findByISBNReturnsBookWhenPresent() {
        Book book = new Book(1, "Author", "Title", null, null, null, null, null, null, null);
        when(crud.findByISBN(1)).thenReturn(Optional.of(book));
        assertEquals("Title", service.findByISBN(1).orElseThrow().getTitle());
    }

    @Test
    void findAllPassesSearchAndSortToRepository() {
        when(crud.findAll("tolkien", "author")).thenReturn(List.of());
        assertEquals(List.of(), service.findAll("tolkien", "author"));
        verify(crud).findAll("tolkien", "author");
    }

    @Test
    void deleteBookReportsWhetherRowWasRemoved() {
        when(crud.deleteBook(5)).thenReturn(true);
        when(crud.deleteBook(6)).thenReturn(false);
        assertTrue(service.deleteBook(5));
        assertFalse(service.deleteBook(6));
    }
}
