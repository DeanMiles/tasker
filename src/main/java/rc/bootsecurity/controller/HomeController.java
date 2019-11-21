package rc.bootsecurity.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;

@Controller
@RequestMapping("/")
public class HomeController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public HomeController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("register")
    public String processRegister(@ModelAttribute User user) {
        User existing = userRepository.findByUsername(user.getUsername());
        if (existing != null) {
            return "register";
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "result";
        }

    }
}
