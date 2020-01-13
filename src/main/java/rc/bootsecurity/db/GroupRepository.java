package rc.bootsecurity.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rc.bootsecurity.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findById(long id);
    Group findByGroupName(String name);
}
