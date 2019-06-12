package x.im.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import x.im.server.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
