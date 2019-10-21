package com.example.retrofitserver.controllers;

import com.example.retrofitserver.model.Book;
import com.example.retrofitserver.services.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    private IBookService service;

    @Autowired
    public BookController(IBookService service) {
        this.service = service;
    }

    @GetMapping("/getBookById")
    public Book getBookById(@RequestParam("id") Long id) {
        System.out.println("getBookById: " + id);
        Book ret = service.getBookById(id);
        System.out.println("getBookById: " + ret);
        return ret;
    }

    @GetMapping("/getBooks")
    public List<Book> getBooks() {
        return service.getBooks();
    }

    @PostMapping("/addBook")
    public Book addBook(@RequestParam String name) {
        System.out.println("addBook: " + name);
        Book ret = service.addBook(name);
        System.out.println("addBook: " + ret);
        return ret;
    }

    @DeleteMapping("/deleteBookByName")
    public boolean deleteBookByName(@RequestParam String name) {
        Integer count = service.deleteBookByName(name);
        System.out.println("deleteBookByName: " + count);
        return count > 0;
    }

    @PutMapping("/updateBookNameById")
    public boolean updateBookNameById(@RequestParam Long id, @RequestParam String name) {
        Integer count = service.updateBookNameById(id, name);
        System.out.println("updateBookNameById: " + count);
        return count > 0;
    }

    // 查询某个作者的书籍
    @GetMapping("/getBooksByAuthorId")
    public List<Book> getBooksByAuthorId(@RequestParam Long authorId) {
        return service.getBooksByAuthorId(authorId);
    }

    // 查询某个作者的书籍
    @GetMapping("/getBooksByAuthorId2")
    public List<Map> getBooksByAuthorId2(@RequestParam Long authorId) {
        return service.getBooksByAuthorId2(authorId);
    }

    // 分页查询book
    @GetMapping("/getBooksPage")
    public PageInfo<Book> getBooks(@RequestParam int pageSize, @RequestParam int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(service.getBooks());
    }
}
