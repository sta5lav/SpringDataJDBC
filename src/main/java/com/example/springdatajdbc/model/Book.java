package com.example.springdatajdbc.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;


@Entity
@Data
@Table(value = "books")
public class Book {

    @Id
    private Long id;
    private String title;
    private String author;
    private int publicationYear;

}
