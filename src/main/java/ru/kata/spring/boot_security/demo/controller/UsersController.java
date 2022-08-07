package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    public UsersController(@Autowired UserService userService, @Autowired RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/index2")
    public String indexPage2(ModelMap model) {
        List<Role> roles = roleService.listRoles();
        model.addAttribute("roles", roles);
        return "index2";
    }

    @GetMapping(value = "/")
    public String indexPage(ModelMap model, Authentication authentication) {
        if (authentication.getAuthorities().contains(roleService.getRole(2L))) {
            model.addAttribute("users", userService.listUsers());
        }
        List<Role> roles = roleService.listRoles();
        model.addAttribute("roles", roles);
        return "index";
    }

    @PostMapping(value = "/admin/new")
    public RedirectView createUser(ModelMap model, @ModelAttribute("user") User user,
                                   @RequestParam(value = "roles", required = false) Long[] rolesIds) {

        if (rolesIds != null) {
            for (Long id : rolesIds) {
                user.addRole(roleService.getRole(id));
            }
        }
        userService.add(user);
        return new RedirectView("/");
    }

    @PostMapping(value = "/admin/{id}")
    public RedirectView updateUser(ModelMap model,
                                   @PathVariable("id") Long id,
                                   @RequestParam("email") String email,
                                   @RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam(value = "roles", required = false) Long[] rolesIds) {
        User user = userService.getUser(id);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.dropRoles();
        userService.addRoles(user, rolesIds);
        userService.update(user, password);
        return new RedirectView("/");
    }

    @GetMapping(value = "/admin/{id}/delete")
    public RedirectView deleteUser(ModelMap model, @PathVariable("id") Long id) {
        userService.delete(id);
        return new RedirectView("/");
    }
}