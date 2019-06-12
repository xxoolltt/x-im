package x.im.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import x.im.server.entity.GroupMember;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    List<GroupMember> findAllByGroupId(Long groupId);
}
