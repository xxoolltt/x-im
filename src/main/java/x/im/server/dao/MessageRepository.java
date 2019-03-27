package x.im.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import x.im.server.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
