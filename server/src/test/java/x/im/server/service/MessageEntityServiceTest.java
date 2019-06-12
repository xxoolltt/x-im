package x.im.server.service;

import org.springframework.test.context.ActiveProfiles;
import x.im.server.TestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import x.im.server.dao.MessageRepository;
import x.im.server.dao.MessageStatusRepository;
import x.im.server.entity.MessageEntity;
import x.im.server.entity.MessageStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * MessageService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 18, 2019</pre>
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = TestApplication.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(classes = TestApplication.class)
public class MessageEntityServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageStatusRepository messageStatusRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: insertMessage(MessageEntity msg, Channel ch)
     */
    @Test
    public void testInsertMessage() throws Exception {
        MessageEntity messageEntity = new MessageEntity();
//        messageEntity.setTo(10002);
//        messageEntity.setFrom(10000);
        messageEntity.setMsgTime(System.currentTimeMillis());
        messageEntity.setType(0);
//        messageEntity.setSendId(1002);
        messageEntity.setContent("test");
        messageService.insertMessage(messageEntity, null);
    }

    @Test
    public void testUnreceivedMsgList() {
        List<MessageEntity> messageEntityList = messageRepository.getAllByToAndMessageStatusReceivedOrderByMsgTime(10002, 0);
        Assert.assertEquals(messageEntityList.size(), 2);
    }

    @Test
    public void testReceiveList() {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(Long.valueOf(10));
        ids.add(Long.valueOf(11));
        List<MessageStatus> list = messageStatusRepository.getAllByMsgIdIn(ids);
        Assert.assertEquals(list.size(), 2);
    }

    /**
     * Method: messageReceived(Long msgId)
     */
    @Test
    public void testMessageReceived() throws Exception {
        Long id = Long.valueOf(7);
        messageService.messageReceived(id);
        MessageStatus one = messageStatusRepository.findByMsgId(id);
        Assert.assertEquals(one.getReceived(), 1);
    }


}
