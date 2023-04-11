package ru.stud.homer.springpreproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.stud.homer.springpreproject.models.User;
import ru.stud.homer.springpreproject.services.UserService;
import ru.stud.homer.springpreproject.validators.UserCreationValidator;
import ru.stud.homer.springpreproject.validators.UserUpdatingValidator;

@Controller
@RequestMapping("/users")
public class UsersController {


    private final UserService userService;
    private final UserCreationValidator userCreationValidator;
    private final UserUpdatingValidator userUpdatingValidator;

    @Autowired
    public UsersController(UserService userService,
                           UserCreationValidator userCreationValidator,
                           UserUpdatingValidator userUpdatingValidator) {
        this.userService = userService;
        this.userCreationValidator = userCreationValidator;
        this.userUpdatingValidator = userUpdatingValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/index";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userCreationValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/new";
        } else {
            userService.add(user);
            return "redirect:/users";
        }
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "users/show";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        userUpdatingValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/edit";
        } else {
            userService.update(id, user);
            return "redirect:/users";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "users/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
