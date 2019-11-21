package rc.bootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rc.bootsecurity.model.User;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class HomeController {

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

        return "result";

    }
}
