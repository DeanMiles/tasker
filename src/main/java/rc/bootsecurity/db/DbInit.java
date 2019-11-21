package rc.bootsecurity.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rc.bootsecurity.model.User;
import rc.bootsecurity.model.UserTask;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, TaskRepository taskRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        this.userRepository.deleteAll();
        this.taskRepository.deleteAll();

        User dan = new User("dupa", passwordEncoder.encode("dupa"), "USER", "");
        User admin = new User("admin", passwordEncoder.encode("admin"), "ADMIN", "");
        User manager = new User("manager", passwordEncoder.encode("manager"), "MANAGER", "");
        List<User> users = Arrays.asList(dan, admin, manager);

        UserTask task0 = new UserTask("Task0", "0", LocalDate.now(),LocalDate.now());
        UserTask task1 = new UserTask("Task1", "1", LocalDate.now(),LocalDate.now());
        UserTask task2 = new UserTask("Task2", "2", LocalDate.now(),LocalDate.now());
        List<UserTask> tasks = Arrays.asList(task0, task1, task2);
        this.userRepository.saveAll(users);
        this.taskRepository.saveAll(tasks);
    }
}
