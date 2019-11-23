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

//    @Column(name = "Owners' ids")
    private String ownerIds;
//    @Column(name = "StartDate")
    private LocalDate startDate;
//    @Column(name = "EndDate")
    private LocalDate endDate;

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

    public UserTask(String taskName, String ownerIds, LocalDate startDate, LocalDate endDate) {
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerIds = ownerIds;
    }

    protected UserTask() {

    }

    private boolean isDone = false;
}
