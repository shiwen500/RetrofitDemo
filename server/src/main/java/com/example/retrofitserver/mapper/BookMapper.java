package com.example.retrofitserver.mapper;

import com.example.retrofitserver.model.Book;
import com.example.retrofitserver.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookMapper {

    Book getBookById(@Param("id") Long id);

    List<Book> getBooks();

    void addBook(@Param("book") Book book);

    Integer deleteBookByName(@Param("name") String name);

    Integer updateBookNameById(@Param("id") Long id, @Param("name") String name);

    List<Book> getBooksByAuthorId(@Param("authorId") Long authorId);

    List<Map> getBooksByAuthorId2(@Param("authorId") Long authorId);
}
