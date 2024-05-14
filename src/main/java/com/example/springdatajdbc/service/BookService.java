package com.example.springdatajdbc.service;


import com.example.springdatajdbc.model.Book;
import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBook(long id);

    Book addBook(Book book);

    Book updateBook(Book book);

    boolean deleteBook(long id);

}
