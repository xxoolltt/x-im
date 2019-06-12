package x.im.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import x.im.server.dao.UserRepository;
import x.im.server.entity.UserEntity;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public UserEntity getUser(Long user_id) {
        return repository.getFirstByUserId(user_id);
    }

    public Long addUser(UserEntity userEntity) {
        repository.save(userEntity);
        return userEntity.getUserId();
    }
}
