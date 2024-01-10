package com.atahan.spring.weather.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;
    private final UserSearchRepository userSearchRepository;

    @Autowired
    public UserController(UserRepository userRepository, UserSearchRepository userSearchRepository) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signupView() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginView() {
        return "signup";
    }

    @GetMapping("/history")
    public List<UserSearch> history() {
        List<UserSearch> userSearch = userSearchRepository.findAll();
        return userSearch;
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

    @PostMapping("/history-save")
    public UserSearch historySave(@RequestParam String text) {
        UserSearch newSearch = new UserSearch(text,"1");
        userSearchRepository.save(newSearch);
        return newSearch;
    } 

    @GetMapping("/version1")
    public String version1() {
        return "version1";
    }

    @GetMapping("/version1second")
    public String version1second() {
        return "version1second";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
