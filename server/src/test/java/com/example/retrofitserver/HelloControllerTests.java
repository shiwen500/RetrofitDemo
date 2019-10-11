package com.example.retrofitserver;

import com.alibaba.fastjson.JSON;
import com.example.retrofitserver.controllers.HelloController;
import com.example.retrofitserver.model.User;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTests {

    private static final String URL_PREFIX = "/users";

    private MockMvc mvc;

    private String url(String relativeUrl) {
        return URL_PREFIX + relativeUrl;
    }

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    public void testUserName() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(url("/userName")).param("id", "1"))
//                .andDo(MockMvcResultHandlers.print())
//                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(IsEqual.equalTo("csw")));
        mvc.perform(MockMvcRequestBuilders.get(url("/userName")).param("id", "2"))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(IsEqual.equalTo("zsn")));
    }

    @Test
    public void testAddUser() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", "3");
        params.put("name", "lss");
        String response = mvc.perform(MockMvcRequestBuilders.post(url("/addUser"))
                .content(JSON.toJSONString(params))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(IsEqual.equalTo("ok")))
                .andReturn().getResponse().getContentAsString();
        System.out.println("response -> " + response);
        // 查询是否真的添加了
        mvc.perform(MockMvcRequestBuilders.get(url("/userName")).param("id", "3"))
//                .andDo(MockMvcResultHandlers.print())
//                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(IsEqual.equalTo("lss")));
    }

    @Test
    public void testAddUserForm() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "3");
        params.add("name", "lss");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url("/addUserForm"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params);

        mvc.perform(requestBuilder)
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(IsEqual.equalTo("ok")));

        // 查询是否真的添加了
        mvc.perform(MockMvcRequestBuilders.get(url("/userName")).param("id", "3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(IsEqual.equalTo("lss")));
    }

    @Test
    public void testPostFile() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(url("/postFile"))
                .file(new MockMultipartFile("file", "test.txt", MediaType.ALL_VALUE, "Hello World!".getBytes()))
                .param("name", "chenshiwen");
        mvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(IsEqual.equalTo("ok")));
    }

    @Test
    public void testGetJson() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url("/getJson"));
        User user = new User(1L, "csw");
        mvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json(JSON.toJSONString(user)));
    }

    @Test
    public void testPostString() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url("/postString"))
                .content("abs");
        mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDownload() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url("/download"));
        String response = mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("response: " + response);
    }
}
