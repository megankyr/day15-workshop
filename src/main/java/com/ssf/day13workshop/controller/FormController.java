package com.ssf.day13workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ssf.day13workshop.model.User;
import com.ssf.day13workshop.repo.ContactsRepo;

import jakarta.validation.Valid;

@Controller
@RequestMapping

public class FormController {

    @Autowired
    ContactsRepo contactsRepo;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "contact";
    }

    @PostMapping("/contact")
    public String processForm(@Valid @ModelAttribute User user, BindingResult binding) {
        if (binding.hasErrors()) {
            return "contact";
        }

        if (!user.isValidAge()) {
            FieldError err = new FieldError("user", "dob", "Age must be between 10 and 100 years old");
            binding.addError(err);
            return "contact";
        }
        contactsRepo.saveUser(user);
        System.out.println("Contact saved in Redis");

        return "contact-created";
    }

    @GetMapping("/contact/{id}")
    public String loadID(@PathVariable String id, Model model) {
        User user = contactsRepo.getUser(id);
        model.addAttribute("user", user);
        return "contact-by-id";

    }

    @GetMapping("/contact/list")
    public String loadLinks(Model model) {
        List<User> users = contactsRepo.getAllUsers();
        model.addAttribute("users", users);
        return "contacts";
    }

}