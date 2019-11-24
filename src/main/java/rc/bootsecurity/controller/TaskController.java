package rc.bootsecurity.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rc.bootsecurity.db.GroupRepository;
import rc.bootsecurity.db.TaskRepository;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.Group;
import rc.bootsecurity.model.User;
import rc.bootsecurity.model.UserTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @PostMapping("adminDelete/{id}")
    public List<UserTask> ADMIN_delete(@PathVariable long id) {
        UserTask userTask = taskRepository.findById(id);
        if (userTask != null) {
            taskRepository.delete(userTask);
        }
        return getAll();
    }

    @PostMapping("delete/{id}")
    public List<UserTask> delete(@PathVariable long id) {
        UserTask userTask = taskRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userTask != null && userTask.isOwner(userRepository.findByUsername(authentication.getName()).getId())) {
            taskRepository.delete(userTask);
        }
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
        if (!authentication.getName().equalsIgnoreCase("anonymousUser")) {
            return taskRepository.findAll().stream().filter(x -> x.isOwner(userRepository.findByUsername(authentication.getName()).getId())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

//    @GetMapping("mytasks")
//    public List<UserTask> getMyTasks() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findByUsername(authentication.getName());
//        if (user != null) {
//            List<UserTask> myTasks = taskRepository.findAll().stream().filter(x -> x.isOwner(user.getId())).collect(Collectors.toList());
//            if (user.getGroups().length() > 0) {
//                List<Group> groups = groupRepository.findAll().stream().filter(x -> x.isParticipant(user.getId())).collect(Collectors.toList());
//                groups.forEach(x -> x.getTaskList().stream().forEach(y -> {
//                    Optional<UserTask> tasks = taskRepository.findById(y);
//                    if (!myTasks.contains(tasks.get())) {
//                        myTasks.add(tasks.get());
//                    }
//                }));
//            }
//            return myTasks;
//        } else {
//            return new ArrayList<>();
//        }
//    }

    @PostMapping("newtask")
    public List<UserTask> addTask(@RequestParam String title) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equalsIgnoreCase("anonymousUser")) {
            UserTask userTask = new UserTask(title, String.valueOf(userRepository.findByUsername(authentication.getName()).getId()));
            taskRepository.save(userTask);
            return getMyTasks();
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("addOwner/{taskid}")
    public List<UserTask> addOwner(@PathVariable long taskid) {
//        User user = userRepository.findById(userid);
        UserTask userTask = taskRepository.findById(taskid);
        if (/*user != null && */userTask != null) {
            userTask.addOwner(3);
            taskRepository.save(userTask);
        }
        return new ArrayList<>();
    }

    @PostMapping("deleteOwner/{taskid}")
    public List<UserTask> deleteOwner(@PathVariable long taskid) {
        UserTask userTask = taskRepository.findById(taskid);
        if (userTask != null) {
            userTask.deleteOwner(3);
            taskRepository.save(userTask);
        }
        return new ArrayList<>();
    }
}
