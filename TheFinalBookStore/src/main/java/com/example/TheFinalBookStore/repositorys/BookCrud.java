package com.example.TheFinalBookStore.repositorys;

import com.example.TheFinalBookStore.models.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookCrud implements IBookCrud {

    /** Tillåtna sorteringsnycklar. Värdena används direkt i SQL, så de får inte komma från användaren obehandlade. */
    static final Map<String, String> SORTABLE_COLUMNS = Map.of(
            "title", "title",
            "author", "author",
            "releseDate", "releseDate",
            "isbn", "ISBN");

    private static final String SELECT_COLUMNS =
            "SELECT ISBN, author, title, language, releseDate, publisher, pages, genre, storage, imageUrl FROM books";

    private static final RowMapper<Book> BOOK_MAPPER = (rs, rowNum) -> {
        Book book = new Book();
        book.setISBN(rs.getInt("ISBN"));
        book.setAuthor(rs.getString("author"));
        book.setTitle(rs.getString("title"));
        book.setLanguage(rs.getString("language"));
        book.setReleseDate(rs.getString("releseDate"));
        book.setPublisher(rs.getString("publisher"));
        book.setPages(rs.getString("pages"));
        book.setGenre(rs.getString("genre"));
        book.setStorage(rs.getString("storage"));
        book.setImageUrl(rs.getString("imageUrl"));
        return book;
    };

    private final JdbcTemplate jdbc;

    public BookCrud(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book addBook(Book book) {
        jdbc.update(
                "INSERT INTO books (ISBN, author, title, language, releseDate, publisher, pages, genre, storage, imageUrl) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                book.getISBN(), book.getAuthor(), book.getTitle(), book.getLanguage(), book.getReleseDate(),
                book.getPublisher(), book.getPages(), book.getGenre(), book.getStorage(), book.getImageUrl());
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        int rows = jdbc.update(
                "UPDATE books SET author=?, title=?, language=?, releseDate=?, publisher=?, pages=?, genre=?, storage=?, imageUrl=? "
                        + "WHERE ISBN=?",
                book.getAuthor(), book.getTitle(), book.getLanguage(), book.getReleseDate(), book.getPublisher(),
                book.getPages(), book.getGenre(), book.getStorage(), book.getImageUrl(), book.getISBN());
        if (rows == 0) {
            throw new IllegalArgumentException("Ingen bok med ISBN " + book.getISBN() + " hittades");
        }
        return book;
    }

    @Override
    public boolean deleteBook(Integer isbn) {
        return jdbc.update("DELETE FROM books WHERE ISBN=?", isbn) > 0;
    }

    @Override
    public Optional<Book> findByISBN(Integer isbn) {
        List<Book> books = jdbc.query(SELECT_COLUMNS + " WHERE ISBN=?", BOOK_MAPPER, isbn);
        return books.stream().findFirst();
    }

    @Override
    public List<Book> findAll(String query, String sortBy) {
        String column = SORTABLE_COLUMNS.getOrDefault(sortBy, "title");
        if (query == null || query.isBlank()) {
            return jdbc.query(SELECT_COLUMNS + " ORDER BY " + column, BOOK_MAPPER);
        }
        String like = "%" + query.trim() + "%";
        return jdbc.query(
                SELECT_COLUMNS + " WHERE CAST(ISBN AS CHAR) LIKE ? OR title LIKE ? OR author LIKE ? ORDER BY " + column,
                BOOK_MAPPER, like, like, like);
    }
}
