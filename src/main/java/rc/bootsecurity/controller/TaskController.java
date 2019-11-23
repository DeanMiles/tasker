package rc.bootsecurity.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rc.bootsecurity.db.TaskRepository;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;
import rc.bootsecurity.model.UserTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tasks/")
public class TaskController {
    private UserRepository userRepository;
    private TaskRepository taskRepository;


    public TaskController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @PostMapping("adminToggleDone/{id}")
    public List<UserTask> ADMIN_toggleDone(@PathVariable long id) {
        UserTask userTask = taskRepository.findById(id);
        if (userTask != null) {
            userTask.setIsDone(!userTask.getIsDone());
            taskRepository.save(userTask);
            return getAll();
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("toggleDone/{id}")
    public List<UserTask> USER_toggleDone(@PathVariable long id) {
        UserTask userTask = taskRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userTask != null && userTask.isOwner(userRepository.findByUsername(authentication.getName()).getId())) {
            userTask.setIsDone(!userTask.getIsDone());
            taskRepository.save(userTask);
            return getMyTasks();
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("adminShowDoneOnly")
    public List<UserTask> ADMIN_showDoneOnly() {
        return getAll().stream().filter(x -> x.getIsDone()).collect(Collectors.toList());
    }

    @GetMapping("adminShowUnDoneOnly")
    public List<UserTask> ADMIN_showUnDoneOnly() {
        return getAll().stream().filter(x -> !x.getIsDone()).collect(Collectors.toList());
    }

    @GetMapping("showDoneOnly")
    public List<UserTask> USER_showDoneOnly() {
        return getMyTasks().stream().filter(x -> x.getIsDone()).collect(Collectors.toList());
    }

    @GetMapping("showUnDoneOnly")
    public List<UserTask> USER_showUnDoneOnly() {
        return getMyTasks().stream().filter(x -> !x.getIsDone()).collect(Collectors.toList());
    }

    @PostMapping("delete/{id}")
    public List<UserTask> delete(@PathVariable long id) {
        this.taskRepository.delete(taskRepository.findById(id));
        return taskRepository.findAll();
    }

    @PostMapping("mydelete/{id}")
    public List<UserTask> myDelete(@PathVariable long id) {
        this.taskRepository.delete(taskRepository.findById(id));
        return getMyTasks();
    }

    @GetMapping("index")
    public String tasks() {
        return "tasks";
    }

    @GetMapping("all")
    public List<UserTask> getAll() {
        return this.taskRepository.findAll();
    }

    @GetMapping("mytasks")
    public List<UserTask> getMyTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return taskRepository.findAll().stream().filter(x -> x.isOwner(userRepository.findByUsername(authentication.getName()).getId())).collect(Collectors.toList());
    }
}
