package x.im.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import x.im.server.entity.MessageEntity;

import java.util.List;


public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    MessageEntity getMessageById(Long id);

    List<MessageEntity> getAllByToAndMessageStatusReceivedOrderByMsgTime(long to, long received);

}
