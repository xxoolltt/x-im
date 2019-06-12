package x.im.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import x.im.server.entity.MessageStatus;

import java.util.Iterator;
import java.util.List;

public interface MessageStatusRepository extends JpaRepository<MessageStatus, Long> {

    MessageStatus findByMsgId(long id);

    List<MessageStatus> getAllByMsgIdIn(Iterable<Long> ids);
//    List<MessageStatus> findByMsgId(Iterable<Long> ids);
}
