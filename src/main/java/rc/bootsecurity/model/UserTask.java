package rc.bootsecurity.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
//@Table(name = "Tasks")
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "ID")
    private long id;

    @Column(nullable = false)
    private String taskName;

//    @Column(name = "Owners' ids")
    private String ownerIds;
//    @Column(name = "StartDate")
    private LocalDate startDate;
//    @Column(name = "EndDate")
    private LocalDate endDate;

    public long getId() {
        return id;
    }

    public List<String> getPermissionList() {
        if (this.ownerIds.length() > 0) {
            return Arrays.asList(this.ownerIds.split(","));
        } else {
            return new ArrayList<>();
        }
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public UserTask(String taskName, String ownerIds, LocalDate startDate, LocalDate endDate) {
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerIds = ownerIds;
    }

    protected UserTask() {

    }

//    @Column(name = "IsDone")
    private boolean isDone = false;
}
