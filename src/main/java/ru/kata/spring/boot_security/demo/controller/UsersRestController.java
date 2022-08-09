package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Map;
import java.util.Set;

@RestController
public class UsersRestController {

    UserService userService;

    public UsersRestController(@Autowired UserService userService) {
        this.userService = userService;
    }

//    @ResponseBody
    @GetMapping(value = "/admin/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<User>> userGetList() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }
    @GetMapping(value = "/admin/api/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User userGet(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/admin/api/users")
    public Set<User> userAdd(@RequestBody User user) {
        userService.add(user);
        return userService.listUsers();
    }

    @PutMapping(value = "/admin/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<User> userUpdate(@RequestBody Map<String, Object> requestBody) {
        userService.update((User) requestBody.get("user"), (String) requestBody.get("password"));
        return userService.listUsers();
    }

    @DeleteMapping (value = "/admin/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<User> userDelete(@RequestBody Map.Entry<String, Long> userInfo)  {
        userService.delete(userInfo.getValue());
        return userService.listUsers();
    }
}


