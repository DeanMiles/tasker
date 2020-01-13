package rc.bootsecurity.controller;

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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("groups/")
public class GroupController {

    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private TaskRepository taskRepository;

    public GroupController(GroupRepository groupRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("mygroups")
    public List<Group> showGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equalsIgnoreCase("anonymousUser")) {
            return groupRepository.findAll().stream().filter(x -> x.isParticipant(userRepository.findByUsername(authentication.getName()).getId())).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("leaveGroup")
    public void leaveGroup(@RequestParam long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Group group = groupRepository.findById(id);
        User user = userRepository.findByUsername(authentication.getName());
        if (authentication.isAuthenticated() && group.isParticipant(user.getId())) {
            group.deleteParticipant(user.getId());
            groupRepository.save(group);
        }
    }

    @GetMapping("all")
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @GetMapping("getOwnerNames")
    public String getOwnerNames() {
        List<User> users = userRepository.findAll();
        JsonObject jsonObject = new JsonObject();
        users.forEach(u -> jsonObject.addProperty(String.valueOf(u.getId()), u.getUsername()));
        return jsonObject.toString();
    }

    @PostMapping("createGroup")
    public void createGroup(@RequestParam String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Group group = groupRepository.findByGroupName(name);
        if (authentication.isAuthenticated() && group == null) {
            Group newgroup = new Group(name);
            newgroup.addParticipant(userRepository.findByUsername(authentication.getName()).getId());
            groupRepository.save(newgroup);
        }
    }

    @PostMapping("deleteParticipant")
    public void deleteParticipant(@RequestParam long id, @RequestParam String name) {
        Group group = groupRepository.findById(id);
        User user = userRepository.findByUsername(name);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && group != null && group.isParticipant(userRepository.findByUsername(authentication.getName()).getId()) && group.isParticipant(user.getId()) && user != null) {
            group.deleteParticipant(user.getId());
            groupRepository.save(group);
        }
    }

    @PostMapping("newtask")
    public void newTask(@RequestParam String title, @RequestParam String date, @RequestParam long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Group group = groupRepository.findById(id);
        User owner = userRepository.findByUsername(authentication.getName());
        if (authentication.isAuthenticated() && group != null && owner != null && group.isParticipant(owner.getId())) {
            LocalDate localDate = LocalDate.parse(date);
            UserTask userTask = new UserTask(title, String.valueOf(userRepository.findByUsername(authentication.getName()).getId()), "brak", localDate);
            taskRepository.save(userTask);
            group.addTask(userTask.getId());
            groupRepository.save(group);
        }
    }

    @PostMapping("deleteTask")
    public void deleteTask(@RequestParam long id, @RequestParam int indeks) {
        Group group = groupRepository.findById(id);
        UserTask userTask = taskRepository.findById(group.getTaskList().get(indeks)).get();
        User owner = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (group != null && owner != null && userTask != null && group.isParticipant(owner.getId()) && group.isTask(userTask.getId())) {
            group.deleteTask(userTask.getId());
            groupRepository.save(group);
            taskRepository.delete(userTask);
        }
    }

    @PostMapping("addParticipant")
    public void addParticipant(@RequestParam long id, @RequestParam String name) {
        Group group = groupRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User owner = userRepository.findByUsername(authentication.getName());
        User user = userRepository.findByUsername(name);
        if (group.isParticipant(owner.getId()) && !group.isParticipant(id) && group != null && user != null) {
            group.addParticipant(userRepository.findByUsername(name).getId());
            groupRepository.save(group);
        }
    }
}
