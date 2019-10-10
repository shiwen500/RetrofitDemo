package com.example.retrofitserver.controllers;

import com.example.retrofitserver.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class HelloController {

    static Map<Long, String> users = Collections.synchronizedMap(new HashMap<>());
    static {
        users.put(1L, "csw");
        users.put(2L, "zsn");
    }

    @RequestMapping(value = "/userName", method = RequestMethod.GET)
    public String userName(@RequestParam Long id) {
        System.out.println("userName -> id: " + id);
        return users.get(id);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestBody User user) {
        System.out.println("addUser : " + user);
        if (users.containsKey(user.getId())) {
            return "error, user exists";
        } else {
            users.put(user.getId(), user.getName());
        }
        return "ok";
    }
    @RequestMapping(value = "/addUserForm", method = RequestMethod.POST)
    public String addUserForm(Long id, @ModelAttribute("name") String name, @RequestBody String body) {
        System.out.println("addUserForm : id -> " + id +  ", name -> " + name + ",body => " + body);
        if (users.containsKey(id)) {
            return "error, user exists";
        } else {
            users.put(id, name);
        }
        return "ok";
    }
    @RequestMapping(value = "/addUserFormJson", method = RequestMethod.POST)
    public String addUserFormJson(@RequestParam("json") String user) {
        System.out.println("addUserFormJson : user -> " + user);
//        if (users.containsKey(user.getId())) {
//            return "error, user exists";
//        } else {
//            users.put(user.getId(), user.getName());
//        }
        return "ok";
    }

    @PostMapping(value = "/postFile")
    public String postFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "name", required = false) String name) {
        System.out.println("Post file Info ====");
        System.out.println("name -> " + file.getName());
        System.out.println("contentType -> " + file.getContentType());
        System.out.println("OriginalFilename -> " + file.getOriginalFilename());
        System.out.println("size -> " + file.getSize());
        System.out.println("isEmpty -> " + file.isEmpty());

        System.out.println("ex-name -> " + name);
        return "ok";
    }

    @GetMapping(value = "/getJson")
    @ResponseBody
    public User getJson() {
        User user = new User();
        user.setId(1L);
        user.setName("csw");
        return user;
    }

    @PostMapping(value = "postString")
    public String postString(@RequestBody String body) {
        System.out.println("postString => body: " + body);
        return "postString ok!";
    }

    @PostMapping(value = "postStream")
    public String postStream(@RequestBody String body) {
        System.out.println("postStream => body: " + body);
        return "postStream ok!";
    }
}
