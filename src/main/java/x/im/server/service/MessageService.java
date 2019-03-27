package x.im.server.service;

import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import x.im.server.UserManager;
import x.im.server.dao.MessageRepository;
import x.im.server.entity.Message;

import java.util.Optional;

import static x.im.server.Const.CODE_SEND_SUCCESS;


@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Async
    public void insertMessage(Message msg, Channel ch) {
        System.out.println(msg);
        messageRepository.save(msg);
        JSONObject response = new JSONObject();
        try {
            response.put("code", CODE_SEND_SUCCESS);
            response.put("send_mid", msg.getSendId());
            response.put("msg_id", msg.getId());
            response.put("time", msg.getMsgTime());
            if (ch.isActive()) {
                ch.writeAndFlush(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Channel channel = UserManager.find((int) msg.getTo());
        if (channel == null || !channel.isActive()) {
            System.out.println("not find channel");
        } else {
            channel.writeAndFlush(msg.toString());
        }
    }

    @Async
    public void messageReceived(Long msgId) {
/*        Optional<Message> option = messageRepository.findAll();
        if (option != null || option.get() == null) {
            Message message = option.get();
            message.setReceived(1);
            messageRepository.save(message);
        }*/
    }

}
