package com.atahan.spring.weather.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String email, @RequestParam String password) {
        User newUser = new User(email, password);
        userRepository.save(newUser);
        return "redirect:/main";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return "redirect:/main";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/version1")
    public String version1() {
        return "version1";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
