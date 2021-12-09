package com.example.TheFinalBookStore.models;

public class Book {
    private Integer ISBN; //Privata instansvariabler
    private String author;  //Matchar kolumnnamnen i databasen
    private String title;
    private String language;
    private String releseDate;
    private String publisher;
    private String pages;
    private String genre;
    private String storage;
    private String imageUrl;

    public Book() {
    }

    public Book(Integer ISBN, String author, String title, String language, String releseDate, String publisher, String pages, String genre, String storage, String imageUrl) {
        this.ISBN = ISBN;
        this.author = author;
        this.title = title;
        this.language = language;
        this.releseDate = releseDate;
        this.publisher = publisher;
        this.pages = pages;
        this.genre = genre;
        this.storage = storage;
        this.imageUrl = imageUrl;
    }

    public Book(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReleseDate() {
        return releseDate;
    }


    public void setReleseDate(String releseDate) {
        this.releseDate = releseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", releseDate='" + releseDate + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pages='" + pages + '\'' +
                ", genre='" + genre + '\'' +
                ", storage='" + storage + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}