package rc.bootsecurity.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
//@Table(name = "Tasks")
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "ID")
    private long id;

    @Column(nullable = false)
    private String taskName;

    public String getOwnerIds() {
        return ownerIds;
    }

    private String ownerIds;
    private LocalDate startDate;
    private LocalDate endDate;
    private String desc;

    public long getId() {
        return id;
    }

    public List<Long> getOwnersList() {
        if (this.ownerIds.length() > 0) {
            return Arrays.asList(this.ownerIds.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public boolean isOwner(Long id) {
        List<Long> longList = getOwnersList();
        return longList.contains(id);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    protected UserTask() {

    }

    public void addOwner(long id) {
        if (ownerIds != null) {
            ownerIds += ",";
        }
        ownerIds += String.valueOf(id);
    }

    public void deleteOwner(long id) {
        List<Long> list = getOwnersList();
        list = list.stream().filter(f -> f != id).collect(Collectors.toList());
        ownerIds = list.toString().substring(1, list.toString().length()-1).replace(" ", "");
    }

    public UserTask(String taskName, String ownerIds) {
        this.taskName = taskName;
        this.ownerIds = ownerIds;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
        this.desc = "brak";
    }

    public UserTask(String taskName, String ownerIds, String desc) {
        this.taskName = taskName;
        this.ownerIds = ownerIds;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
        this.desc = desc;
    }

    public UserTask(String taskName, String ownerIds, String desc, LocalDate localDate) {
        this.taskName = taskName;
        this.ownerIds = ownerIds;
        this.startDate = LocalDate.now();
        this.endDate = localDate;
        this.desc = desc;
    }

    private boolean isDone = false;
}
