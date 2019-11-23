package rc.bootsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rc.bootsecurity.db.TaskRepository;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;
import rc.bootsecurity.model.UserTask;

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

    @PostMapping("toggledone/{id}")
    public List<UserTask> toggleDone(@PathVariable long id) {
        UserTask userTask = this.taskRepository.findById(id);
        if (userTask.getIsDone()) {
            userTask.setIsDone(false);
        } else {
            userTask.setIsDone(true);
        }
        this.taskRepository.save(userTask);
        return this.taskRepository.findAll();
    }

    @GetMapping("done")
    public List<UserTask> done() {
        List<UserTask> list = taskRepository.findAll();
        return list.stream().filter(x -> x.getIsDone() == true).collect(Collectors.toList());
    }

    @GetMapping("undone")
    public List<UserTask> undone() {
        List<UserTask> list = taskRepository.findAll();
        return list.stream().filter(x -> x.getIsDone() == false).collect(Collectors.toList());
    }

    @PostMapping("delete/{id}")
    public List<UserTask> delete(@PathVariable long id) {
        this.taskRepository.delete(taskRepository.findById(id));
        return taskRepository.findAll();
    }

    @GetMapping("index")
    public String tasks() {
        return "tasks";
    }

    @GetMapping("all")
    public List<UserTask> getAll() {
        return this.taskRepository.findAll();
    }
}
