package rc.bootsecurity.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rc.bootsecurity.model.User;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        this.userRepository.deleteAll();

        User dan = new User("dupa", passwordEncoder.encode("dupa"), "USER", "");
        User admin = new User("admin", passwordEncoder.encode("admin"), "ADMIN", "");
        User manager = new User("manager", passwordEncoder.encode("manager"), "MANAGER", "");


        List<User> users = Arrays.asList(dan, admin, manager);
        this.userRepository.saveAll(users);
    }
}
