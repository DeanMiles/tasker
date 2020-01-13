package rc.bootsecurity.controller;


import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import com.google.gson.JsonObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rc.bootsecurity.db.GroupRepository;
import rc.bootsecurity.db.TaskRepository;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.Group;
import rc.bootsecurity.model.User;
import rc.bootsecurity.model.UserTask;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tasks/")
public class TaskController {
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private GroupRepository groupRepository;


    public TaskController(UserRepository userRepository, TaskRepository taskRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
    }

    @PostMapping("toggle/{id}")
    public void toggle(@PathVariable long id) {
        UserTask userTask = taskRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            userTask.setIsDone(!userTask.getIsDone());
            taskRepository.save(userTask);
        }
    }

    @PostMapping("delete/{id}")
    public void delete(@PathVariable long id) {
        UserTask userTask = taskRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            taskRepository.delete(userTask);
        }
    }

    @GetMapping("index")
    public String tasks() {
        return "tasks";
    }

    @PostMapping("newtask/{title}/{desc}/{date}")
    public void addTask(@PathVariable String title, @PathVariable String desc, @PathVariable String date) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            LocalDate localDate = LocalDate.parse(date);
            taskRepository.save(new UserTask(title, String.valueOf(userRepository.findByUsername(authentication.getName()).getId()), desc, localDate));
        }
    }

    @PostMapping("addOwner/{taskid}/{username}")
    public void addOwner(@PathVariable long taskid, @PathVariable String username) {
        User user = userRepository.findByUsername(username);
        UserTask userTask = taskRepository.findById(taskid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (user != null && userTask != null  && authentication.isAuthenticated() && taskRepository.findById(taskid).isOwner(userRepository.findByUsername(authentication.getName()).getId())) {
            userTask.addOwner(userRepository.findByUsername(username).getId());
            taskRepository.save(userTask);
        }
    }

    @PostMapping("deleteOwner/{taskid}/{username}")
    public void deleteOwner(@PathVariable long taskid, @PathVariable String username) {
        User user = userRepository.findByUsername(username);
        UserTask userTask = taskRepository.findById(taskid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && user != null && userTask != null && taskRepository.findById(taskid).isOwner(userRepository.findByUsername(authentication.getName()).getId()) &&
        userTask.isOwner(user.getId())) {
            userTask.deleteOwner(userRepository.findByUsername(username).getId());
            taskRepository.save(userTask);
        }
    }

    @GetMapping("show/{done}/{time}/{group}")
    public List<UserTask> show(@PathVariable int done, @PathVariable int time, @PathVariable int group) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        if (auth.isAuthenticated()) {
            List<UserTask> list = taskRepository.findAll();
            return list.stream().filter(f -> {
                if (done == 0) {
                    return true;
                } else if (done == 1 && f.getIsDone()) {
                    return true;
                } else if (done == 2 && !f.getIsDone()) {
                    return true;
                } else {
                    return false;
                }
            }).filter(f -> {
                if (time == 0) {
                    return true;
                } else if (time == 1 && f.getEndDate().compareTo(LocalDate.now()) < 7) {
                    return true;
                } else if (time == 2 && f.getEndDate().compareTo(LocalDate.now()) < 31) {
                    return true;
                } else if (time == 3 && f.getEndDate().compareTo(LocalDate.now()) < 365) {
                    return true;
                } else {
                    return false;
                }
            }).filter(f -> {
                if (group == 0 && (f.isOwner(user.getId()))) {
//                    groupRepository.findAll().stream().filter(g -> g.isTask(f.getId()) && g.isParticipant(user.getId())).
                    return true;
                } else if (group == 1 && f.isOwner(user.getId())) {
                    return true;
                } else if (group == 2 && !f.isOwner(user.getId())) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("getTasksNames")
    public String getTasksNames() {
        List<UserTask> tasks = taskRepository.findAll();
        JsonObject jsonObject = new JsonObject();
        tasks.forEach(u -> jsonObject.addProperty(String.valueOf(u.getId()), u.getTaskName()));
        return jsonObject.toString();
    }

    @GetMapping("getOwnerNames")
    public String getOwnerNames() {
        List<User> users = userRepository.findAll();
        JsonObject jsonObject = new JsonObject();
        users.forEach(u -> jsonObject.addProperty(String.valueOf(u.getId()), u.getUsername()));
        return jsonObject.toString();
    }

}
