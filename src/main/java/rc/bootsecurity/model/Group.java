package rc.bootsecurity.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Groups")
public class Group{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String participants = "";
    private String tasks;

    public String getParticipants() {
        return participants;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Long> getParticipantsList() {
        if (participants.length() > 0) {
            return Arrays.asList(participants.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<Long> getTaskList() {
        if (tasks.length() > 0) {
            return Arrays.asList(tasks.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public void addTask(long id) {
        List<Long> list = getTaskList();
        list.add(id);
        tasks = list.toString().substring(1, list.toString().length() - 1).replace(" ", "");
    }

    public String getTasks() {
        return tasks;
    }

    public void addParticipant(long id) {
        List<Long> list = getParticipantsList();
        list.add(id);
        participants = list.toString().substring(1, list.toString().length() - 1).replace(" ", "");
    }

    public void deleteParticipant(long id) {
        List<Long> list = getParticipantsList();
        list = list.stream().filter(f -> f != id).collect(Collectors.toList());
        participants = list.toString().substring(1, list.toString().length()-1).replace(" ", "");
    }

    public void deleteTask(long id) {
        List<Long> list = getTaskList();
        list = list.stream().filter(f -> f != id).collect(Collectors.toList());
        tasks = list.toString().substring(1, list.toString().length()-1).replace(" ", "");
    }

    public boolean isParticipant(Long id) {
        List<Long> list = getParticipantsList();
        return list.contains(id);
    }

    public boolean isTask(Long id) {
        List<Long> list = getParticipantsList();
        return list.contains(id);
    }

    private String groupName;

    public Group() {
    }
    public Group(String groupName) {
        this.groupName = groupName;
        this.participants = new String();
        this.tasks = new String();
    }

    public Group(String groupName, String participants) {
        this.groupName = groupName;
        this.participants = participants;
    }

    public Group(String groupName, String participants, String tasks) {
        this.groupName = groupName;
        this.participants = participants;
        this.tasks = tasks;
    }

    public long getId() {
        return id;
    }
}