package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/admin/api")
    public Set<User> userGetList() {
        return userService.listUsers();
    }
    @GetMapping("/admin/api/user/{id}")
    public User userGet(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/admin/api")
    public Set<User> userAdd(@RequestBody User user) {
        userService.add(user);
        return userService.listUsers();
    }

    @DeleteMapping ("/admin/api")
    public Set<User> userDelete(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return userService.listUsers();
    }

    @PutMapping("/admin/api")
    public Set<User> userUpdate(@RequestBody Map.Entry<User, String> userEntry) {
        userService.update(userEntry.getKey(), userEntry.getValue());
        return userService.listUsers();
    }
}


