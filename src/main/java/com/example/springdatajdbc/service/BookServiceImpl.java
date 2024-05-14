package com.example.springdatajdbc.service;

import com.example.springdatajdbc.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public BookServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("books")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM books"
                , new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Book getBook(long id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id=?"
                        , new Object[]{id}
                        , new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public Book addBook(Book book) {
        Map<String, Object> parameters = new HashMap<>();
        System.out.println(book);
        parameters.put("title", book.getTitle());
        parameters.put("author", book.getAuthor());
        parameters.put("publicationYear", book.getPublicationYear());
        Number id = jdbcInsert.executeAndReturnKey(parameters);
        book.setId(id.longValue());
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update("UPDATE books SET title = ?" +
                        ", author = ?" +
                        ", publicationYear = ? " +
                        "WHERE id = ?"
                , book.getTitle()
                , book.getAuthor()
                , book.getPublicationYear()
                , book.getId());
        return book;
    }

    @Override
    public boolean deleteBook(long id) {
        return jdbcTemplate.update("DELETE FROM books WHERE id = ?", id) > 0;
    }
}
