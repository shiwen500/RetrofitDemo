package com.example.retrofitserver.services.impl;

import com.alibaba.fastjson.JSON;
import com.example.retrofitserver.mapper.BookMapper;
import com.example.retrofitserver.model.Book;
import com.example.retrofitserver.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book getBookById(Long id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public Book addBook(String name) {
        Book book = new Book(null, name);
        bookMapper.addBook(book);
        return book;
    }

    @Override
    public Integer deleteBookByName(String name) {
        return bookMapper.deleteBookByName(name);
    }

    @Override
    public List<Book> getBooks() {
        return bookMapper.getBooks();
    }

    @Override
    public Integer updateBookNameById(Long id, String name) {
        return bookMapper.updateBookNameById(id, name);
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookMapper.getBooksByAuthorId(authorId);
    }

    @Override
    public List<Map> getBooksByAuthorId2(Long authorId) {
        return bookMapper.getBooksByAuthorId2(authorId);
    }
}
