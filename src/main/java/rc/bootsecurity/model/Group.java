//package rc.bootsecurity.model;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//public class Group {
//    public long getId() {
//        return id;
//    }
//
//    public String getGroupName() {
//        return groupName;
//    }
//
//    public void setGroupName(String groupName) {
//        this.groupName = groupName;
//    }
//
//    public List<Long> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(List<Long> participants) {
//        this.participants = participants;
//    }
//
//    public List<Long> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(List<Long> tasks) {
//        this.tasks = tasks;
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//
//    @Column(nullable = false)
//    private String groupName;
//
//    public Group(String groupName, List<Long> participants, List<Long> tasks) {
//        this.groupName = groupName;
//        this.participants = participants;
//        this.tasks = tasks;
//    }
//
//    public Group(String groupName, List<Long> participants) {
//        this.groupName = groupName;
//        this.participants = participants;
//    }
//
//    public void addUser(long id) {
//        participants.add(id);
//    }
//
//    public void addUser(List<Long> ids) {
//        participants.addAll(ids);
//    }
//
//    public void addTask(long id) {
//        tasks.add(id);
//    }
//
//    public void addTask(List<Long> ids) {
//        tasks.addAll(ids);
//    }
//
//    @Column(nullable = false)
//    private List<Long> participants;
//    private List<Long> tasks;
//}
