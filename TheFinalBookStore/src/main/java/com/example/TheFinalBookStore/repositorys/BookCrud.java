package com.example.TheFinalBookStore.repositorys;

import com.example.TheFinalBookStore.models.Book;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository //Component based annotation https://www.youtube.com/watch?v=N0EGju17TTI&list=PLGTrAf5-F1YIiJH5rpBcv3M6DSrbi08vI&index=8
public class BookCrud implements IBookCrud { //Lägg till title

    private Connection con;




    @Override
    public Book addBook(Book book) {
        try {
            getConnection();

            String insertBookSql = "INSERT INTO books VALUES(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement prepStat = con.prepareStatement(insertBookSql);
            prepStat.setInt(1, book.getISBN());
            prepStat.setString(2, book.getAuthor());
            prepStat.setString(3,book.getTitle());
            prepStat.setString(4, book.getLanguage());
            prepStat.setString(5, book.getReleseDate());
            prepStat.setString(6, book.getPublisher());
            prepStat.setString(7, book.getPages());
            prepStat.setString(8, book.getGenre());
            prepStat.setString(9, book.getStorage());
            prepStat.setString(10, book.getImageUrl());

            prepStat.executeUpdate();
            con.setAutoCommit(true);
            prepStat.close();
            con.close();
            return book;
        } catch (SQLException log) {
            Logger.getLogger(BookCrud.class.getName()).log(Level.SEVERE,null,log);
        }
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        try {
            con = getConnection();
            String updateBookSql = "UPDATE books SET author=?, title=?, language=?, releseDate=?, publisher=?, pages=?, genre=?, storage=?, imageUrl=? WHERE ISBN=?";

            PreparedStatement prpStmt = con.prepareStatement(updateBookSql);
            prpStmt.setString(1, book.getAuthor());
            prpStmt.setString(2,book.getTitle());
            prpStmt.setString(3, book.getLanguage());
            prpStmt.setString(4, book.getReleseDate());
            prpStmt.setString(5, book.getPublisher());
            prpStmt.setString(6, book.getPages());
            prpStmt.setString(7, book.getGenre());
            prpStmt.setString(8, book.getStorage());
            prpStmt.setString(9, book.getImageUrl());
            prpStmt.setInt(10, book.getISBN());

            prpStmt.executeUpdate();
            prpStmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookCrud.class.getName() + ex.getMessage());
        }
        return null;
    }

    @Override
    public Book deleteBook(Integer bookISBN) {

        try {
            con = getConnection();

            //? är platshållar för data som ska "in" i tabellen
            String deleteBookSql = "DELETE FROM books WHERE ISBN=?";
            // A SQL statement is precompiled and stored in a PreparedStatement object.
            // This object can then be used to efficiently execute this statement multiple times.

            PreparedStatement prepStatmt = con.prepareStatement(deleteBookSql);
            prepStatmt.setInt(1, (bookISBN));// sätter data för det första frågetecken eg första platshålleran
            // Executes the SQL statement in this PreparedStatement object,
            //which must be an SQL Data Manipulation Language (DML) statement, such as INSERT, UPDATE OR DELETE;
            prepStatmt.executeUpdate();
            prepStatmt.close();
            // sätter data för det första frågetecken eg första platshålleran
            con.close();

        } catch (SQLException ex) {
            System.out.println(BookCrud.class.getName() + ex.getMessage());

        }

        return null;
    }

    @Override
    public Book getBookByISBN(Integer ISBN) {
        try {
            con = getConnection();
            String sqlSelectBookByID = "SELECT* FROM books WHERE ISBN=?";
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(sqlSelectBookByID);
            stmt.setInt(1, ISBN);

            ResultSet resultSet = stmt.executeQuery();
            Book book = new Book();
            while (resultSet.next()){
                selectFromDB(resultSet, book);

            }
            resultSet.close();
            stmt.close();
            con.close();
            return book;
        } catch (SQLException ex) {
            Logger.getLogger(BookCrud.class.getName()).log(Level.SEVERE,null,ex);
        }
        return null;
    }


    @Override
    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();
        try{
            getConnection();
            Statement stmt = con.createStatement();
            String sqlSelectAllCars = "SELECT * FROM books"; //String sqlSelectAllCars = "SELECT ISBN,author,title,language,releseDate,publisher,pages,genre,storage,imageUrl from books";
            ResultSet resultSet = stmt.executeQuery(sqlSelectAllCars);
            while (resultSet.next()){
                Book book = new Book();
                selectFromDB(resultSet, book);
                books.add(book);
            }
            resultSet.close();
            stmt.close();
            con.close();
            return books;
        } catch (SQLException ex) {
            System.out.println(BookCrud.class.getName() + ex.getMessage());
        }
        return null;
    }

    private void selectFromDB(ResultSet resultSet, Book book) throws SQLException {
        book.setISBN(resultSet.getInt("ISBN"));
        book.setAuthor(resultSet.getString("author"));
        book.setTitle(resultSet.getString("title"));
        book.setLanguage(resultSet.getString("language"));
        book.setReleseDate(resultSet.getString("releseDate"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setPages(resultSet.getString("pages"));
        book.setGenre(resultSet.getString("genre"));
        book.setStorage(resultSet.getString("storage"));
        book.setImageUrl(resultSet.getString("imageUrl"));
    }


    private Connection getConnection() {

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/QGiurckTaL",
                    "QGiurckTaL",
                    "9mcXfR7XqJ");
            //     con.setAutoCommit(true);//Saves without us having to commit https://stackoverflow.com/questions/10457335/commit-or-conn-setautocommittrue
            return con;
        } catch (SQLException ex) {
            System.out.println(BookCrud.class.getName() + ex.getMessage());
        }
        return null;
    }



}