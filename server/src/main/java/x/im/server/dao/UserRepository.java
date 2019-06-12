package x.im.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import x.im.server.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity getFirstByUserId(Long userId);
}
