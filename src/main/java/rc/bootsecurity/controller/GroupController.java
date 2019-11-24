package rc.bootsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rc.bootsecurity.db.GroupRepository;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("groups/")
public class GroupController {

    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public GroupController(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
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

    @GetMapping("all")
    public List<Group> getAll() {
        return groupRepository.findAll();
    }
}
