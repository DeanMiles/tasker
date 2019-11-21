package rc.bootsecurity.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rc.bootsecurity.model.UserTask;

@Repository
public interface TaskRepository extends JpaRepository<UserTask, Long> {
    UserTask findById(long id);
}
