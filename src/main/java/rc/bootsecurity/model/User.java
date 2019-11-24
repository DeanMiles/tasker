package rc.bootsecurity.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "user_name", nullable = false)
    @NotEmpty(message = "Provide your login")
    private String username;


    @Column(name = "password",nullable = false)
    private String password;

    public User () {
        this("", "");
    }

    private String tasks;
    private String roles = "";
    private String permissions = "";

    public String getGroups() {
        return groups;
    }

    public void addGroup(long id) {
        if (groups != null) {
            groups += ",";
        }
        groups += String.valueOf(id);
    }

    public List<Long> getGroupsList() {
        if (groups.length() > 0) {
            return Arrays.asList(groups.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public void deleteGroup(long id) {
        List<Long> list = getGroupsList();
        if (list.contains(id)) {
            list.remove(id);
            String string = "";
            for (Long i : list) {
                string += String.valueOf(id);
                string += ",";
            }
            groups = string;
        }
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    private String groups = "";
    private int active = 1;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTasks() {
        return tasks;
    }

    public String getRoles() {
        return roles;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = "USER";
        this.permissions = "";
    }

    public User(String username, String password, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
    }


    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        } else {
            return new ArrayList<>();
        }
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        } else {
            return new ArrayList<>();
        }
    }

    public int getActive() {
        return active;
    }
}
