package rc.bootsecurity.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rc.bootsecurity.db.TaskRepository;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;
import rc.bootsecurity.model.UserTask;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class ApiController {

    private UserRepository userRepository;
    private TaskRepository taskRepository;

    public ApiController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("test1")
    public String test1() {
        return "API Test 1";
    }

    @GetMapping("test2")
    public String test2() {
        return "API Test 2";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public List<User> add(@RequestBody User user) {
        userRepository.save(user);
        return userRepository.findAll();
    }

    @RequestMapping(value = "check", method = RequestMethod.GET)
    public List<User> check() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "addtest", method = RequestMethod.POST)
    public List<User> addtest() {
        User user = new User("przemek", "przemek", "USER", "");
        userRepository.save(user);
        return userRepository.findAll();
    }

    //USERS//
    @GetMapping("users")
    public List<User> allUsers() {
        return this.userRepository.findAll();
    }

    @RequestMapping(value = "getUserByID/{id}", method = RequestMethod.GET)
    public List<User> getUserByID(@PathVariable long id) {
        List<User> list = userRepository.findAll();
        return list.stream().filter(x -> x.getId() == id).collect(Collectors.toList());
    }

    //TASKS//
    @GetMapping("tasks")
    public List<UserTask> allTasks() {
        return this.taskRepository.findAll();
    }

    @RequestMapping(value = "tasksById/{id}", method = RequestMethod.GET)
    public List<UserTask> taskById(@PathVariable long id) {
        List<UserTask> tasks = this.taskRepository.findAll();
        return tasks.stream().filter(x -> x.getId() == id).collect(Collectors.toList());
    }
}
