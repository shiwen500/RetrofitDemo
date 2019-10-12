package com.example.retrofitserver;

import com.example.retrofitserver.controllers.BookController;
import com.example.retrofitserver.services.IBookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTests {

    private MockMvc mvc;
    @Autowired
    private IBookService bookService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new BookController(bookService)).build();
    }

    @Test
    public void testGetBookById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/getBookById")
                .param("id", "3");
        mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetBooks() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/getBooks");
        String response = mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("response: " + response);
    }

    @Test
    public void testAddBook() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book/addBook")
                .param("name", "C++");
        mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteBookByName() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/book/deleteBookByName")
                .param("name", "C++");
        mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testUpdateBookNameById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/book/updateBookNameById")
                .param("name", "JAVA")
                .param("id", "1");
        mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testGetBooksByAuthorId() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/getBooksByAuthorId")
                .param("authorId", "1");
        String response = mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("response: " + response);
    }

    @Test
    public void testGetBooksByAuthorId2() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/getBooksByAuthorId2")
                .param("authorId", "2");
        String response = mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("response: " + response);
    }
}
