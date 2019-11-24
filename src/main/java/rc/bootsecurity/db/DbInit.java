package rc.bootsecurity.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rc.bootsecurity.model.Group;
import rc.bootsecurity.model.User;
import rc.bootsecurity.model.UserTask;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private PasswordEncoder passwordEncoder;
    private GroupRepository groupRepository;

    public DbInit(UserRepository userRepository, TaskRepository taskRepository, GroupRepository groupRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();
        taskRepository.deleteAll();
        groupRepository.deleteAll();

        User dan = new User("dupa", passwordEncoder.encode("dupa"), "USER", "");
        User admin = new User("admin", passwordEncoder.encode("admin"), "ADMIN", "");
        User manager = new User("manager", passwordEncoder.encode("manager"), "MANAGER", "");
        admin.addGroup(7);
        admin.addGroup(8);
        admin.addGroup(9);
        List<User> users = Arrays.asList(dan, admin, manager);

        UserTask task0 = new UserTask("Task0", "0", LocalDate.now(),LocalDate.now());
        UserTask task1 = new UserTask("Task1", "1", LocalDate.now(),LocalDate.now());
        UserTask task2 = new UserTask("Task2", "2", LocalDate.now(),LocalDate.now());
        List<UserTask> tasks = Arrays.asList(task0, task1, task2);

        Group group0 = new Group("group0", "2", "4,5,6");
        Group group1 = new Group("group1", "2", "4,5");
        Group group2 = new Group("group2", "2", "6");
        List<Group> groups = Arrays.asList(group0, group1, group2);

        userRepository.saveAll(users);
        taskRepository.saveAll(tasks);
        groupRepository.saveAll(groups);
    }
}
