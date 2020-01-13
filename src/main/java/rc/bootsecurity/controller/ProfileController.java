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

    @PostMapping("changeUsername")
    public String changeUsername(@RequestParam String newUsername, @RequestParam String password1, @RequestParam String password2) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        User check = userRepository.findByUsername(newUsername);
        if (user != null) {
            if (check == null) {
                if (password1.equalsIgnoreCase(password2)) {
                    if (passwordEncoder.matches(password1, user.getPassword())) {
                        user.setUsername(newUsername);
                        userRepository.save(user);
                        return "Succeed!";
                    } else {
                        return "Incorrect password.";
                    }
                } else {
                    return "Those two passwords are not equal.";
                }
            } else {
                return "This username is already taken.";
            }
        } else {
            return "You are not logged in.";
        }
    }

    @PostMapping("changePassword")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword1, @RequestParam String newPassword2) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        if (user != null) {
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                if (newPassword1.equalsIgnoreCase(newPassword2)) {
                    user.setPassword(passwordEncoder.encode(newPassword1));
                    userRepository.save(user);
                    return "Succeed!";
                } else {
                    return "Those two passwords are not equal.";
                }
            } else {
                return "Incorrect password.";
            }
        } else {
            return "You are not logged in.";
        }
    }
}

