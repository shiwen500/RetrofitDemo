package com.example.retrofitserver.services;

import com.example.retrofitserver.model.Book;

import java.util.List;
import java.util.Map;

public interface IBookService {

    Book getBookById(Long id);

    List<Book> getBooks();

    Book addBook(String name);

    Integer deleteBookByName(String name);

    Integer updateBookNameById(Long id, String name);

    List<Book> getBooksByAuthorId(Long authorId);

    List<Map> getBooksByAuthorId2(Long authorId);
}
