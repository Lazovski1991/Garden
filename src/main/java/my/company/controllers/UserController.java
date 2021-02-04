package my.company.controllers;

import my.company.entity.User;
import my.company.service.User.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public String getPageRegistration() {
        return "registration";
    }

    @PostMapping
    public String addUsers(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        User user = new User(name, email, password, LocalDateTime.now(), true);
        userService.add(user);
        return "/login";
    }
}
