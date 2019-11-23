package rc.bootsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;

@RestController
@RequestMapping("profile/")
public class ProfileController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public ProfileController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("changeUserName")
    public String changeUserName(@RequestParam String txt) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        User check = this.userRepository.findByUsername(txt);
        if (user != null && check == null) {
            user.setUsername(txt);
            this.userRepository.save(user);
            return "succeed";
        } else {
            return "failure";
        }
    }

    @PostMapping("changePassword")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String newPassword2) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        if (user != null && newPassword.equalsIgnoreCase(newPassword2) && passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            this.userRepository.save(user);
            return "succeed";
        } else {
            return "failure";
        }
    }
}

