package com.springboot.demo.controller;

import com.springboot.demo.model.User;
import com.springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public String printUsers(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("users", this.userService.listUsers());
        model.addAttribute("ROLES", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        return "/admin/users";
    }

    @PostMapping(value = "/users/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam List<String> rolesValues) {
        this.userService.addUser(user, rolesValues);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/users/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        this.userService.removeUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/editUser")
    public String editUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", this.userService.getUserById(id));
        model.addAttribute("users", this.userService.listUsers());
        model.addAttribute("ROLES", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        return "/admin/editUser";
    }
}